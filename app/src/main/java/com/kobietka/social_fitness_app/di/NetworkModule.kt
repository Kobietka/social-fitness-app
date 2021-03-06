package com.kobietka.social_fitness_app.di

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.service.*
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
    fun provideOkHttpClient(
        userCredentialsRepository: UserCredentialsRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                runBlocking {
                    val original = chain.request()
                    val newRequest = original.newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer ${userCredentialsRepository.getUserToken()}"
                        ).method(original.method(), original.body())
                        .url(original.url())
                        .build()
                    chain.proceed(newRequest)
                }
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
    @Singleton
    fun provideActivityService(@Named("retrofitAuth") retrofit: Retrofit): ActivityService {
        return retrofit.create(ActivityService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventService(@Named("retrofitAuth") retrofit: Retrofit): EventService {
        return retrofit.create(EventService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentService(@Named("retrofitAuth") retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }

    @Provides
    @Singleton
    fun providePostService(@Named("retrofitAuth") retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    @Provides
    @Singleton
    fun provideGroupService(@Named("retrofitAuth") retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }

    @Provides
    @Singleton
    fun provideGroupMemberService(@Named("retrofitAuth") retrofit: Retrofit): GroupMemberService {
        return retrofit.create(GroupMemberService::class.java)
    }

    @Provides
    @Singleton
    fun provideInvitationService(@Named("retrofitAuth") retrofit: Retrofit): InvitationService {
        return retrofit.create(InvitationService::class.java)
    }

}




















