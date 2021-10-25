package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.remote.CommentRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.comment.CreateCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.EditCommentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class CommentModule {

    @Provides
    fun provideCreateCommentUseCase(
        commentRemoteRepository: CommentRemoteRepository,
        commentRepository: CommentRepository
    ): CreateCommentUseCase {
        return CreateCommentUseCase(
            commentRepository = commentRepository,
            commentRemoteRepository = commentRemoteRepository
        )
    }

    @Provides
    fun provideEditCommentUseCase(
        commentRemoteRepository: CommentRemoteRepository,
        commentRepository: CommentRepository
    ): EditCommentUseCase {
        return EditCommentUseCase(
            commentRemoteRepository = commentRemoteRepository,
            commentRepository = commentRepository
        )
    }

}




















