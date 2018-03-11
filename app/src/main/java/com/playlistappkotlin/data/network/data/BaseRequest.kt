package com.playlistappkotlin.data.network.data

class BaseRequest {

    private var userid: Int = 0
    private var token: String? = null
    private val devtype = "Android"

    constructor() {
        // Empty constructor
    }

    constructor(userId: Int, token: String) {
        this.userid = userId
        this.token = token
    }

    fun setUserId(userId: Int) {
        this.userid = userId
    }

    fun setToken(token: String) {
        this.token = token
    }
}
