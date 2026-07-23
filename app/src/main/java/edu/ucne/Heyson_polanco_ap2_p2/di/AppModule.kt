@file:Suppress("PackageName")
package edu.ucne.Heyson_polanco_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.Heyson_polanco_ap2_p2.data.RepositoryImp
import edu.ucne.Heyson_polanco_ap2_p2.data.remote.Api
import edu.ucne.Heyson_polanco_ap2_p2.domain.repository.GastosRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): Api {
        return Retrofit.Builder()
            .baseUrl("https://api-2026-h7eddqgydxc0fmau.eastus2-01.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideGastosRepository(
        api: Api
    ): GastosRepository {
        return RepositoryImp(api)
    }
}