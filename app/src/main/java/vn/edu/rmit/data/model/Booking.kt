package vn.edu.rmit.data.model

import java.time.LocalDate
import java.util.UUID

data class Booking(
    val id: String = UUID.randomUUID().toString(),
    val property: Property,
    val user: Profile,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now()
)