package com.kobietka.social_fitness_app.presentation.edit_user

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kobietka.social_fitness_app.presentation.Screen
import com.kobietka.social_fitness_app.presentation.components.PasswordTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField


@Composable
fun EditUserScreen(
    editUserViewModel: EditUserViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = editUserViewModel.screenState.value
    val nickname = editUserViewModel.nickname.value
    val dataPassword = editUserViewModel.dataPassword.value
    val oldPassword = editUserViewModel.oldPassword.value
    val newPassword = editUserViewModel.newPassword.value

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                editUserViewModel.onLogoutClick()
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.MainScreen.route){
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Outlined.Logout,
                contentDescription = "logout"
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = "Edit your data",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        if(state.dataError.isNotBlank()) Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = state.dataError,
            color = MaterialTheme.colors.error,
            fontWeight = FontWeight.Bold
        )
        StandardTextField(
            text = nickname.text,
            error = nickname.error,
            label = nickname.label,
            onValueChange = editUserViewModel::onNicknameChange
        )
        PasswordTextField(
            text = dataPassword.text,
            error = dataPassword.error,
            label = dataPassword.label,
            isVisible = dataPassword.isVisible,
            onVisibilityChange = editUserViewModel::onDataPasswordVisibilityChange,
            onValueChange = editUserViewModel::onDataPasswordChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            enabled = !state.isDataLoading,
            onClick = editUserViewModel::onUpdateDataClick
        ){
            if(!state.isDataLoading) Text("Update")
            else CircularProgressIndicator()
        }
        Text(
            modifier = Modifier.padding(bottom = 30.dp, top = 30.dp),
            text = "Change your password",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        if(state.passwordError.isNotBlank()) Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = state.passwordError,
            color = MaterialTheme.colors.error,
            fontWeight = FontWeight.Bold
        )
        PasswordTextField(
            text = newPassword.text,
            error = newPassword.error,
            label = newPassword.label,
            isVisible = newPassword.isVisible,
            onVisibilityChange = editUserViewModel::onNewPasswordVisibilityChange,
            onValueChange = editUserViewModel::onNewPasswordChange
        )
        PasswordTextField(
            text = oldPassword.text,
            error = oldPassword.error,
            label = oldPassword.label,
            isVisible = oldPassword.isVisible,
            onVisibilityChange = editUserViewModel::onOldPasswordVisibilityChange,
            onValueChange = editUserViewModel::onOldPasswordChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            enabled = !state.isPasswordLoading,
            onClick = {
                editUserViewModel.onUpdatePasswordClick {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.MainScreen.route){
                            inclusive = true
                        }
                    }
                }
            }
        ){
            if(!state.isPasswordLoading) Text("Update")
            else CircularProgressIndicator()
        }
    }

}