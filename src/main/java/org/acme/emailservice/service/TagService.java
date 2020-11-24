package org.acme.emailservice.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.acme.emailservice.model.Tag;

@ApplicationScoped
public class TagService {

    @PersistenceContext(name="TagDS")
    EntityManager em;

    public Tag getTag(Long id){
        return em.find(Tag.class,id);
    }

    public Tag getTag(Tag tag) {
        Tag t = em.find(Tag.class, tag);
        System.out.println("Error");
        return t;
    }
    
    public List<Tag> getTags(){
        /* return (List<Tag>)em.createQuery("SELECT t FROM Tag t", Tag.class)
                .getResultList(); */
        return (List<Tag>)em.createNamedQuery("Tags.getAll", Tag.class).getResultList();        
    }

    @Transactional
    public Tag updateOrCreate(Tag tag) {
        if (tag.getId() == null){
            em.persist(tag);
            return tag;
        }else{
            return em.merge(tag);
        }
    }

    @Transactional
    public Tag delete(Long id) {
        Tag t = em.find(Tag.class, id);

        if (t != null){
            em.remove(t);
        }
        return t;
    }
}
