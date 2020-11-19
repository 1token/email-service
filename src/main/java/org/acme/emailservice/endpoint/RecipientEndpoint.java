package org.acme.emailservice.endpoint;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class RecipientEndpoint {

    @Query
    public String helloRecipient() {
        return "Hello Recipient!";
    }

    /* @Query
    public List<Recipient> getRecipients(){
        return recipientService.getRecipients();
    } */
}
