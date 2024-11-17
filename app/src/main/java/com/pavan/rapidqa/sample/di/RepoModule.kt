package com.pavan.rapidqa.sample.di


import com.pavan.rapidqa.sample.data.GhostRepoImpl
import com.pavan.rapidqa.sample.domain.GhostRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindRepo(repo: GhostRepoImpl): GhostRepo
}