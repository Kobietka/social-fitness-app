package com.kobietka.social_fitness_app.presentation.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.CreateCommentUseCase
import com.kobietka.social_fitness_app.domain.usecase.comment.GetCommentsForPostUseCase
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
    private val getRemotePost: GetRemotePostUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PostScreenState())
    val state: State<PostScreenState> = _state

    private val _comment = mutableStateOf(StandardTextFieldState(label = "Comment"))
    val comment: State<StandardTextFieldState> = _comment

    init {
        handle.get<String>("postId")?.let { postId ->
            getPost(postId = postId).onEach { post ->
                _state.value = _state.value.copy(post = post)
            }.launchIn(viewModelScope)
            getCommentsForPost(postId = postId).onEach { comments ->
                _state.value = _state.value.copy(comments = comments)
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
























