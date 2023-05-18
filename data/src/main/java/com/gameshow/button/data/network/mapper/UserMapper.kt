package com.gameshow.button.data.network.mapper

import com.gameshow.button.domain.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserMapper : BaseMapperRepository<List<User>, String> {
    override fun transformToRepository(type: String): List<User> {
        return Gson().fromJson(
            type,
            TypeToken.getParameterized(List::class.java, User::class.java).type
        )
    }

    override fun transform(type: List<User>): String {
        TODO("Not yet implemented")
    }
}