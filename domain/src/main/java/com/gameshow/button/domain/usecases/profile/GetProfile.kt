package com.gameshow.button.domain.usecases.profile

import com.gameshow.button.domain.entities.Profile

interface GetProfile {
    fun getProfile(): List<Profile>
}