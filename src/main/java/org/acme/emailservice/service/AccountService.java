package org.acme.emailservice.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.acme.emailservice.model.Account;

@ApplicationScoped
public class AccountService {

    @PersistenceContext(name="AccountDS")
    EntityManager em;

    public Account getAccount(Long id){
        return em.find(Account.class, id);
    }

    public Account getAccount(Account account) {
        Account t = em.find(Account.class, account);
        System.out.println("Error");
        return t;
    }
    
    public List<Account> getAccounts(){
        return (List<Account>)em.createNamedQuery("Account.getAll", Account.class).getResultList();        
    }
    
}
