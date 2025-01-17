package vn.edu.rmit.data.service

import com.stripe.model.PaymentIntent

interface StripeService {
    suspend fun generatePaymentIntent(price: Long): PaymentIntent
}