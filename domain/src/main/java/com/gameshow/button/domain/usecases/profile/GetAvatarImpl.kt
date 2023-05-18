package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.repositories.ProfileRepository

class GetAvatarImpl(private val profileRepository: ProfileRepository):
    GetAvatar {

    override fun getAvatar(avatarName: String): Int {
        return profileRepository.getAvatar(avatarName)
    }
}