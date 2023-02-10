package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.BuildConfig
import com.example.tvmazeclient.data.api.ShowsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * modulo para inyeccion de dependencias de retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    /**
     * se activa interceptor unicamente en modo DEBUG
     */
    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    /**
     * se agrega cliente con interceptor a retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideShowsApiService(retrofit: Retrofit): ShowsApiService{
        return retrofit.create(ShowsApiService::class.java)
    }
}