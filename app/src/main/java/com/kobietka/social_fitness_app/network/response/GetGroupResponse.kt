package com.kobietka.social_fitness_app.network.response

import com.kobietka.social_fitness_app.domain.model.User


data class GetGroupResponse(
    val id: String,
    val name: String,
    val description: String,
    val owner: User,
    val invitation: Invitation?,
    val members: List<MemberDto>
)