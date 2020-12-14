package org.acme.emailservice.graphql;

import java.util.List;

import javax.inject.Inject;

import org.acme.emailservice.model.User;
import org.acme.emailservice.service.UserService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class UserEndpoint {

    @Inject
    UserService userService;

    @Query
    public String helloUserg() {
        return "Hello User!";
    }

    @Query
    public User getUser(Long id){
        return userService.getUser(id);
    }

    @Query
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Mutation
    public User updateUser(User user){
        return userService.updateOrCreate(user);
    }
    
    @Mutation
    public User deleteUser(Long id){
        return userService.delete(id);
    }
}
