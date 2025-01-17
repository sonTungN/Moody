package vn.edu.rmit.ui.screen.manager.property

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.PropertyTypeService
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

data class PropertyScreenState(
    val propertyId: String? = "",
    val property: Property = Property(),
    val propertyTypes: List<PropertyType> = emptyList(),
    val moodTags: List<Mood> = emptyList()
)

@HiltViewModel
class ManagerPropertyViewModel @Inject constructor(
    state: SavedStateHandle,
    private val videoService: VideoService,
    private val propertyService: PropertyService,
    private val propertyTypeService: PropertyTypeService,
    private val moodService: MoodService,
    private val supabaseClient: SupabaseClient,
    private val application: Application
) : ViewModel() {
    private val propertyId = state.get<String>("id")

    private val _uiState = MutableStateFlow(PropertyScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val propertyType = propertyTypeService.getPropertyTypes()
            val moods = moodService.getMoods()
            val property = propertyId?.let {
                propertyService.getProperty(it)
            } ?: Property()

            _uiState.update {
                it.copy(
                    propertyId = propertyId,
                    property = property,
                    propertyTypes = propertyType,
                    moodTags = moods,
                )
            }
        }
    }

    fun updateProperty(property: Property, onUpdate: () -> Unit) {
        viewModelScope.launch {
            propertyService.updateProperty(property)
            onUpdate()
        }
    }

    fun upload(uri: Uri, property: Property, video: Video) {
        viewModelScope.launch {
            try {
                application.applicationContext.contentResolver.openInputStream(uri)
                    ?.use { it.buffered().readBytes() }?.let { file ->
                        val response = supabaseClient.storage.from("videos")
                            .upload("${uri.pathSegments.last()}.mp4", file)

                        Log.i("Upload", "Upload successfully!, info: $response")

                        val video = videoService.addVideo(
                            video.copy(
                                url = supabaseClient.storage.from("videos").publicUrl(response.path)
                            )
                        )
                        val property = propertyService.updateProperty(
                            property.copy(
                                videos = property.videos.plus(
                                    video
                                )
                            )
                        )
                    }
            } catch (e: Exception) {
                Log.e("Upload", "Failed to upload video to database, reason: $e")
            }
        }
    }


}