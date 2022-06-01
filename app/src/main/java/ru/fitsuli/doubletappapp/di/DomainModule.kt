package ru.fitsuli.doubletappapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.usecases.*

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddHabitUseCase(repository: HabitRepository) = AddHabitUseCase(repository)

    @Provides
    fun provideDeleteHabitUseCase(repository: HabitRepository) = DeleteHabitUseCase(repository)

    @Provides
    fun provideFindHabitByIdUseCase(repository: HabitRepository) = FindHabitByIdUseCase(repository)

    @Provides
    fun provideGetFilteredHabitsUseCase(repository: HabitRepository) =
        GetFilteredHabitsUseCase(repository)

    @Provides
    fun provideMarkAsDoneUseCase(repository: HabitRepository) = MarkAsDoneUseCase(repository)

    @Provides
    fun provideSaveFilterUseCase(repository: HabitRepository) = SaveFilterUseCase(repository)

    @Provides
    fun provideUpdateFromNetUseCase(repository: HabitRepository) = UpdateFromNetUseCase(repository)

    @Provides
    fun provideUpdateHabitUseCase(repository: HabitRepository) = UpdateHabitUseCase(repository)
}