package com.demo.domain

data class User(val name: String, val email: String) {
    sealed interface Event {
        data class UserCreated(val name: String, val email: String) : Event
    }
}


