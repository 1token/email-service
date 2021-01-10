package org.acme.emailservice.email;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.acme.emailservice.model.Account;
import org.jboss.logging.Logger;

public class EmailClient implements Runnable {

    private static Logger log = Logger.getLogger(EmailClient.class);

    private ScheduledExecutorService scheduler;
    private SynchronizedTask runningTask;

    public static boolean die = false;

    public EmailClient(Account account) {
        // USER_NAME = getProperty("email.username");
        // created = true;
        // emails = new HashSet<Mail>();

        scheduler = Executors.newScheduledThreadPool(0);
        runningTask = new SynchronizedTask();
        // OAuth2Authenticator.initialize();

        log.info("EmailClient created");
    }

    public void run() {

        while (!die) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.info("Interrupted");
                return;
            }
            continue;
        }
        try {
            this.shutdown();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("EmailClient thread exited gracefully");
    }

    public void shutdown() throws InterruptedException {
        log.info("EmailClient shutdown");
        if (runningTask != null)
        runningTask.stop();
        if (scheduler != null)
        scheduler.shutdownNow();
    }

    class SynchronizedTask implements Runnable {

        boolean stop;
        int load;
        boolean settingCode;

        public SynchronizedTask() {
            log.info("EmailClient/SynchronizedTask create");
            scheduler.scheduleWithFixedDelay(this, 20, 20, TimeUnit.SECONDS);
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
                    if (!this.equals(runningTask)) {
                        stop();
                        return;
                    }
                    if (load != 0 && !settingCode) {
                        log.info("EmailClient/SynchronizedTask run");
                    } else
                        load++;
                }
            } catch (Exception e) {
                while (!Thread.interrupted()) {
                    stop = true;
                    Thread.currentThread().interrupt();
                    log.info("EmailClient/SynchronizedTask Exception interupted");
                }
            }
        }

        public void stop() {
            while (!Thread.interrupted()) {
                stop = true;
                Thread.currentThread().interrupt();
                log.info("EmailClient/SynchronizedTask interrupted");
            }
        }
    }
}
