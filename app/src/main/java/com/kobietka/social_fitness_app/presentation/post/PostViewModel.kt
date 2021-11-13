package com.kobietka.social_fitness_app.presentation.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.GroupMember
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.CreateCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.DeleteCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.EditCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.GetCommentsForPostUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.DeletePostUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.EditPostUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.GetPostUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.GetRemotePostUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PostViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getPost: GetPostUseCase,
    private val getCommentsForPost: GetCommentsForPostUseCase,
    private val createComment: CreateCommentUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val getRemotePost: GetRemotePostUseCase,
    private val getUsers: GetUsersUseCase,
    private val deletePost: DeletePostUseCase,
    private val editPost: EditPostUseCase,
    private val deleteComment: DeleteCommentUseCase,
    private val editComment: EditCommentUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PostScreenState())
    val state: State<PostScreenState> = _state

    private val _comments = mutableStateOf(mutableListOf<CommentState>())
    val comments: State<MutableList<CommentState>> = _comments

    private val _comment = mutableStateOf(StandardTextFieldState(label = "Comment"))
    val comment: State<StandardTextFieldState> = _comment

    private val _postContent = mutableStateOf(StandardTextFieldState(label = "Content"))
    val postContent: State<StandardTextFieldState> = _postContent

    init {
        handle.get<String>("postId")?.let { postId ->
            getPost(postId = postId).onEach { post ->
                post?.let {
                    _state.value = _state.value.copy(post = it)
                    _postContent.value = _postContent.value.copy(text = it.content)
                }
                getUsers().onEach { users ->
                    try {
                        val loggedUser = users.first()
                        _state.value = _state.value.copy(
                            loggedUser = GroupMember(
                                id = "",
                                userId = loggedUser.id,
                                nickname = loggedUser.nickname
                            )
                        )
                    } catch (exception: Exception) { }
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)
            getCommentsForPost(postId = postId).onEach { comments ->
                val commentStates = _comments.value.toMutableList()
                comments.forEach { comment ->
                    val commentState = commentStates.firstOrNull { commentState ->
                        commentState.comment.id == comment.id
                    }
                    if(commentState == null) commentStates.add(
                        CommentState(
                            comment = comment,
                            commentEditValue = StandardTextFieldState(
                                text = comment.content,
                                label = "Content"
                            )
                        )
                    )
                    else {
                        commentStates.remove(commentState)
                        commentStates.add(commentState.copy(comment = comment))
                    }
                }
                _comments.value = commentStates
            }.launchIn(viewModelScope)
            handle.get<String>("groupId")?.let { groupId ->
                getRemotePost(
                    postId = postId,
                    groupId = groupId
                ).onEach { progress ->
                    when(progress){
                        is Progress.Loading -> _state.value = _state.value.copy(isLoading = true)
                        is Progress.Finished -> _state.value = _state.value.copy(isLoading = false)
                        is Progress.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                loadingError = progress.message
                            )
                            delay(1500)
                            _state.value = _state.value.copy(loadingError = "")
                        }
                        is Progress.Unauthorized -> {
                            _state.value = _state.value.copy(isLoading = false)
                            logoutUser()
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onCommentChanged(value: String){
        _comment.value = _comment.value.copy(text = value)
    }

    fun onPostContentChanged(value: String){
        _postContent.value = _postContent.value.copy(text = value)
    }

    fun onEditPostClick(){
        _state.value = _state.value.copy(isEditingPost = true)
    }

    fun onCancelEditPostClick(){
        _state.value = _state.value.copy(isEditingPost = false)
    }

    fun onSendEditPostClick(){
        val content = _postContent.value.text.trim()

        if(content.isBlank()){
            _postContent.value = _postContent.value.copy(error = "This field cannot be empty")
        } else {
            editPost(
                postId = _state.value.post.id,
                content = content
            ).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isEditingPostLoading = true)
                    is Progress.Finished -> _state.value = _state.value.copy(
                        isEditingPostLoading = false,
                        isEditingPost = false
                    )
                    is Progress.Error -> _postContent.value = _postContent.value.copy(error = progress.message)
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isEditingPostLoading = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onDeleteCommentClick(commentId: String){
        deleteComment(commentId = commentId).onEach { progress ->
            when(progress){
                Progress.Finished -> {
                    val comments = _comments.value
                    _comments.value = comments.filter { it.comment.id != commentId }.toMutableList()
                }
                Progress.Unauthorized -> logoutUser()
                else -> { }
            }
        }.launchIn(viewModelScope)
    }

    fun onExpandCommentClick(commentId: String){
        val comments = _comments.value.toMutableList()
        val commentState = comments.find { it.comment.id == commentId } ?: return
        val index = comments.indexOf(commentState)
        comments.remove(commentState)
        comments.add(index, commentState.copy(isExpanded = !commentState.isExpanded))
        _comments.value = comments
    }

    fun onEditCommentClick(commentId: String){
        val comments = _comments.value.toMutableList()
        val commentState = comments.find { it.comment.id == commentId } ?: return
        val index = comments.indexOf(commentState)
        comments.remove(commentState)
        comments.add(index, commentState.copy(isEditing = true))
        _comments.value = comments
    }

    fun onCancelCommentEdit(commentId: String){
        val comments = _comments.value.toMutableList()
        val commentState = comments.find { it.comment.id == commentId } ?: return
        val index = comments.indexOf(commentState)
        comments.remove(commentState)
        comments.add(index, commentState.copy(isEditing = false))
        _comments.value = comments
    }

    fun onCommentContentChange(commentId: String, value: String){
        val comments = _comments.value.toMutableList()
        val commentState = comments.find { it.comment.id == commentId } ?: return
        val index = comments.indexOf(commentState)
        comments.remove(commentState)
        comments.add(index, commentState.copy(commentEditValue = commentState.commentEditValue.copy(text = value)))
        _comments.value = comments
    }

    fun onUpdateComment(commentId: String){
        val commentState = _comments.value.find { it.comment.id == commentId } ?: return
        editComment(
            commentId = commentId,
            content = commentState.commentEditValue.text.trim(),
            postId = _state.value.post.id
        ).onEach { progress ->
            when(progress){
                is Progress.Loading -> {
                    val comments = _comments.value.toMutableList()
                    val state = comments.find { it.comment.id == commentId }
                    if(state != null){
                        val index = comments.indexOf(state)
                        comments.remove(state)
                        comments.add(index, state.copy(isLoading = true))
                        _comments.value = comments
                    }
                }
                is Progress.Finished -> {
                    val comments = _comments.value.toMutableList()
                    val state = comments.find { it.comment.id == commentId }
                    if(state != null){
                        val index = comments.indexOf(state)
                        comments.remove(state)
                        comments.add(
                            index,
                            state.copy(
                                isLoading = false,
                                isEditing = false,
                                isExpanded = false
                            )
                        )
                        _comments.value = comments
                    }
                }
                is Progress.Unauthorized -> logoutUser()
                is Progress.Error -> {
                    val comments = _comments.value.toMutableList()
                    val state = comments.find { it.comment.id == commentId }
                    if(state != null){
                        val index = comments.indexOf(state)
                        comments.remove(state)
                        comments.add(
                            index,
                            state.copy(
                                isLoading = false,
                                commentEditValue = state.commentEditValue.copy(error = progress.message)
                            )
                        )
                        _comments.value = comments
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onDeletePostClick(onFinish: () -> Unit){
        deletePost(postId = state.value.post.id).onEach { progress ->
            when(progress){
                is Progress.Loading -> _state.value = _state.value.copy(isDeletePostLoading = true)
                is Progress.Finished -> {
                    _state.value = _state.value.copy(isDeletePostLoading = false)
                    onFinish()
                }
                is Progress.Error -> _state.value = _state.value.copy(isDeletePostLoading = false)
                is Progress.Unauthorized -> {
                    _state.value = _state.value.copy(isDeletePostLoading = false)
                    logoutUser()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSendCommentClick(){
        val content = _comment.value.text.trim()

        if(content.isNotBlank()){
            createComment(
                postId = _state.value.post.id,
                content = content
            ).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isCreatingComment = true)
                    is Progress.Finished -> {
                        _state.value = _state.value.copy(isCreatingComment = false)
                        _comment.value = _comment.value.copy(text = "")
                    }
                    is Progress.Error -> _state.value = _state.value.copy(
                        isCreatingComment = false,
                        creatingCommentError = progress.message
                    )
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isCreatingComment = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
        } else _comment.value = _comment.value.copy(error = "This field cannot be empty")
    }

}
























