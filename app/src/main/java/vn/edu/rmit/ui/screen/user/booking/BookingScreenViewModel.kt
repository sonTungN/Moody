package vn.edu.rmit.ui.screen.user.booking

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stripe.model.PaymentIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.BookingService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.StripeService
import java.time.LocalDate
import javax.inject.Inject

data class BookingUiState(
    val property: Property = Property(),
    val profile: Profile = Profile(),
    val paymentIntent: PaymentIntent? = null
)

@HiltViewModel
class BookingScreenViewModel @Inject constructor(
    state: SavedStateHandle,
    private val propertyService: PropertyService,
    private val accountService: AccountService,
    private val bookingService: BookingService,
    private val stripeService: StripeService
) : ViewModel() {
    val id = state.get<String>("id")

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBooking()
        getProfile()
    }

    private fun getBooking() {
        viewModelScope.launch {
            if (id !== null)
                propertyService.getProperty(id).let { property ->
                    _uiState.update { state ->
                        state.copy(property = property)
                    }
                }
        }
    }

    private fun getProfile() {
        if (id !== null)
            viewModelScope.launch {
                accountService.getProfile()?.let { profile ->
                    _uiState.update { state ->
                        state.copy(profile = profile)
                    }
                }
            }
    }

    fun getPaymentIntent(price: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    stripeService.generatePaymentIntent(price).let { intent ->
                        _uiState.update { state ->
                            state.copy(paymentIntent = intent)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Stripe", "Cannot create payment intent, error: $e")
                }
            }
        }
    }

    fun reserveProperty(property: Property, startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            val userProfile = accountService.getProfile()

            userProfile?.let {
                bookingService.addBooking(
                    Booking(
                        property = property,
                        user = userProfile,
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            }
        }
    }
}