package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    Scaffold(
        topBar = {
            Surface(elevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "${state.group.name} details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 30.dp),
                    text = "Edit group",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                if (state.updatingGroupError.isNotBlank()) Text(
                    modifier = Modifier.padding(bottom = 30.dp),
                    text = state.updatingGroupError,
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.Bold
                )
                if (state.updateMessage.isNotBlank()) Text(
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
                ) {
                    if (!state.isUpdatingGroup) Text("Update")
                    else CircularProgressIndicator()
                }
            }
            item {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp, top = 30.dp),
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
                            .padding(top = 20.dp)
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
            item {
                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "Members",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if(state.groupMembers.isEmpty()){
                item {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "No members in your group.\nInvite them by sending the invitation code.",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else items(state.groupMembers){ groupMember ->
                GroupMemberListItem(
                    groupMember = groupMember,
                    onKickClick = editGroupViewModel::onKickGroupMemberClick
                )
            }
        }
    }
}




















