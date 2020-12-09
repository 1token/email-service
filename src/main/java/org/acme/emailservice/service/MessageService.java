package org.acme.emailservice.service;

import java.util.Collections;

// import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.acme.emailservice.exception.RecordNotFound;
import org.acme.emailservice.model.Label;
import org.acme.emailservice.model.Message;
import org.acme.emailservice.model.Tag;

@ApplicationScoped
public class MessageService {

    @PersistenceContext
    EntityManager em;

    public Message getMessage(Long id) {
        return em.find(Message.class, id);
    }

    public Message getMessage(Message message) {
        Message t = em.find(Message.class, message);
        System.out.println("Error");
        return t;
    }

    public List<Message> getMessages() {
        // return (List<Message>)em.createNamedQuery("Message.getAll",
        // Message.class).getResultStream()
        // .collect(toList());
        return (List<Message>) em.createNamedQuery("Message.getAll", Message.class).getResultList();
    }

    // ToDo: User/Role for message, labels, ...
    @Transactional
    public Message updateOrCreate(Message newMessage) throws RecordNotFound {
        if (newMessage.getId() == null) {
            em.persist(newMessage);
            return newMessage;
        } else {
            boolean updateHistory = false;
            boolean updateTimeline = false;
            Message oldMessage = em.find(Message.class, newMessage.getId());
            if (oldMessage == null) {
                throw new RecordNotFound("Message [" + newMessage.getId() + "] not found");
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
            return em.merge(oldMessage);
        }
    }

    @Transactional
    public Message delete(Long id) {
        Message t = em.find(Message.class, id);

        if (t != null) {
            em.remove(t);
        }
        return t;
    }
}
