package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.model.GroupMember


@Composable
fun GroupMemberListItem(
    groupMember: GroupMember,
    onKickClick: (String) -> Unit,
    canLoggedUserKick: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = groupMember.nickname,
                fontWeight = FontWeight.Medium
            )
            if(canLoggedUserKick) Button(
                modifier = Modifier
                    .padding(top = 13.dp)
                    .height(40.dp),
                onClick = { onKickClick(groupMember.id) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text(
                    text = "Kick",
                    color = Color.White
                )
            }
        }
        Divider(modifier = Modifier.padding(top = 5.dp))
    }
}














