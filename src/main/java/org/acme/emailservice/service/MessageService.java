package org.acme.emailservice.service;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.acme.emailservice.exception.EmailServiceException;
import org.acme.emailservice.model.Account;
import org.acme.emailservice.model.Label;
import org.acme.emailservice.model.Message;
import org.acme.emailservice.model.Tag;
// import org.jboss.logging.Logger;
import org.acme.emailservice.model.User;

@ApplicationScoped
public class MessageService {

    // private static Logger LOGGER = Logger.getLogger(Message.class);

    @PersistenceContext
    EntityManager em;

    public Message getMessage(String username, Long id) {
        Message result = em.createNamedQuery("Message.get", Message.class).setParameter("username", username)
                .setParameter("id", id).getSingleResult();
        return result;
    }

    public List<Message> getMessages(String username) {
        return (List<Message>) em.createNamedQuery("Message.getAll", Message.class).setParameter("username", username)
                .getResultList();
    }

    public List<Message> getMessages(String username, Account account) {
        Account accountByEmailAddress = em.createNamedQuery("Account.getByEmailAddress", Account.class).setParameter("username", username).setParameter("emailAddress", account.getEmailAddress()).getSingleResult();

        return (List<Message>) em.createNamedQuery("Message.getAllByAccount", Message.class).setParameter("username", username).setParameter("account", accountByEmailAddress).getResultList();
    }

        // TODO: User/Role for message, labels, ...
    @Transactional
    public Message createMessage(String username, Account account, Message newMessage) {
        User user = em.createNamedQuery("User.getUserByUsername", User.class).setParameter("username", username).getSingleResult();

        Account accountByEmailAddress = em.createNamedQuery("Account.getByEmailAddress", Account.class).setParameter("username", username).setParameter("emailAddress", account.getEmailAddress()).getSingleResult();
        newMessage.setAccount(accountByEmailAddress);

        Label draftLabel = em.createNamedQuery("Label.getUserDraftsLabel", Label.class).setParameter("user", user).getSingleResult();
        newMessage.addLabel(draftLabel);

        newMessage.setHistoryId(Long.parseLong(em.createNativeQuery("select nextval('MESSAGE_HISTORY_ID')").getSingleResult().toString()));
        newMessage.setTimelineId(Long.parseLong(em.createNativeQuery("select nextval('MESSAGE_TIMELINE_ID')").getSingleResult().toString()));
        newMessage.setLastStmt((byte) 0);

        em.persist(newMessage);
        return newMessage;
    }

    // TODO: User/Role for message, labels, ...
    @Transactional
    public Message updateMessage(String username, Message newMessage) throws EmailServiceException {
        if (newMessage.getId() == null) {
            throw new EmailServiceException("id is required");
        }
        boolean updateHistory = false;
        boolean updateTimeline = false;
        Message oldMessage;
        oldMessage = em.createNamedQuery("Message.get", Message.class).setParameter("username", username)
        .setParameter("id", newMessage.getId()).getSingleResult();
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
        // TODO: insert new labels into label table
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
            oldMessage.setLastStmt((byte) 1);
        }
        if (updateTimeline) {
            Long value = Long.parseLong(
                    em.createNativeQuery("select nextval('MESSAGE_TIMELINE_ID')").getSingleResult().toString());
            oldMessage.setTimelineId(value);
        }
        return em.merge(oldMessage);
    }

    @Transactional
    public Message delete(String username, Long id) {
        Message result = em.createNamedQuery("Message.get", Message.class).setParameter("username", username)
        .setParameter("id", id).getSingleResult();

        if (result != null) {
            em.remove(result);
        }
        return result;
    }
}
