package com.example.calendar.presentation.di.moules

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.calendar.R
import com.example.calendar.presentation.CalendarApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CalendarApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context)
            = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher_round)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
}