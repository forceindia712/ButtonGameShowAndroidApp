package com.gameshow.button.domain.repositories

interface ProfileRepository {
    fun saveProfile(profile: com.gameshow.button.domain.entities.Profile)
    fun deleteProfile(profile: com.gameshow.button.domain.entities.Profile)
    fun getProfiles(): List<com.gameshow.button.domain.entities.Profile>
    fun renewIcon(): String
    fun getAvatar(avatarName: String): Int
    fun checkInternetConnection(): Boolean
    fun checkRulesHaveBeenChanged(): Boolean
    fun changeRulesVersion()
}