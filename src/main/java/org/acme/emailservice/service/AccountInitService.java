package org.acme.emailservice.service;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.acme.emailservice.model.Account;
import org.acme.emailservice.model.AccountInit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AccountInitService {

    private static Logger log = Logger.getLogger(AccountInitService.class);

    @ConfigProperty(name = "ACCOUNTS_INIT")
    String accountsInitConfig;

    public void persistAccount() {
        Jsonb jsonb = JsonbBuilder.create();
        AccountInit[] accountInitArray = jsonb.fromJson(accountsInitConfig, new AccountInit[] {}.getClass());
        log.debug(jsonb.toJson(accountInitArray));
        for (AccountInit accountInit : accountInitArray) {
            Account newAccount = new Account();
            newAccount.setEmailAddress(accountInit.getEmail_address());
        }
    }
}
