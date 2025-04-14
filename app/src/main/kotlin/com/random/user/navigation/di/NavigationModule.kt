package com.random.user.navigation.di

import com.random.user.presentation.navigation.FeatureNavigation
import com.random.users.users.navigation.UsersNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @IntoSet
    fun provideFeatureNavigation(): FeatureNavigation = UsersNavigationImpl()
}
