package org.acme.emailservice.endpoint;

import java.util.List;

import javax.inject.Inject;

import org.acme.emailservice.model.Account;
import org.acme.emailservice.service.AccountService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class AccountEndpoint {

    @Inject
    AccountService accountService;

    @Query
    public String helloAccount() {
        return "Hello Account!";
    }

    @Query
    public Account getAccount(Long id){
        return accountService.getAccount(id);
    }

    @Query
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }
}