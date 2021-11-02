package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField


@Composable
fun EditGroupScreen(
    editGroupViewModel: EditGroupViewModel = hiltViewModel()
) {
    val state = editGroupViewModel.state.value
    val groupName = editGroupViewModel.groupName.value
    val groupDescription = editGroupViewModel.groupDescription.value

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = "Edit group",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        if(state.updatingGroupError.isNotBlank()) Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = state.updatingGroupError,
            color = MaterialTheme.colors.error,
            fontWeight = FontWeight.Bold
        )
        if(state.updateMessage.isNotBlank()) Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = state.updateMessage,
            fontWeight = FontWeight.Bold
        )
        StandardTextField(
            text = groupName.text,
            error = groupName.error,
            label = groupName.label,
            onValueChange = editGroupViewModel::onGroupNameChange
        )
        MultilineTextField(
            text = groupDescription.text,
            error = groupDescription.error,
            label = groupDescription.label,
            maxLines = 6,
            onValueChange = editGroupViewModel::onGroupDescriptionChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            enabled = !state.isUpdatingGroup,
            onClick = editGroupViewModel::onEditGroupClick
        ){
            if(!state.isUpdatingGroup) Text("Update")
            else CircularProgressIndicator()
        }
        Text(
            modifier = Modifier.padding(bottom = 30.dp, top = 30.dp),
            text = "Invitation code",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        if(state.invitation != null){
            Text(
                text = state.invitation.code,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(50.dp),
                onClick = editGroupViewModel::onDeleteCodeClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                ),
                enabled = !state.isDeletingCode
            ) {
                if(!state.isDeletingCode) Text(text = "Delete code")
                else CircularProgressIndicator()
            }
        } else Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            onClick = editGroupViewModel::onCreateCodeClick,
            enabled = !state.isCreatingCode
        ) {
            if(!state.isCreatingCode) Text(text = "Create code")
            else CircularProgressIndicator()
        }
    }
}




















