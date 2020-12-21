package org.acme.emailservice;

import java.time.ZoneId;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.configuration.ProfileManager;

import org.acme.emailservice.email.EmailService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationLifecycle {

    Logger logger = Logger.getLogger(ApplicationLifecycle.class);

    @ConfigProperty(name = "server.timezone")
    String timeZone;

    private EmailService emailService;

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        // System.setProperty("user.timezone", timeZone);
        logger.info("System time zone is: " + ZoneId.systemDefault());
        logger.info("User time zone is: " + System.getProperty("user.timezone"));
    }

    public void onStart(@Observes StartupEvent event) {

        logger.info("The application is starting with " + ProfileManager.getActiveProfile() + " profile");
        emailService = new EmailService();
    }

    public void onStop(@Observes ShutdownEvent event) throws InterruptedException {

        emailService.shutdown();
        logger.info("The application is stopping...");
    }
}