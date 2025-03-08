package vn.edu.rmit.ui.screen.user.booking

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import vn.edu.rmit.BuildConfig
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.property.RoomDetails
import vn.edu.rmit.ui.component.select.DateRangePickerModal
import vn.edu.rmit.ui.component.select.RoomPickerModal
import vn.edu.rmit.ui.component.select.getFormattedDate
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    id: String,
    viewModel: BookingScreenViewModel = hiltViewModel(),
    paymentComplete: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val paymentSheet = rememberPaymentSheet({ paymentSheetResult ->
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.e("Payment", "Payment cancelled")
            }

            is PaymentSheetResult.Failed -> {
                Log.e("Payment", "Payment error: ${paymentSheetResult.error}")
            }

            is PaymentSheetResult.Completed -> {
                Log.i("Payment", "Payment completed")
                paymentComplete()
            }
        }
    })
    val context = LocalContext.current

    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>?>(null) }
    var selectedRooms by remember { mutableIntStateOf(1) }
    var showDateModal by remember { mutableStateOf(false) }
    var showRoomModal by remember { mutableStateOf(false) }

    val startDate by remember {
        derivedStateOf {
            selectedDateRange?.first?.let {
                Instant.ofEpochMilli(
                    it
                )
            }
        }
    }

    val endDate by remember {
        derivedStateOf {
            selectedDateRange?.second?.let {
                Instant.ofEpochMilli(
                    it
                )
            }
        }
    }

    val price by remember {
        derivedStateOf {
            ChronoUnit.DAYS.between(
                startDate ?: Instant.ofEpochMilli(Instant.now().toEpochMilli()),
                endDate ?: Instant.ofEpochMilli(Instant.now().toEpochMilli())
            ) * uiState.property.price * selectedRooms
        }
    }

    LaunchedEffect(context) {
        PaymentConfiguration.init(context, BuildConfig.STRIPE_PUBLISHABLE_KEY)
    }

    if (showDateModal) {
        DateRangePickerModal(
            onDateRangeSelected = {
                selectedDateRange = it
                showDateModal = false
                if (it.first != null && it.second != null)
                    viewModel.getPaymentIntent(
                        price
                    )
            },
            onDismiss = { showDateModal = false }
        )
    }

    if (showRoomModal) {
        RoomPickerModal(
            initialRooms = selectedRooms,
            onDismiss = { showRoomModal = false },
            onConfirm = { rooms ->
                selectedRooms = rooms
                showRoomModal = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.booking_details),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            RoomDetails(uiState.property)

            val dateRangeText = if (selectedDateRange != null) {
                val (startDate, endDate) = selectedDateRange!!
                val formattedStartDate = startDate?.let {
                    getFormattedDate(it)
                } ?: "Start"
                val formattedEndDate = endDate?.let {
                    getFormattedDate(it)
                } ?: "End"
                "$formattedStartDate - $formattedEndDate"
            } else {
                stringResource(R.string.choose_date_range)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(
                    onClick = { showDateModal = true },
                ) {
                    Icon(
                        Icons.Filled.CalendarToday, dateRangeText, modifier = Modifier.size(
                            ButtonDefaults.IconSize
                        )
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(dateRangeText)
                }
                FilledTonalButton(
                    onClick = { showRoomModal = true },
                ) {
                    Icon(
                        Icons.Filled.Chair,
                        stringResource(R.string.select_room_amount),
                        modifier = Modifier.size(
                            ButtonDefaults.IconSize
                        )
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("${stringResource(R.string.select_room_amount)} $selectedRooms")
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (price > 0)
                    Text(
                        NumberFormat.getCurrencyInstance().apply {
                            maximumFractionDigits = 0
                            currency = Currency.getInstance("VND")
                        }.format(price), modifier = Modifier.weight(1f)
                    )
                else Text(
                    "${
                        NumberFormat.getCurrencyInstance().apply {
                            maximumFractionDigits = 0
                            currency = Currency.getInstance("VND")
                        }.format(uiState.property.price)
                    }/night",
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        uiState.paymentIntent?.let {
                            presentPaymentSheet(paymentSheet, it.clientSecret)
                        }
                        if (startDate != null && endDate != null)
                            viewModel.reserveProperty(
                                uiState.property, startDate = LocalDate.ofInstant(
                                    startDate!!,
                                    ZoneOffset.UTC
                                ), endDate = LocalDate.ofInstant(
                                    endDate!!,
                                    ZoneOffset.UTC
                                )
                            )
                    },
                ) {
                    Text(stringResource(R.string.reserve))
                }
            }
        }
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
            merchantDisplayName = "MoodScape",
            // Set `allowsDelayedPaymentMethods` to true if your business handles
            // delayed notification payment methods like US bank accounts.
            allowsDelayedPaymentMethods = true,
            googlePay = googlePayConfiguration
        )
    )
}