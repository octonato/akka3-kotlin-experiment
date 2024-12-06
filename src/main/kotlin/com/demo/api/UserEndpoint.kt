package com.demo.api
import akka.javasdk.annotations.Acl
import akka.javasdk.annotations.http.Get
import akka.javasdk.annotations.http.HttpEndpoint
import akka.javasdk.annotations.http.Post
import akka.javasdk.client.ComponentClient
import com.demo.application.CustomerEntity
import com.demo.application.UserEntity
import com.demo.domain.Create
import com.demo.domain.User
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

/**
 * This is a simple Akka Endpoint that returns "Hello World!".
 * Locally, you can access it by running `curl http://localhost:9000/hello`.
 */
// Opened up for access from the public internet to make the service easy to try out.
// For actual services meant for production this must be carefully considered, and often set more limited
@Acl(allow = [Acl.Matcher(principal = Acl.Principal.INTERNET)])
@HttpEndpoint("/users")
class UserEndpoint(val componentClient: ComponentClient) {

    @Post("/create/{name}/{email}")
    fun create2(name: String, email: String): CompletionStage<String> {
        componentClient
            .forEventSourcedEntity(name)
            .method(CustomerEntity::createUser)
            .invokeAsync(Create(name, email))

        return CompletableFuture.completedStage("Hel=lo World!")
    }

    @Post("/{name}/{email}")
    fun create(name: String, email: String): CompletionStage<String> {
        componentClient
            .forKeyValueEntity(name)
            .method(UserEntity::createUser)
            .invokeAsync(Create(name, email))

        return CompletableFuture.completedStage("Hel=lo World!")
    }

    @Get("/{name}")
    fun getUser(name:String): CompletionStage<User> {
        return componentClient
            .forKeyValueEntity(name)
            .method(UserEntity::getUser)
            .invokeAsync()
    }
}
