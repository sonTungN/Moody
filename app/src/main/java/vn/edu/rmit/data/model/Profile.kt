package vn.edu.rmit.data.model

import vn.edu.rmit.data.model.type.Role

data class Profile(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profileUrl: String = "",

    val role: Role = Role(),

    val savedLocation: List<String> = emptyList(),
    val booking: List<String> = emptyList(),
)