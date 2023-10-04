package com.nameisjayant.notessss.di

import com.nameisjayant.notessss.data.local.model.Notes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmConfiguration {
    @Provides
    @Singleton
    fun providesRealmConfigs(): Realm {
        val config = RealmConfiguration.create(setOf(Notes::class))
        return Realm.open(config)
    }

}