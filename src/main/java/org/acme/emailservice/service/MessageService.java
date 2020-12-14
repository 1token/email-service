package org.acme.emailservice.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.acme.emailservice.model.Label;
import org.acme.emailservice.model.Message;
import org.acme.emailservice.model.Tag;
// import org.jboss.logging.Logger;

@ApplicationScoped
public class MessageService {

    // private static Logger LOGGER = Logger.getLogger(Message.class);

    @PersistenceContext
    EntityManager em;

    public Optional<Message> getMessage(String username, Long id) {
        try {
            Message result = em.createNamedQuery("Message.get", Message.class).setParameter("username", username)
                    .setParameter("id", id).getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            // LOGGER.debugf("No record found for username: %s", username);
            return Optional.empty();
        }
    }

    public List<Message> getMessages(String username) {
        return (List<Message>) em.createNamedQuery("Message.getAll", Message.class).setParameter("username", username)
                .getResultList();
    }

    // ToDo: User/Role for message, labels, ...
    @Transactional
    public Optional<Message> updateOrCreate(String username, Message newMessage) {
        if (newMessage.getId() == null) {
            // newMessage.getAccount().setUsername(username);
            em.persist(newMessage);
            return Optional.ofNullable(newMessage);
        } else {
            boolean updateHistory = false;
            boolean updateTimeline = false;
            Message oldMessage = em.find(Message.class, newMessage.getId());
            if (oldMessage == null) {
                return Optional.empty();
            }
            if (oldMessage.getSentAt() == null) {
                // Subject
                if (newMessage.getSubject() != null && (!oldMessage.getSubject().equals(newMessage.getSubject()))) {
                    updateHistory = true;
                    updateTimeline = true;
                    oldMessage.setSubject(newMessage.getSubject());
                }
                // Tags
                for (Tag tag : newMessage.getTags()) {
                    tag.setMessage(oldMessage);
                }
                List<Tag> newTags = newMessage.getTags();
                List<Tag> oldTags = oldMessage.getTags();
                boolean tagEquals = newTags.containsAll(oldTags) && oldTags.containsAll(newTags);
                if (newTags != null && !tagEquals) {
                    updateHistory = true;
                    updateTimeline = true;
                    oldMessage.getTags().clear();
                    oldMessage.getTags().addAll(newMessage.getTags());
                }
            }
            // Labels
            Set<Label> newLabels = newMessage.getLabels();
            Set<Label> oldLabels = oldMessage.getLabels();
            boolean labelEquals = newLabels.containsAll(oldLabels) && oldLabels.containsAll(newLabels);
            if (newLabels != null && !labelEquals) {
                updateHistory = true;
                oldMessage.getLabels().clear();
                for (Label l : newMessage.getLabels()) {
                    Label label = em.find(Label.class, l.getId());
                    if (label != null) {
                        oldMessage.addLabel(label);
                    }
                }
            }
            if (updateHistory) {
                Long value = Long.parseLong(
                        em.createNativeQuery("select nextval('MESSAGE_HISTORY_ID')").getSingleResult().toString());
                oldMessage.setHistoryId(value);
            }
            if (updateTimeline) {
                Long value = Long.parseLong(
                        em.createNativeQuery("select nextval('MESSAGE_TIMELINE_ID')").getSingleResult().toString());
                oldMessage.setTimelineId(value);
            }
            return Optional.ofNullable(em.merge(oldMessage));
        }
    }

    @Transactional
    public Optional<Message> delete(String username, Long id) {
        Message result = em.createNamedQuery("Message.get", Message.class).setParameter("username", username)
        .setParameter("id", id).getSingleResult();

        if (result != null) {
            em.remove(result);
        }
        return Optional.ofNullable(result);
    }
}
