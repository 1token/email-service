package org.acme.emailservice.graphql;

import java.util.List;
import java.util.Optional;

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
    public Message getMessage(Long id) throws RecordNotFound {
        Optional<Message> result = messageService.getMessage(username, id);
        return result.orElseThrow(() -> new RecordNotFound("Message not found"));
    }

    @Query
    public List<Message> getMessages() {
        return messageService.getMessages(username);
    }

    @Mutation
    public Message createMessage(Message message) throws RecordNotFound {
        Optional<Message> result = messageService.updateOrCreate(username, message);
        return result.orElseThrow(() -> new RecordNotFound("Message not created"));
    }

    @Mutation
    public Message updateMessage(Message message) throws RecordNotFound {
        Optional<Message> result = messageService.updateOrCreate(username, message);
        return result.orElseThrow(() -> new RecordNotFound("Message not found"));
    }

    @Mutation
    public Message deleteMessage(Long id) throws RecordNotFound {
        Optional<Message> result = messageService.delete(username, id);
        return result.orElseThrow(() -> new RecordNotFound("Message not found"));
    }
}