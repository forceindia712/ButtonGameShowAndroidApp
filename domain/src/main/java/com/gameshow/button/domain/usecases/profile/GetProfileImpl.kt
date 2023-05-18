package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ProfileRepository

class GetProfileImpl(private val profileRepository: ProfileRepository) :
    GetProfile {

    override fun getProfile(): List<Profile> {
        return profileRepository.getProfiles()
    }
}