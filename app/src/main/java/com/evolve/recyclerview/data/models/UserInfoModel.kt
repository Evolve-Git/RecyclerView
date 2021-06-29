package com.evolve.recyclerview.data.models

data class UserInfo(
        val steamId: Int,
        val communityvisibilitystate: Int,
        val profilestate: Int,
        val personaname: String,
        val profileurl: String,
        val avatar: String,
        val lastlogoff: Int,
        val personastate: Int,
        val realname: String,
        val primaryclanid: Long,
        val timecreated: Int,
        val personastateflags: Int
        )

data class UserInfoResponse(
        val players: ArrayList<UserInfo>
        )

data class UserInfoModel (
        val response: UserInfoResponse
        )