package org.acme.emailservice.endpoint;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class TagEndpoint {

    @Query
    public String helloTag() {
        return "Hello Tag!";
    }

    /* @Query
    public List<Tag> getTags(){
        return tagService.getTags();
    } */
}
