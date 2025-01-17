package vn.edu.rmit.ui.screen.user.reserve

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stripe.model.PaymentIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.StripeService
import javax.inject.Inject

data class ReserveScreenState(
    val profile: Profile = Profile(),
    val properties: List<Property> = emptyList(),
    val paymentIntent: PaymentIntent? = null,
)

@HiltViewModel
class ReserveScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val propertyService: PropertyService,
    private val stripeService: StripeService
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReserveScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
        getReservation()
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

    private fun getProfile() {
        viewModelScope.launch {
            accountService.getProfile()?.let { profile ->
                _uiState.update { state ->
                    state.copy(profile = profile)
                }
            }
        }
    }

    private fun getReservation() {
        viewModelScope.launch {
            val profile = accountService.getProfile()
            profile?.let { userProfile ->
                val properties = propertyService.getProperties().first()
                val userProperties = userProfile.booking.mapNotNull { bookingId ->
                    properties.find { property -> property.id == bookingId }
                }
                _uiState.update { state ->
                    state.copy(properties = userProperties)
                }
                getPaymentIntent(userProperties.map { it.price }.reduce { a, b ->
                    a + b
                })
            }
        }
    }
}