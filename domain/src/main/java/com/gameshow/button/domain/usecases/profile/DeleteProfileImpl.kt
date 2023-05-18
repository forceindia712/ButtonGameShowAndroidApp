package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ProfileRepository

class DeleteProfileImpl(private val profileRepository: ProfileRepository) :
    DeleteProfile {

    override fun deleteProfile(profile: Profile) {
        profileRepository.deleteProfile(profile)
    }
}