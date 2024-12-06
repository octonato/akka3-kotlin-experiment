package com.demo.application

import akka.Done
import akka.javasdk.annotations.ComponentId
import akka.javasdk.keyvalueentity.KeyValueEntity
import com.demo.domain.Create
import com.demo.domain.User

@ComponentId("user")
class UserEntity : KeyValueEntity<User>() {

    fun createUser(cmd: Create): Effect<Done> {
        return effects()
            .updateState( User(cmd.name, cmd.email))
            .thenReply(Done.getInstance())
    }

    fun getUser(): Effect<User> {
        return effects().reply(currentState())
    }

}