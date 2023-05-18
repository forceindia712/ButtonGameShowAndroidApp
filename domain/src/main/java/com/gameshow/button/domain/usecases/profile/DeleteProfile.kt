package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile

interface DeleteProfile {
    fun deleteProfile(profile: Profile)
}