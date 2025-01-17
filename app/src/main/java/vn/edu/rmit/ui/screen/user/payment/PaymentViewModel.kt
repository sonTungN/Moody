package vn.edu.rmit.ui.screen.user.payment

import android.util.Log
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
import vn.edu.rmit.data.service.StripeService
import javax.inject.Inject

data class PaymentState(
    val paymentIntent: PaymentIntent? = null
)

@HiltViewModel
class PaymentViewModel
@Inject
constructor(
    private val stripeService: StripeService
) : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentState())
    val uiState = _uiState.asStateFlow()

    init {
        getPaymentIntent()
    }

    fun getPaymentIntent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    stripeService.generatePaymentIntent(1000000L).let { intent ->
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
}