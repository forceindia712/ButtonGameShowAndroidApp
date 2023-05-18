package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.repositories.ProfileRepository

class RenewIconImpl(private val profileRepository: ProfileRepository):
    RenewIcon {

    override fun renewIcon(): String {
        return profileRepository.renewIcon()
    }
}