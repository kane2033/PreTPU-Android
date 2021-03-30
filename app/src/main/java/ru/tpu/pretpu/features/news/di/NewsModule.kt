package ru.tpu.pretpu.features.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.tpu.pretpu.features.news.data.network.NewsApi
import ru.tpu.pretpu.features.news.data.network.NewsApiImpl
import ru.tpu.pretpu.features.news.data.repository.NewsRepositoryImpl
import ru.tpu.pretpu.features.news.domain.repository.NewsRepository

@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    @ViewModelScoped
    fun provideNewsApi(api: NewsApiImpl): NewsApi = api

    @Provides
    @ViewModelScoped
    fun provideNewsRepository(repository: NewsRepositoryImpl):
            NewsRepository = repository
}