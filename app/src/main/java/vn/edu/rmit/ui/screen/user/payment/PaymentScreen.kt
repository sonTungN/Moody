package vn.edu.rmit.ui.screen.user.payment

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import io.ktor.websocket.Frame.Text
import vn.edu.rmit.BuildConfig

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val paymentSheet = rememberPaymentSheet(::onPaymentSheetResult)
    val context = LocalContext.current

    LaunchedEffect(context) {
        PaymentConfiguration.init(context, BuildConfig.STRIPE_PUBLISHABLE_KEY)
    }

    Button(
        onClick = {
            uiState.paymentIntent?.let {
                presentPaymentSheet(paymentSheet, it.clientSecret)
            }

        }
    ) {
        Text("Checkout")
    }
}


private fun presentPaymentSheet(
    paymentSheet: PaymentSheet,
    paymentIntentClientSecret: String
) {
    val googlePayConfiguration = PaymentSheet.GooglePayConfiguration(
        environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
        countryCode = "VN",
        currencyCode = "VND"
    )

    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "My merchant name",
            // Set `allowsDelayedPaymentMethods` to true if your business handles
            // delayed notification payment methods like US bank accounts.
            allowsDelayedPaymentMethods = true,
            googlePay = googlePayConfiguration
        )
    )
}

private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
    when (paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            print("Canceled")
        }

        is PaymentSheetResult.Failed -> {
            print("Error: ${paymentSheetResult.error}")
        }

        is PaymentSheetResult.Completed -> {
            // Display for example, an order confirmation screen
            print("Completed")
        }
    }
}