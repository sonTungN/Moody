package vn.edu.rmit.data.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.RoleService
import vn.edu.rmit.data.service.VideoService
import vn.edu.rmit.data.service.impl.AccountServiceImpl
import vn.edu.rmit.data.service.impl.MoodServiceImpl
import vn.edu.rmit.data.service.impl.RoleServiceImpl
import vn.edu.rmit.data.service.impl.VideoServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

//    @Binds
//    abstract fun provideLocationService(impl: LocationServiceImpl): LocationService
//
//    @Binds
//    abstract fun provideDonationService(impl: DonationServiceImpl): DonationService
//
//    @Binds
//    abstract fun provideBloodTypeService(impl: BloodTypeServiceImpl): BloodTypeService
//
    @Binds
    abstract fun provideMoodService(impl: MoodServiceImpl): MoodService

    @Binds
    abstract fun provideRoleService(impl: RoleServiceImpl): RoleService

    @Binds
    abstract fun provideVideoService(impl: VideoServiceImpl): VideoService
//
//    @Binds
//    abstract fun provideLocationTypeService(impl: LocationTypeServiceImpl): LocationTypeService
//
//    @Binds
//    abstract fun provideNotificationService(impl: NotificationServiceImpl): NotificationService
}
