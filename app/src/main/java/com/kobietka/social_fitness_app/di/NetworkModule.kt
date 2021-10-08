package com.kobietka.social_fitness_app.di

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.service.AuthService
import com.kobietka.social_fitness_app.domain.service.UpdateUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl("https://filkur-fitness-app.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @Provides
    @Singleton
    @Named("retrofitAuth")
    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://filkur-fitness-app.herokuapp.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("token") token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .method(original.method(), original.body())
                    .url(original.url())
                    .build()
                chain.proceed(newRequest)
            }.build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@Named("retrofit") retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUpdateUserService(@Named("retrofitAuth") retrofit: Retrofit): UpdateUserService {
        return retrofit.create(UpdateUserService::class.java)
    }

    @Provides
    @Named("token")
    fun provideUserToken(
        userCredentialsRepository: UserCredentialsRepository
    ): String = runBlocking {
        return@runBlocking userCredentialsRepository.getUserToken()
    }

}




















