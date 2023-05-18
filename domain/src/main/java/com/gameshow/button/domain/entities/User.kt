package com.gameshow.button.domain.entities

data class User(
    val socketID: String = "",
    val nickname: String = "",
    val avatarID: String = "",
    val isClicked: Boolean = false,
    val isAdmin: Boolean = false,
)
