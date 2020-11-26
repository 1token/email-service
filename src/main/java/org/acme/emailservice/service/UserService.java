package org.acme.emailservice.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.acme.emailservice.model.User;

@ApplicationScoped
public class UserService {
    
    @PersistenceContext(name="UserDS")
    EntityManager em;

    public User getUser(Long id){
        return em.find(User.class, id);
    }

    public User getUser(User user) {
        User t = em.find(User.class, user);
        System.out.println("Error");
        return t;
    }
    
    public List<User> getUsers(){
        return (List<User>)em.createNamedQuery("User.getAll", User.class).getResultList();        
    }

    @Transactional
    public User updateOrCreate(User user) {
        if (user.getId() == null){
            em.persist(user);
            return user;
        }else{
            return em.merge(user);
        }
    }

    @Transactional
    public User delete(Long id) {
        User t = em.find(User.class, id);

        if (t != null){
            em.remove(t);
        }
        return t;
    }
}
