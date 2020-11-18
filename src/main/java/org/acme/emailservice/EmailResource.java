package org.acme.emailservice;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class EmailResource {

    @Query
    public String hello() {
        return "hello";
    }
}