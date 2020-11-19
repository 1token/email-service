package org.acme.emailservice.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.acme.emailservice.model.Tag;

@ApplicationScoped
public class TagService {

    @PersistenceContext(name="TagDS")
    EntityManager em;
    
    public List<Tag> getTags(){
        /* return (List<Tag>)em.createQuery("SELECT t FROM Tag t", Tag.class)
                .getResultList(); */
        return (List<Tag>)em.createNamedQuery("Tags.getAll", Tag.class ).getResultList();        
    }
}
