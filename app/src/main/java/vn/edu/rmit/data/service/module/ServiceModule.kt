package vn.edu.rmit.data.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.BookingService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.PropertyTypeService
import vn.edu.rmit.data.service.RoleService
import vn.edu.rmit.data.service.VideoActionService
import vn.edu.rmit.data.service.VideoService
import vn.edu.rmit.data.service.impl.AccountServiceImpl
import vn.edu.rmit.data.service.impl.BookingServiceImpl
import vn.edu.rmit.data.service.impl.MoodServiceImpl
import vn.edu.rmit.data.service.impl.PropertyServiceImpl
import vn.edu.rmit.data.service.impl.PropertyTypeServiceImpl
import vn.edu.rmit.data.service.impl.RoleServiceImpl
import vn.edu.rmit.data.service.impl.VideoActionServiceImpl
import vn.edu.rmit.data.service.impl.VideoServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun providePropertyService(impl: PropertyServiceImpl): PropertyService

    @Binds
    abstract fun providePropertyTypeService(impl: PropertyTypeServiceImpl): PropertyTypeService

    @Binds
    abstract fun provideMoodService(impl: MoodServiceImpl): MoodService

    @Binds
    abstract fun provideRoleService(impl: RoleServiceImpl): RoleService

    @Binds
    abstract fun provideVideoService(impl: VideoServiceImpl): VideoService

    @Binds
    abstract fun provideVideoActionService(impl: VideoActionServiceImpl): VideoActionService

    @Binds
    abstract fun provideBookingService(impl: BookingServiceImpl): BookingService
}
