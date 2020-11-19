package org.acme.emailservice.endpoint;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class MessageEndpoint {

    @Query
    public String helloMessage() {
        return "Hello Message!";
    }

    /* @Query
    public List<Message> getMessages(){
        return messageService.getMessages();
    } */
}