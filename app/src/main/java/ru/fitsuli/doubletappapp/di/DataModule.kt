package ru.fitsuli.doubletappapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.fitsuli.doubletappapp.data.repository.HabitRepositoryImpl
import ru.fitsuli.doubletappapp.data.storage.local.LocalDataSource
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.data.storage.network.RemoteDataSource
import ru.fitsuli.doubletappapp.domain.HabitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        local: LocalDataSource,
        remote: RemoteDataSource
    ): HabitRepository = HabitRepositoryImpl(local, remote)

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource =
        LocalStorage(context)

    @Provides
    @Singleton
    fun provideLRemoteDataSource(): RemoteDataSource =
        NetworkStorage()
}