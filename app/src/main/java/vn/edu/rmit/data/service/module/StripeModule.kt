package vn.edu.rmit.data.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vn.edu.rmit.data.service.StripeService
import vn.edu.rmit.data.service.impl.StripeServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class StripeModule {
    @Binds
    abstract fun provideStripe(impl: StripeServiceImpl): StripeService
}