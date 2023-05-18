package com.gameshow.button.di

import com.gameshow.button.data.BuildConfig
import com.gameshow.button.data.database.repositories.ProfileRepositoryImpl
import com.gameshow.button.data.network.mapper.ProfileMapper
import com.gameshow.button.data.network.mapper.UserMapper
import com.gameshow.button.data.network.repositories.ServerCommunicationRepositoryImpl
import com.gameshow.button.domain.repositories.ProfileRepository
import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import com.gameshow.button.domain.usecases.profile.ChangeRulesVersion
import com.gameshow.button.domain.usecases.profile.ChangeRulesVersionImpl
import com.gameshow.button.domain.usecases.profile.CheckInternetConnection
import com.gameshow.button.domain.usecases.profile.CheckInternetConnectionImpl
import com.gameshow.button.domain.usecases.profile.CheckRulesHaveBeenChanged
import com.gameshow.button.domain.usecases.profile.CheckRulesHaveBeenChangedImpl
import com.gameshow.button.domain.usecases.profile.DeleteProfile
import com.gameshow.button.domain.usecases.profile.DeleteProfileImpl
import com.gameshow.button.domain.usecases.profile.GetAvatar
import com.gameshow.button.domain.usecases.profile.GetAvatarImpl
import com.gameshow.button.domain.usecases.profile.GetProfile
import com.gameshow.button.domain.usecases.profile.GetProfileImpl
import com.gameshow.button.domain.usecases.profile.RenewIcon
import com.gameshow.button.domain.usecases.profile.RenewIconImpl
import com.gameshow.button.domain.usecases.profile.SaveProfile
import com.gameshow.button.domain.usecases.profile.SaveProfileImpl
import com.gameshow.button.domain.usecases.server.ButtonClickedEmitUseCase
import com.gameshow.button.domain.usecases.server.CheckConnectionEmitUseCase
import com.gameshow.button.domain.usecases.server.CheckConnectionOnUseCase
import com.gameshow.button.domain.usecases.server.CheckStartGameOnUseCase
import com.gameshow.button.domain.usecases.server.CloseGameEmitUseCase
import com.gameshow.button.domain.usecases.server.CloseGameOnUseCase
import com.gameshow.button.domain.usecases.server.CloseGameTimeoutOnUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyOnUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyTimeoutOnUseCase
import com.gameshow.button.domain.usecases.server.CreateLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.DisconnectSocketUseCaseImpl
import com.gameshow.button.domain.usecases.server.GameStartedOrTooMuchPeopleOnUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyCodeOnUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyUsersEmitUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyUsersOnUseCase
import com.gameshow.button.domain.usecases.server.InitSocketUseCase
import com.gameshow.button.domain.usecases.server.JoinToLobbyBooleanEmitUseCase
import com.gameshow.button.domain.usecases.server.JoinToLobbyBooleanOnUseCase
import com.gameshow.button.domain.usecases.server.KickUserFromLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.KickUserFromLobbyOnUseCase
import com.gameshow.button.domain.usecases.server.LeaveGameEmitUseCase
import com.gameshow.button.domain.usecases.server.LeaveLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.RenewConnectionGameEmitUseCase
import com.gameshow.button.domain.usecases.server.RenewConnectionLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.StartGameEmitUseCase
import com.gameshow.button.domain.usecases.server.StartRoundEmitUseCase
import com.gameshow.button.domain.usecases.server.WrongLobbyCodeOnUseCase
import com.gameshow.button.presentation.callback.DeleteCallback
import com.gameshow.button.presentation.callback.DeleteCallbackImpl
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val dataModule = module {
    single { ProfileMapper() }
    single { UserMapper() }

    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}

val domainModule = module {
    single<GetProfile> {
        GetProfileImpl(
            get()
        )
    }
    single<SaveProfile> {
        SaveProfileImpl(
            get()
        )
    }
    single<DeleteProfile> {
        DeleteProfileImpl(
            get()
        )
    }
    single<RenewIcon> {
        RenewIconImpl(
            get()
        )
    }
    single<GetAvatar> {
        GetAvatarImpl(
            get()
        )
    }
    single<CheckInternetConnection> {
        CheckInternetConnectionImpl(
            get()
        )
    }
    single<CheckRulesHaveBeenChanged> {
        CheckRulesHaveBeenChangedImpl(
            get()
        )
    }
    single<ChangeRulesVersion> {
        ChangeRulesVersionImpl(
            get()
        )
    }

    single<DeleteCallback> {
        DeleteCallbackImpl(
            get()
        )
    }

    single { ButtonClickedEmitUseCase(get()) }
    single { CheckConnectionEmitUseCase(get()) }
    single { CheckConnectionOnUseCase(get()) }
    single { CheckStartGameOnUseCase(get()) }
    single { CloseGameEmitUseCase(get()) }
    single { CloseGameOnUseCase(get()) }
    single { CloseLobbyEmitUseCase(get()) }
    single { CloseGameTimeoutOnUseCase(get()) }
    single { CloseLobbyOnUseCase(get()) }
    single { CloseLobbyTimeoutOnUseCase(get()) }
    single { CreateLobbyEmitUseCase(get()) }
    single { DisconnectSocketUseCaseImpl(get()) }
    single { GameStartedOrTooMuchPeopleOnUseCase(get()) }
    single { GetLobbyCodeOnUseCase(get()) }
    single { GetLobbyUsersEmitUseCase(get()) }
    single { GetLobbyUsersOnUseCase(get()) }
    single { InitSocketUseCase(get()) }
    single { JoinToLobbyBooleanEmitUseCase(get()) }
    single { JoinToLobbyBooleanOnUseCase(get()) }
    single { KickUserFromLobbyEmitUseCase(get()) }
    single { KickUserFromLobbyOnUseCase(get()) }
    single { LeaveGameEmitUseCase(get()) }
    single { LeaveLobbyEmitUseCase(get()) }
    single { RenewConnectionGameEmitUseCase(get()) }
    single { RenewConnectionLobbyEmitUseCase(get()) }
    single { StartGameEmitUseCase(get()) }
    single { StartRoundEmitUseCase(get()) }
    single { GetLobbyUsersOnUseCase(get()) }
    single { WrongLobbyCodeOnUseCase(get()) }
}

val presentationModule = module {
    viewModelOf(::ProfileViewModel)
    viewModel {
        SocketViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

