package com.app.domain.entity.response


data class UserLaunchData(
    var firstTime: Boolean?
)


data class SignInData(
    var fact: String?,
    val length: Int
)

data class SignUpData(
    var fact: String?,
    val length: Int
)