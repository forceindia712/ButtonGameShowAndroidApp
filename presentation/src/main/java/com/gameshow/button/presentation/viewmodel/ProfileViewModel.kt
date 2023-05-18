package com.gameshow.button.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.usecases.profile.ChangeRulesVersion
import com.gameshow.button.domain.usecases.profile.CheckInternetConnection
import com.gameshow.button.domain.usecases.profile.CheckRulesHaveBeenChanged
import com.gameshow.button.domain.usecases.profile.DeleteProfile
import com.gameshow.button.domain.usecases.profile.GetAvatar
import com.gameshow.button.domain.usecases.profile.GetProfile
import com.gameshow.button.domain.usecases.profile.RenewIcon
import com.gameshow.button.domain.usecases.profile.SaveProfile

class ProfileViewModel(
    private val saveProfile: SaveProfile,
    private val deleteProfile: DeleteProfile,
    private val getProfile: GetProfile,
    private val renewIcon: RenewIcon,
    private val getAvatar: GetAvatar,
    private val checkInternetConnection: CheckInternetConnection,
    private val checkRulesHaveBeenChanged: CheckRulesHaveBeenChanged,
    private val changeRulesVersion: ChangeRulesVersion
) : ViewModel() {

    var profileList: MutableLiveData<List<Profile>> = MutableLiveData(arrayListOf())
    var acceptRules: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        getProfile()
        checkRulesHaveBeenChanged()
    }

    fun saveProfile(tempProfile: Profile) {
        saveProfile.saveProfile(tempProfile)
        getProfile()
    }

    fun deleteProfile(profile: Profile) {
        deleteProfile.deleteProfile(profile)
        getProfile()
    }

    private fun getProfile() {
        profileList.postValue(getProfile.getProfile())
    }

    fun renewIcon(): String {
        return renewIcon.renewIcon()
    }

    fun getAvatar(avatarName: String): Int {
        return getAvatar.getAvatar(avatarName)
    }

    fun checkInternetConnection(): Boolean {
        return checkInternetConnection.checkInternetConnection()
    }

    fun changeRulesVersion() {
        changeRulesVersion.changeRulesVersion()
        checkRulesHaveBeenChanged()
    }

    private fun checkRulesHaveBeenChanged() {
        acceptRules.value = checkRulesHaveBeenChanged.checkRulesHaveBeenChanged()
    }
}