package vn.edu.rmit.data.service.impl

import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.BookingService
import vn.edu.rmit.data.service.PropertyService
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.UUID
import javax.inject.Inject

class BookingServiceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val database: FirebaseDatabase,
    private val accountService: AccountService,
    private val propertyService: PropertyService,
) : BookingService {
    override suspend fun documentToBooking(document: DocumentSnapshot): Booking {
        return Booking(
            id = document.id,
            startDate = document.getDate("startDate")?.let {
                LocalDate.ofInstant(it.toInstant(), ZoneOffset.UTC)
            } ?: LocalDate.now(),
            endDate = document.getDate("endDate")?.let {
                LocalDate.ofInstant(it.toInstant(), ZoneOffset.UTC)
            } ?: LocalDate.now(),
            property = document.getDocumentReference("property")?.get()?.await()?.let {
                propertyService.documentToProperty(it)
            } ?: Property(),
            user = document.getDocumentReference("user")?.get()?.await()?.let {
                accountService.documentToProfile(it)
            } ?: Profile(),
        )
    }

    override suspend fun getBookingById(id: String): Booking {
        return db.collection("booking").document(id).get().await().let {
            documentToBooking(it)
        }
    }

    override suspend fun addBooking(booking: Booking): Booking {
        db.collection("booking").document(booking.id).set(booking.let {
            hashMapOf(
                "startDate" to Timestamp(it.startDate.atStartOfDay().toInstant(ZoneOffset.UTC)),
                "endDate" to Timestamp(it.endDate.atStartOfDay().toInstant(ZoneOffset.UTC)),
                "property" to db.collection("properties").document(it.property.id),
                "user" to db.collection("profiles").document(it.user.id)
            )
        }).await()

        return getBookingById(booking.id)
    }

    override suspend fun findBookings(propertyId: String?, userId: String?): List<Booking> {
        var query: Query = db.collection("booking")

        if (propertyId !== null) query = query.whereEqualTo("property", db.collection("properties").document(propertyId))

        if (userId !== null) query = query.whereEqualTo("user", db.collection("profiles").document(userId))

        return query.get().await().documents.map {
            documentToBooking(it)
        }
    }
}