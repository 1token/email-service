package org.acme.emailservice.endpoint;

import java.util.List;

import javax.inject.Inject;

import org.acme.emailservice.model.Message;
import org.acme.emailservice.service.MessageService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class MessageEndpoint {

    @Inject
    MessageService messageService;

    @Query
    public String helloMessage() {
        return "Hello Message!";
    }

    @Query
    public Message getMessage(Long id){
        return messageService.getMessage(id);
    }

    @Query
    public List<Message> getMessages() {
        return messageService.getMessages();
    }
}