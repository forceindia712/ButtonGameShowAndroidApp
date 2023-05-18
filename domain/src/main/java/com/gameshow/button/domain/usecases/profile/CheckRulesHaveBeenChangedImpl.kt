package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.repositories.ProfileRepository

class CheckRulesHaveBeenChangedImpl(private val profileRepository: ProfileRepository):
    CheckRulesHaveBeenChanged {

    override fun checkRulesHaveBeenChanged(): Boolean {
        return profileRepository.checkRulesHaveBeenChanged()
    }
}