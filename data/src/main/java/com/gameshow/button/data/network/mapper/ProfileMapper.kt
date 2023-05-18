package com.gameshow.button.data.network.mapper

import com.gameshow.button.domain.entities.Profile
import com.google.gson.Gson

class ProfileMapper : BaseMapperRepository<Profile, String> {
    override fun transform(type: Profile): String {
        return Gson().toJson(type)
    }

    override fun transformToRepository(type: String): Profile {
        TODO("Not yet implemented")
    }
}