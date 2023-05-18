package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.repositories.ProfileRepository

class CheckInternetConnectionImpl(private val profileRepository: ProfileRepository):
    CheckInternetConnection {

    override fun checkInternetConnection(): Boolean {
        return profileRepository.checkInternetConnection()
    }
}