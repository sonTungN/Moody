package vn.edu.rmit.data.model

data class Profile(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profileUrl: String = "",

    val role: Role = Role(),
//    val membershipType: MembershipType = MembershipType(),

    val savedLocation: List<String> = emptyList(),
    val booking: List<String> = emptyList(),
)