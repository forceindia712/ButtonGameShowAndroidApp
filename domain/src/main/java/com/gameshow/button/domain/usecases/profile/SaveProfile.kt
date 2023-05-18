package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile

interface SaveProfile {
    fun saveProfile(profile: Profile)
}