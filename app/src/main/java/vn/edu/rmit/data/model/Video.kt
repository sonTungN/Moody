package vn.edu.rmit.data.model

data class Video(
    val title: String = "",
    val desc: String = "",
    val url: String = "",

    val moodTags: List<Mood> = emptyList()
)