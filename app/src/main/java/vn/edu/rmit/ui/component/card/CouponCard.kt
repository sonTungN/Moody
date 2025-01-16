package vn.edu.rmit.ui.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.rmit.R
import vn.edu.rmit.data.model.type.Coupon

@Composable
fun CouponCard(
    coupon: Coupon,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFEBF8E1) else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = if (isSelected) CardDefaults.cardElevation(8.dp) else CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row with Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = coupon.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = coupon.badge,
                    color = coupon.badgeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(coupon.badgeColor.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = coupon.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Terms and Expiry
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Expires on: ${coupon.expiryDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                RadioButton(
                    selected = isSelected,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun PaymentScreenWithCoupons(
    onPaymentComplete: (selectedCoupon: Coupon?) -> Unit
) {
    var selectedCoupon by remember { mutableStateOf<Coupon?>(null) }

    // Example coupons
    val coupons = listOf(
        Coupon(
            title = "discount $5",
            description = "Valid for all payment methods.",
            discount = 5,
            expiryDate = "31/07/2023",
            badge = "Freeship",
            badgeColor = Color(0xFF4CAF50)
        ),
        Coupon(
            title = "discount $10",
            description = "Get 20% off your order!",
            discount = 10,
            expiryDate = "25/07/2023",
            badge = "Discount",
            badgeColor = Color(0xFFFFC107)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.payment_details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        // Coupon Section
        Text("Available Coupons", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(coupons) { coupon ->
                CouponCard(
                    coupon = coupon,
                    isSelected = coupon == selectedCoupon,
                    onClick = { selectedCoupon = coupon }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to Complete Payment
        Button(
            onClick = { onPaymentComplete(selectedCoupon) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Complete Payment")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponCardPreview() {
    CouponCard(
        Coupon(
            title = "Free Delivery",
            description = "Valid for all payment methods.",
            discount = 5,
            expiryDate = "31/07/2023",
            badge = "Freeship",
            badgeColor = Color(0xFF4CAF50)
        ),
        isSelected = false
    ) { }
}