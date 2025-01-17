package vn.edu.rmit.data.service.impl

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.PropertyTypeService
import vn.edu.rmit.data.service.VideoService
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class PropertyServiceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val propertyTypeService: PropertyTypeService,
    private val accountService: AccountService,
    private val videoService: VideoService,
    private val moodService: MoodService

) : PropertyService {
    private val propertyRef = db.collection("properties")

    override suspend fun documentToProperty(document: DocumentSnapshot): Property {
        val geoPoint = document.getGeoPoint("geoPoint")

        val travelers =
            document.get("travelers")?.let {
                (it as List<*>).filterIsInstance<DocumentReference>()
            } ?: emptyList()

        val videos =
            document.get("videos")?.let {
                (it as List<*>).filterIsInstance<DocumentReference>()
            } ?: emptyList()

        val moodTags =
            document.get("mood_tags")?.let {
                (it as List<*>).filterIsInstance<DocumentReference>()
            } ?: emptyList()

        return Property(
            id = document.id,
            owner = document.getDocumentReference("owner")?.snapshots()?.map {
                accountService.documentToProfile(it)
            }?.first() ?: Profile(),
            type = document.getDocumentReference("type")?.snapshots()?.map {
                propertyTypeService.documentToPropertyType(it)
            }?.first() ?: PropertyType(),
            name = document.getString("name") ?: "",
            address = document.getString("address") ?: "",
            geoPoint = geoPoint?.let {
                LatLng(
                    it.latitude,
                    it.longitude
                )
            },
            price = document.getLong("price") ?: 0,
            image = document.getString("image") ?: "",
            videos = videos.map { videoDocumentRef ->
                videoDocumentRef.snapshots().map {
                    videoService.documentToVideo(it)
                }.first()
            }.toList(),
            moodTags = moodTags.map { moodTagsDocumentRef ->
                moodTagsDocumentRef.snapshots().map {
                    moodService.documentToMood(it)
                }.first()
            }.toList(),
            openingHours = document.getString("opening_hours")?.let {
                LocalTime.parse(it)
            } ?: LocalTime.of(0, 0),
            closingHours = document.getString("closing_hours")?.let {
                LocalTime.parse(it)
            } ?: LocalTime.of(0, 0),
            travelers = travelers.map { travelersDocumentRef ->
                travelersDocumentRef.snapshots().map {
                    accountService.documentToProfile(it)
                }.first()
            }.toList()
        )
    }

    override suspend fun getProperty(id: String): Property {
        return propertyRef.document(id).get().await().let {
            documentToProperty(it)
        }
    }

    override fun getProperties(): Flow<List<Property>> {
        return propertyRef.snapshots().map { snapshot ->
            snapshot.documents.map { documentToProperty(it) }
        }
    }

    override suspend fun updateProperty(property: Property): Property {
        db.collection("properties").document(property.id).set(
            hashMapOf(
                "name" to property.name,
                "address" to property.address,
                "geoPoint" to property.geoPoint?.let {
                    GeoPoint(it.latitude, it.longitude)
                },
                "price" to property.price,
                "image" to property.image,
                "videos" to property.videos.map { video ->
                    db.collection("videos").document(video.id)
                },
                "mood_tags" to property.moodTags.map { mood ->
                    db.collection("moods").document(mood.id)
                },
                "opening_hours" to property.openingHours.toString(),
                "closing_hours" to property.closingHours.toString(),
                "travelers" to property.travelers.map { traveler ->
                    db.collection("profiles").document(traveler.id)
                },
                "owner" to property.owner.let { db.collection("profiles").document(it.id) }
            )
        ).await()

        return getProperty(property.id)
    }

    override suspend fun addProperty(property: Property): Property {
        val id = UUID.randomUUID()

        db.collection("properties").document(id.toString()).set(
            hashMapOf(
                "name" to property.name,
                "address" to property.address,
                "type" to property.type.let { db.collection("property_type").document(it.id) },
                "geoPoint" to property.geoPoint?.let {
                    GeoPoint(it.latitude, it.longitude)
                },
                "price" to property.price,
                "image" to property.image,
                "videos" to property.videos.map { video ->
                    db.collection("videos").document(video.id)
                },
                "mood_tags" to property.moodTags.map { mood ->
                    db.collection("moods").document(mood.id)
                },
                "opening_hours" to property.openingHours.toString(),
                "closing_hours" to property.closingHours.toString(),
                "travelers" to property.travelers.map { traveler ->
                    db.collection("profiles").document(traveler.id)
                },
                "owner" to property.owner.let { db.collection("profiles").document(it.id) }
            )
        ).await()

        db.collection("profiles").document(property.owner.id)
            .update(
            "ownedProperties", FieldValue.arrayUnion(id.toString())
        ).await()

        return getProperty(id.toString())
    }
}