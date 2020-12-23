package org.acme.emailservice.email;

import org.jboss.logging.Logger;

import jakarta.mail.Folder;

public class EmailService {

    private static Logger LOGGER = Logger.getLogger(EmailService.class);

    public Folder inbox;

    int nthreads = 2;
    Thread[] threads;

    public EmailService() {

        LOGGER.info("EmailService created");

        threads = new Thread[nthreads];
		for (int i = 0; i < nthreads; i++) {
			threads[i] = new Thread((Runnable) new GmailClient());
			threads[i].start();
		}
    }

    public void shutdown() throws InterruptedException {
        LOGGER.info("EmailService shutdown");
        GmailClient.die = true;
        // wait for all the threads to finish
        // for (int i = 0; i < nthreads; i++) {
        // threads[i].interrupt();
        // }
        for (int i = 0; i < nthreads; i++) {
            threads[i].join();
        }
    }
}
