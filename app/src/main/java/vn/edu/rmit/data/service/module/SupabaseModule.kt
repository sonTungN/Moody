package vn.edu.rmit.data.service.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import vn.edu.rmit.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    fun provideSupabase() =
        createSupabaseClient(
            BuildConfig.SUPABASE_ENDPOINT,
            BuildConfig.SUPABASE_ANON_KEY
        ) {
            //Already the default serializer, but you can provide a custom Json instance (optional):
            defaultSerializer = KotlinXSerializer()
            install(Storage)
        }
}