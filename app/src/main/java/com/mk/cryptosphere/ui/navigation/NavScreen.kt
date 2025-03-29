package com.mk.cryptosphere.ui.navigation

sealed class NavScreen(val route: String) {
    data object Home: NavScreen("home")
    data object Splash: NavScreen("splash")
    data object  EVC: NavScreen("evc")
    data object AFC: NavScreen("afc")
    data object AKVC: NavScreen("akvc")
    data object VC: NavScreen("vc")
    data object Settings: NavScreen("settings")
}