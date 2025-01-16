package vn.edu.rmit.data.service.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    fun provideSupabase() =
        createSupabaseClient(
            "https://yowjbglnodagjukzsbom.supabase.co",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inlvd2piZ2xub2RhZ2p1a3pzYm9tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzcwMzkyMjcsImV4cCI6MjA1MjYxNTIyN30.mGz9kgS8nxW2L0XKOQ01IdXZr3llMYEfk0WsnNMwfe0"
        ) {
            //Already the default serializer, but you can provide a custom Json instance (optional):
            defaultSerializer = KotlinXSerializer()
            install(Storage)
        }
}