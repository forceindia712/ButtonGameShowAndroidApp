package com.gameshow.button.app

import android.app.Application
import com.gameshow.button.BuildConfig
import com.gameshow.button.data.network.repositories.ServerCommunicationRepositoryImpl
import com.gameshow.button.di.dataModule
import com.gameshow.button.di.domainModule
import com.gameshow.button.di.presentationModule
import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ButtonGameShowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ButtonGameShowApplication)
            modules(mutableListOf(dataModule, serverModule, domainModule, presentationModule))
        }
    }

    private val serverModule = module {
        single<ServerCommunicationRepository> {
            ServerCommunicationRepositoryImpl(
                get(),
                get(),
                BuildConfig.HEROKU_SERVER_ADDRESS
            )
        }
    }

}