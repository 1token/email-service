package org.acme.emailservice.endpoint;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class LabelEndpoint {

    @Query
    public String helloLabel() {
        return "Hello Label!";
    }

    /* @Query
    public List<Label> getLabels(){
        return labelService.getLabels();
    } */
}