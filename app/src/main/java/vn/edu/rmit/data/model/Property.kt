package vn.edu.rmit.data.model

import com.google.android.gms.maps.model.LatLng
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.data.model.type.PropertyType
import java.time.LocalTime

data class Property(
    val id: String = "",
    val owner: Profile = Profile(),
    val type: PropertyType = PropertyType(),
    val name: String = "",
    val address: String = "",
    val geoPoint: LatLng? = null,
    val price: Long = 0,

    val image: String = "",
    val videos: List<Video> = emptyList(),
    val moodTags: List<Mood> = emptyList(),

    val openingHours: LocalTime = LocalTime.of(0, 0),
    val closingHours: LocalTime = LocalTime.of(23, 59),

    val travelers: List<Profile> = emptyList()
)
