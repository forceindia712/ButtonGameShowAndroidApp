package com.gameshow.button.presentation.callback

import com.gameshow.button.domain.entities.Profile

interface DeleteCallback {

    fun setPositiveButton(profile: Profile)
}