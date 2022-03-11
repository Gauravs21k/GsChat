package com.gaurav.gschat

class User {
    lateinit var name: String
    lateinit var email: String
    lateinit var uid: String

    constructor()

    constructor(name : String, email : String, uid : String) {
        this.name = name
        this.email = email
        this.uid = uid
    }
}
