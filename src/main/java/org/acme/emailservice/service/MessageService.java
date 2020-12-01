package org.acme.emailservice.service;

// import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.acme.emailservice.model.Message;

@ApplicationScoped
public class MessageService {

    @PersistenceContext(name="MessageDS")
    EntityManager em;

    public Message getMessage(Long id){
        return em.find(Message.class, id);
    }

    public Message getMessage(Message message) {
        Message t = em.find(Message.class, message);
        System.out.println("Error");
        return t;
    }
    
    public List<Message> getMessages(){
        // return (List<Message>)em.createNamedQuery("Message.getAll", Message.class).getResultStream()
        // .collect(toList());
        return (List<Message>)em.createNamedQuery("Message.getAll", Message.class).getResultList();  
    }
    
}
