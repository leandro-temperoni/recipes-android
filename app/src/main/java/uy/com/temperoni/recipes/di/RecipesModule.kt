package uy.com.temperoni.recipes.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import uy.com.temperoni.recipes.repository.networking.GsonWrapper
import uy.com.temperoni.recipes.repository.networking.HttpWrapper

@Module
@InstallIn(SingletonComponent::class)
class RecipesModule {

    @Singleton
    @Provides
    fun providesResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun providesGsonWrapper(gson: Gson): GsonWrapper {
        return GsonWrapper(gson)
    }

    @Singleton
    @Provides
    fun providesHttpWrapper(): HttpWrapper {
        return HttpWrapper()
    }

    @Provides
    fun providesDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}