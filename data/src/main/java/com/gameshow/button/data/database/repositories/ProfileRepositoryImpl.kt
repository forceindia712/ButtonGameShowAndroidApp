package com.gameshow.button.data.database.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ProfileRepository
import com.google.gson.Gson
import java.util.Random


class ProfileRepositoryImpl(private val context: Context) :
    ProfileRepository {

    private val sharedPreferencesProfile
        get() = context.getSharedPreferences("profile", Context.MODE_PRIVATE)

    private val sharedPreferencesRules
        get() = context.getSharedPreferences("rules", Context.MODE_PRIVATE)

    override fun saveProfile(profile: Profile) {
        val existingProfilesJsonString = sharedPreferencesProfile.getString("profile", "[]")
        val existingProfiles = Gson().fromJson(existingProfilesJsonString, Array<Profile>::class.java).toMutableList()

        existingProfiles.add(profile)
        val updatedProfilesJsonString = Gson().toJson(existingProfiles)

        sharedPreferencesProfile.edit().putString("profile", updatedProfilesJsonString).apply()
    }

    override fun deleteProfile(profile: Profile) {
        val existingProfilesJsonString = sharedPreferencesProfile.getString("profile", "[]")
        val existingProfiles = Gson().fromJson(existingProfilesJsonString, Array<Profile>::class.java).toMutableList()

        existingProfiles.remove(profile)
        val updatedProfilesJsonString = Gson().toJson(existingProfiles)

        sharedPreferencesProfile.edit().putString("profile", updatedProfilesJsonString).apply()
    }

    override fun getProfiles(): List<Profile> {
        val profilesJsonString = sharedPreferencesProfile.getString("profile", "[]")
        return Gson().fromJson(profilesJsonString, Array<Profile>::class.java).toList()
    }

    override fun renewIcon(): String {
        val avatarList = com.gameshow.button.data.database.objects.ApplicationObject.listOfAvatar

        val random = Random()
        val index = random.nextInt(avatarList.size)

        return avatarList[index]
    }

    override fun getAvatar(avatarName: String): Int {
        return context.resources.getIdentifier(avatarName, "drawable", context.packageName)
    }

    override fun checkInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun checkRulesHaveBeenChanged(): Boolean {
        return sharedPreferencesRules.getBoolean(com.gameshow.button.data.database.objects.ApplicationObject.rulesVersion, false)
    }

    override fun changeRulesVersion() {
        val editor = sharedPreferencesRules.edit()
        editor.putBoolean(com.gameshow.button.data.database.objects.ApplicationObject.rulesVersion, true)
        editor.apply()
    }
}