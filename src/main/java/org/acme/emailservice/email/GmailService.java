package org.acme.emailservice.email;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.acme.emailservice.email.oauth2.OAuth2Authenticator;
import org.jboss.logging.Logger;

import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;

public class GmailService {

    private static Logger LOGGER = Logger.getLogger(GmailService.class);

    private String emailAddress;
    private String accessToken;
    private Store store;
    public Folder inbox;

    static ScheduledExecutorService monitor;
    static SynchronizeTask synchronizeTask;

    public GmailService() {
        // USER_NAME = getProperty("gmail.username");
        // created = true;
        // emails = new HashSet<Mail>();
        monitor = Executors.newScheduledThreadPool(0);
        synchronizeTask = new SynchronizeTask();
        OAuth2Authenticator.initialize();
        LOGGER.info("GmailService created");
    }

    public void login(String emailAddress, String accessToken) throws MessagingException {

        if (emailAddress == null || accessToken == null) {
            throw new IllegalArgumentException("Email address and access token fields are required");
        }

        this.emailAddress = emailAddress;
        this.accessToken = accessToken;

        OAuth2Authenticator.initialize();
        this.store = OAuth2Authenticator.connectToImap(this.emailAddress, this.accessToken);

        this.inbox = store.getFolder("INBOX");
        // this.inbox.open(Folder.READ_ONLY);
    }

    public void shutdown() {
        LOGGER.info("GmailService shutdown");
        if (synchronizeTask != null)
            synchronizeTask.stop();
        if (monitor != null)
            monitor.shutdownNow();
    }

    class SynchronizeTask implements Runnable {

        boolean stop;
        int load;
        boolean settingCode;

        public SynchronizeTask() {
            monitor.scheduleWithFixedDelay(this, 20, 20, TimeUnit.SECONDS);
            stop = false;
            load = 0;
            settingCode = false;
        }

        public synchronized void run() {
            try {
                if (stop) {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    if (!this.equals(synchronizeTask)) {
                        stop();
                        return;
                    }
                    if (load != 0 && !settingCode) {
                        LOGGER.info("GmailService...");
                    } else
                        load++;
                }
            } catch (Exception e) {
                while (!Thread.interrupted()) {
                    stop = true;
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void stop() {
            while (!Thread.interrupted()) {
                stop = true;
                Thread.currentThread().interrupt();
            }
        }
    }
}
