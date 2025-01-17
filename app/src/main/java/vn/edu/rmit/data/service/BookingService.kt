package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import vn.edu.rmit.data.model.Booking

interface BookingService {
    suspend fun documentToBooking(document: DocumentSnapshot): Booking
    suspend fun getBookingById(id: String): Booking
    suspend fun addBooking(booking: Booking): Booking
    suspend fun findBookings(propertyId: String? = null, userId: String? = null): List<Booking>
}