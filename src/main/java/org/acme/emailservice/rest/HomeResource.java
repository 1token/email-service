package org.acme.emailservice.rest;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/")
@RequestScoped
// @ApplicationScoped
public class HomeResource {

    @ConfigProperty(name = "OIDC_AUTH_SERVER_URL")
    String oidcAuthServerUrl;

    @Inject
    Template index;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return index.data("oidcAuthServerUrl", oidcAuthServerUrl);
    } 
}
