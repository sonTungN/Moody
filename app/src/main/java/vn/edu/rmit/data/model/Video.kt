package vn.edu.rmit.data.model

import vn.edu.rmit.data.model.type.Mood

data class Video(
    val title: String = "",
    val desc: String = "",
    val url: String = "",
    val propertyId: String = "",
    val moodTags: List<String> = emptyList()
)