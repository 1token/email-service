package org.acme.emailservice.service;

import java.util.Collections;

// import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

    @Transactional
    public Message updateOrCreate(Message newMessage) {
        if (newMessage.getId() == null) {
            em.persist(newMessage);
            return newMessage;
        } else {
            boolean updateHistory = false;
            boolean updateTimeline = false;
            Message oldMessage = em.find(Message.class, newMessage.getId());
            // Set<Label> labelsOld = persistentMessage.getLabels();
            if (oldMessage.getSentAt() == null) {
                if (newMessage.getSubject() != null) {
                    updateHistory = true;
                    updateTimeline = true;
                    oldMessage.setSubject(newMessage.getSubject());
                }
                List<Tag> newTags = newMessage.getTags();
                List<Tag> oldTags = oldMessage.getTags();
                boolean tagEquals = newTags.containsAll(oldTags) && oldTags.containsAll(newTags);
                if (newTags != null && !tagEquals) {
                    updateHistory = true;
                    updateTimeline = true;
                    em.createQuery("DELETE FROM Tag t WHERE t.message.id=:messageId")
                    .setParameter("messageId", oldMessage.getId())
                    .executeUpdate();
                    for (Tag tag : newMessage.getTags()) {
                        tag.setMessage(oldMessage);
                    }   
                    oldMessage.setTags(newMessage.getTags());
                }
            }
            if (oldMessage.getLabels() != null && (!oldMessage.getLabels().equals(newMessage.getLabels()))) {
                updateHistory = true;
                oldMessage.setLabels(newMessage.getLabels());
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
