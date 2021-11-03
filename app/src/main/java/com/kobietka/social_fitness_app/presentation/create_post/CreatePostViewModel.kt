package com.kobietka.social_fitness_app.presentation.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.CreatePostUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CreatePostViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getGroup: GetGroupUseCase,
    private val createPost: CreatePostUseCase,
    private val logoutUser: LogoutUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CreatePostScreenState())
    val state: State<CreatePostScreenState> = _state

    private val _postContent = mutableStateOf(StandardTextFieldState(label = "Content"))
    val postContent: State<StandardTextFieldState> = _postContent

    init {
        handle.get<String>("groupId")?.let { groupId ->
            getGroup(groupId = groupId).onEach { group ->
                _state.value = _state.value.copy(group = group)
            }.launchIn(viewModelScope)
        }
    }

    fun onCreatePostClick(onSuccess: () -> Unit){
        _postContent.value = _postContent.value.copy(error = "")
        val content = _postContent.value.text.trim()
        if(content.isNotBlank()){
            createPost(
                groupId = state.value.group.id,
                content = content
            ).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isCreatingPost = true)
                    is Progress.Finished -> {
                        _state.value = _state.value.copy(isCreatingPost = false)
                        onSuccess()
                    }
                    is Progress.Error -> _state.value = _state.value.copy(
                        isCreatingPost = false,
                        creatingPostError = progress.message
                    )
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isCreatingPost = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
        } else _postContent.value = _postContent.value.copy(error = "This field cannot be blank")
    }

    fun onPostContentChange(value: String){
        _postContent.value = _postContent.value.copy(text = value)
    }

}





















