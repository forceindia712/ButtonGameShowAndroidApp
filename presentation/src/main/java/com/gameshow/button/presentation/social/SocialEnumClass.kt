package com.gameshow.button.presentation.social

import com.gameshow.button.presentation.utils.SocialObject

enum class SocialEnumClass(val applicationPackage: String, val socialName: String? = null, val downloadGooglePlay: String? = null) {
    GithubForceIndia(
        SocialObject.githubPackage,
        SocialObject.githubForceIndia, SocialObject.googlePlayDownloadLink + SocialObject.githubPackage
    ),
    GithubDawiczku(
        SocialObject.githubPackage,
        SocialObject.githubDawiczku, SocialObject.googlePlayDownloadLink + SocialObject.githubPackage
    )
}