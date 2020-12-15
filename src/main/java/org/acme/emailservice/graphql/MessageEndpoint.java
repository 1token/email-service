package org.acme.emailservice.graphql;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.acme.emailservice.model.Message;
import org.acme.emailservice.service.MessageService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@GraphQLApi
@RolesAllowed({ "user", "admin" })
public class MessageEndpoint {

    @Inject
    @Claim(standard = Claims.preferred_username)
    String username;

    @Inject
    MessageService messageService;

    @Query
    public String helloMessage() {
        return "Hello Message!";
    }

    @Query
    public Message getMessage(Long id) {
        return messageService.getMessage(username, id);
    }

    @Query
    public List<Message> getMessages() {
        return messageService.getMessages(username);
    }

    @Mutation
    public Message createMessage(Message message) {
        return messageService.updateOrCreate(username, message);
    }

    @Mutation
    public Message updateMessage(Message message) {
        return messageService.updateOrCreate(username, message);
    }

    @Mutation
    public Message deleteMessage(Long id) {
        return messageService.delete(username, id);
    }
}