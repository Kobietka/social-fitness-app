package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.remote.CommentRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.comment.CreateCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.DeleteCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.EditCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.GetCommentsForPostUseCase
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
    fun provideGetCommentsForPostUseCase(
        commentRepository: CommentRepository,
        groupMemberRepository: GroupMemberRepository
    ): GetCommentsForPostUseCase {
        return GetCommentsForPostUseCase(
            commentRepository = commentRepository,
            groupMemberRepository = groupMemberRepository
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

    @Provides
    fun provideDeleteCommentUseCase(
        commentRemoteRepository: CommentRemoteRepository,
        commentRepository: CommentRepository
    ): DeleteCommentUseCase {
        return DeleteCommentUseCase(
            commentRepository = commentRepository,
            commentRemoteRepository = commentRemoteRepository
        )
    }

}




















