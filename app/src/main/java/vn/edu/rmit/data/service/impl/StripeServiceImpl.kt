package vn.edu.rmit.data.service.impl

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import vn.edu.rmit.BuildConfig
import vn.edu.rmit.data.service.StripeService
import javax.inject.Inject

/**
 * Mock payment (server-side) SDK for DEVELOPMENT ONLY
 */
class StripeServiceImpl @Inject constructor() : StripeService {
    override suspend fun generatePaymentIntent(price: Long): PaymentIntent {
        Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY

        val params = PaymentIntentCreateParams.builder().setAmount(price).setCurrency("vnd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
            ).build();

        return PaymentIntent.create(params);
    }
}