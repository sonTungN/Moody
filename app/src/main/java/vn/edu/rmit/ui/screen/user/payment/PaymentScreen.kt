package vn.edu.rmit.ui.screen.user.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.ui.screen.user.property.PropertyScreenViewModel

@Composable
fun PaymentScreen(
    startDate: String,
    endDate: String,
    amount: String,
    onPaymentComplete: (cardNumber: String, expiryDate: String, cvv: String, couponCode: String) -> Unit,
    viewModel: PropertyScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var couponCode by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf(0) }
    var discountMessage by remember { mutableStateOf("") }

    val subtotal = amount.toDoubleOrNull() ?: 0.0
    val tax = subtotal * 0.1
    val discountAmount = subtotal * discount / 100
    val total = subtotal + tax - discountAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.payment_details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        // Order Details Section (read-only)
        Text(
            text = stringResource(R.string.order_details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = uiState.property.name,
            onValueChange = {},
            label = { Text(stringResource(R.string.property)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = {},
            label = { Text(stringResource(R.string.start_date)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = {},
            label = { Text(stringResource(R.string.end_date)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {},
            label = { Text(stringResource(R.string.amount)) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        HorizontalDivider()

        // Coupon Section
        Text(
            text = stringResource(R.string.coupon),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = couponCode,
                onValueChange = { couponCode = it },
                label = { Text(stringResource(R.string.coupon_code)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                when (couponCode.lowercase()) {
                    "10" -> {
                        discount = 10
                        discountMessage = "10% discount applied successfully!"
                    }
                    else -> {
                        discount = 0
                        discountMessage = R.string.invalid_coupon_code.toString()
                    }
                }
            }) {
                Text(stringResource(R.string.apply))
            }
        }

        if (discountMessage.isNotEmpty()) {
            Text(discountMessage, color = if (discount > 0) Color.Green else Color.Red)
        }

        HorizontalDivider()

        // Payment Details Section
        Text(
            text = stringResource(R.string.payment_details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text(stringResource(R.string.card_number)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = expiryDate,
            onValueChange = { expiryDate = it },
            label = { Text(stringResource(R.string.expiry_date_mm_yy)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text(stringResource(R.string.cvv)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Order Summary Section
        Text(
            text = stringResource(R.string.order_summary),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.subtotal))
                Text("$${"%.2f".format(subtotal)}")
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tax")
                Text("$${"%.2f".format(tax)}")
            }

            if (discount > 0) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Discount ($discount%)", color = Color.Green)
                    Text("- $${"%.2f".format(discountAmount)}", color = Color.Green)
                }
            }

            HorizontalDivider()

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontWeight = FontWeight.Bold)
                Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Complete Payment Button
        Button(
            onClick = {
                onPaymentComplete(cardNumber, expiryDate, cvv, couponCode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.complete_payment))
        }
    }
}


@Preview(showBackground = true, heightDp = 1000)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(
        startDate = "2025-01-01",
        endDate = "2025-01-05",
        amount = "500.00",
        onPaymentComplete = { _, _, _, _ -> }
    )
}
