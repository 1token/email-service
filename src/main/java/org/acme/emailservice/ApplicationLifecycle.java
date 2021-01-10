package org.acme.emailservice;

import java.time.ZoneId;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.configuration.ProfileManager;

import org.acme.emailservice.email.EmailService;
import org.acme.emailservice.service.AccountInitService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationLifecycle {

    Logger log = Logger.getLogger(ApplicationLifecycle.class);

    @ConfigProperty(name = "server.timezone")
    String timeZone;

    private EmailService emailService;

    @Inject
    AccountInitService accountInitService;

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        // System.setProperty("user.timezone", timeZone);
        log.info("System time zone is: " + ZoneId.systemDefault());
        log.info("User time zone is: " + System.getProperty("user.timezone"));
    }

    public void onStart(@Observes StartupEvent event) {

        log.info("The application is starting with " + ProfileManager.getActiveProfile() + " profile");
        emailService = new EmailService();

        // only for dev, create accounts
        if (ProfileManager.getActiveProfile() == "dev") {
            accountInitService.persistAccount();
        }
    }

    public void onStop(@Observes ShutdownEvent event) throws InterruptedException {

        emailService.shutdown();
        log.info("The application is stopping...");
    }
}