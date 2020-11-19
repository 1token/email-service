package org.acme.emailservice.endpoint;

import java.util.List;

import javax.inject.Inject;

import org.acme.emailservice.model.Tag;
import org.acme.emailservice.service.TagService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class TagEndpoint {

    @Inject
    TagService tagService;

    @Query
    public String helloTag() {
        return "Hello Tag!";
    }

    @Query
    public List<Tag> getTags() {
        return tagService.getTags();
    }
}
