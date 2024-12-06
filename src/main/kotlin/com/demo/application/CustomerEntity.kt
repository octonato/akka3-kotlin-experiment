package com.demo.application

import akka.Done
import akka.javasdk.annotations.ComponentId
import akka.javasdk.eventsourcedentity.EventSourcedEntity
import com.demo.domain.Create
import com.demo.domain.User

@ComponentId("user-es")
class CustomerEntity : EventSourcedEntity<User, User.Event>() {

    fun createUser(command: Create): Effect<Done> {
        return effects().persist(User.Event.UserCreated(command.name, command.email)).thenReply { Done.getInstance() }
    }
    override fun applyEvent(event: User.Event?): User {
         when(event) {
            is User.Event.UserCreated -> return User(event.name, event.email)
            null -> throw IllegalStateException("Unexpected event")
        }
    }
}