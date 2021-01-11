package org.acme.emailservice.email;

import org.acme.emailservice.model.Account;
import org.jboss.logging.Logger;

public class GmailClient extends BaseClassClient {

    private static Logger log = Logger.getLogger(GmailClient.class);

    public GmailClient(Account account) {

        super(account);
        // USER_NAME = getProperty("email.username");
        // created = true;
        // emails = new HashSet<Mail>();

        // OAuth2Authenticator.initialize();

        log.info("GmailClient created");
    }

    public void login() {
        log.info("GmailClient/SynchronizedTask login");
    }

    public void synchronize() {
        log.info("GmailClient/SynchronizedTask synchronize");
    }
}
