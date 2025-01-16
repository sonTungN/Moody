package vn.edu.rmit.data.model.type

import android.media.Image
import androidx.compose.ui.graphics.Color

data class Coupon(
    val title: String,
    val description: String,
    val discount: Int,
    val expiryDate: String,
    val badge: String,
    val badgeColor: Color
)