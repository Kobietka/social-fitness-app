package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.event.components.DividedProperty


@Composable
fun EditGroupScreen(
    editGroupViewModel: EditGroupViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = editGroupViewModel.state.value
    val groupName = editGroupViewModel.groupName.value
    val groupDescription = editGroupViewModel.groupDescription.value
    val deleteGroupName = editGroupViewModel.deleteGroupName.value

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
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.isUserAGroupOwner) item {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
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
            } else item {
                DividedProperty(name = "Group name", value = state.group.name)
                DividedProperty(name = "Group description", value = state.group.description)
            }
            item {
                if(state.invitation != null){
                    DividedProperty(name = "Invitation code", value = state.invitation.code)
                    if(state.isUserAGroupOwner) Button(
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
                        if(!state.isDeletingCode) Text(text = "Delete code", color = Color.White)
                        else CircularProgressIndicator()
                    }
                } else if(state.isUserAGroupOwner) {
                    DividedProperty(
                        name = "Invitation code",
                        value = "Create invitation code for your group to invite your friends."
                    )
                    Button(
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
            if(state.isUserAGroupOwner) item {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    text = "Delete group",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                StandardTextField(
                    text = deleteGroupName.text,
                    error = deleteGroupName.error,
                    label = deleteGroupName.label,
                    onValueChange = editGroupViewModel::onDeleteNameChange
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp)
                        .height(50.dp),
                    onClick = {
                        editGroupViewModel.onDeleteGroupClick {
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    },
                    enabled = !state.isDeletingGroup
                ) {
                    if(!state.isDeletingGroup) Text(text = "Delete group")
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
                    onKickClick = editGroupViewModel::onKickGroupMemberClick,
                    canLoggedUserKick = state.isUserAGroupOwner
                )
            }
        }
    }
}




















