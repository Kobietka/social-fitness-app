package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    }
}




















