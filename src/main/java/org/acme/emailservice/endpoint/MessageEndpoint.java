package org.acme.emailservice.endpoint;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.acme.emailservice.exception.RecordNotFound;
import org.acme.emailservice.model.Message;
import org.acme.emailservice.service.MessageService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

// import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

@GraphQLApi
@RolesAllowed({ "user", "admin" })
public class MessageEndpoint {

    @Inject
    SecurityIdentity identity;

    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim(standard = Claims.email)
    String email;

    @Inject
    MessageService messageService;

    @Query
    public String helloMessage() {
        return "Hello Message!";
    }

    @Query
    public Message getMessage(Long id) {
        return messageService.getMessage(id);
    }

    @Query
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @Mutation
    public Message updateMessage(Message message) throws RecordNotFound {
        return messageService.updateOrCreate(message);
    }

    @Mutation
    public Message deleteMessage(Long id) {
        return messageService.delete(id);
    }
}