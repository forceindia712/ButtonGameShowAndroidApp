package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ProfileRepository

class SaveProfileImpl(private val profileRepository: ProfileRepository) :
    SaveProfile {

    override fun saveProfile(profile: Profile) {
        profileRepository.saveProfile(profile)
    }
}