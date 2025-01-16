package vn.edu.rmit.data.service.module

import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.edu.rmit.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object GoogleMapsModule {
    @Provides
    fun provideGeoApiContext() =
        GeoApiContext.Builder().apiKey(BuildConfig.MAPS_API_KEY).build()

}