package com.app.domain.entity.request

//data class SignInRequest(
//    var mobile: String,
//    var password: String,
//    var deviceId: String
//)

data class SignInRequest(
    var email: String?,
    var password: String?
)

data class SignUpRequest(
    var email: String,
    var password: String,
    var confirmPassword: String
)

data class FirebaseRequest(
    var email: String,
    var password: String,
)
