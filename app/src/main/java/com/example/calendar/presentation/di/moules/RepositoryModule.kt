package com.example.calendar.presentation.di.moules

import com.example.calendar.data.repositories.TaskRepositoryImpl
import com.example.calendar.domain.repositories.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideTaskRepository(repository: TaskRepositoryImpl): TasksRepository
}