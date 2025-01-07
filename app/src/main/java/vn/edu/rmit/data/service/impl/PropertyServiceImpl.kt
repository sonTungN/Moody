package vn.edu.rmit.data.service.impl

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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
        val geoPoint = document.getGeoPoint("geopoint")

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
                propertyTypeService.documentToLocationType(it)
            }?.first() ?: PropertyType(),
            name = document.getString("name") ?: "",
            address = document.getString("address") ?: "",
            geoPoint = geoPoint?.let {
                LatLng(
                    it.latitude,
                    it.longitude
                )
            },
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
}