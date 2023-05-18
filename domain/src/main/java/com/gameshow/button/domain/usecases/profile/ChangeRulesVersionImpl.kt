package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.repositories.ProfileRepository

class ChangeRulesVersionImpl(private val profileRepository: ProfileRepository):
    ChangeRulesVersion {

    override fun changeRulesVersion() {
        profileRepository.changeRulesVersion()
    }
}