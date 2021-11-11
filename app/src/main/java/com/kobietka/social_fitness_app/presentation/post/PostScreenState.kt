package com.kobietka.social_fitness_app.presentation.post

import com.kobietka.social_fitness_app.domain.model.Comment
import com.kobietka.social_fitness_app.domain.model.GroupMember
import com.kobietka.social_fitness_app.domain.model.Post


data class PostScreenState(
    val isLoading: Boolean = false,
    val isEditingPost: Boolean = false,
    val isDeletePostLoading: Boolean = false,
    val loadingError: String = "",
    val loggedUser: GroupMember = GroupMember(id = "", userId = "", nickname = ""),
    val post: Post = Post(id = "", content = "", user = GroupMember(id = "", userId = "", nickname = ""), createdAt = ""),
    val comments: List<Comment> = emptyList(),
    val isCreatingComment: Boolean = false,
    val creatingCommentError: String = ""
)
