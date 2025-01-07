package vn.edu.rmit.data.model.type

import androidx.annotation.DrawableRes

data class PropertyType(
    val id: String = "",
    val name: String = "",
    @DrawableRes val image: Int
)