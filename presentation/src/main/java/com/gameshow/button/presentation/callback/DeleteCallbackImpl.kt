package com.gameshow.button.presentation.callback

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.presentation.viewmodel.ProfileViewModel

class DeleteCallbackImpl(val viewModel: ProfileViewModel): DeleteCallback {

    override fun setPositiveButton(profile: Profile) {
        viewModel.deleteProfile(profile)
    }
}