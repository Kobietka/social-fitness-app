package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.post.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class PostModule {

    @Provides
    fun provideCreatePostUseCase(
        postRemoteRepository: PostRemoteRepository,
        postRepository: PostRepository
    ): CreatePostUseCase {
        return CreatePostUseCase(
            postRemoteRepository = postRemoteRepository,
            postRepository = postRepository
        )
    }

    @Provides
    fun provideGetPostUseCase(
        postRepository: PostRepository,
        groupMemberRepository: GroupMemberRepository
    ): GetPostUseCase {
        return GetPostUseCase(
            postRepository = postRepository,
            groupMemberRepository = groupMemberRepository
        )
    }

    @Provides
    fun provideDeletePostUseCase(
        postRemoteRepository: PostRemoteRepository,
        postRepository: PostRepository
    ): DeletePostUseCase {
        return DeletePostUseCase(
            postRepository = postRepository,
            postRemoteRepository = postRemoteRepository
        )
    }

    @Provides
    fun provideGetRemotePostUseCase(
        postRemoteRepository: PostRemoteRepository,
        postRepository: PostRepository,
        commentRepository: CommentRepository
    ): GetRemotePostUseCase {
        return GetRemotePostUseCase(
            postRepository = postRepository,
            postRemoteRepository = postRemoteRepository,
            commentRepository = commentRepository
        )
    }

    @Provides
    fun provideEditPostUseCase(
        postRemoteRepository: PostRemoteRepository,
        postRepository: PostRepository
    ): EditPostUseCase {
        return EditPostUseCase(
            postRemoteRepository = postRemoteRepository,
            postRepository = postRepository
        )
    }

}



















