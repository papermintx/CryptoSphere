package com.mk.cryptosphere.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.cryptosphere.ui.presentation.affine_cipher.AffineCipherScreen
import com.mk.cryptosphere.ui.presentation.auto_key_vigenere_cipher.AutoKeyVigenereCipherScreen
import com.mk.cryptosphere.ui.presentation.extended_vigenere_cipher.ExtendedVigereCipherScreen
import com.mk.cryptosphere.ui.presentation.hill_cipher.HillCipherScreen
import com.mk.cryptosphere.ui.presentation.home.HomeScreen
import com.mk.cryptosphere.ui.presentation.playfair_cipher.PlayfairCipherScreen
import com.mk.cryptosphere.ui.presentation.vigenere_cipher.VigenereCipherScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route,
        modifier = modifier
    ) {
        composable(
            NavScreen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HomeScreen(
                goEVC = {
                    navController.navigate(NavScreen.EVC.route)
                },
                goVC = {
                    navController.navigate(NavScreen.VC.route)
                },
                goAKVC = {
                    navController.navigate(NavScreen.AKVC.route)
                },
                goAFC = {
                    navController.navigate(NavScreen.AFC.route)
                },
                goPLC = {
                    navController.navigate(NavScreen.PLC.route)
                },
                goHLC = {
                    navController.navigate(NavScreen.HLC.route)
                }
            )
        }

        composable(
            NavScreen.HLC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HillCipherScreen {
                navController.popBackStack()
            }
        }

        composable(
            NavScreen.EVC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            ExtendedVigereCipherScreen{
                navController.popBackStack()
            }
        }

        composable(
            NavScreen.VC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            VigenereCipherScreen {
                navController.popBackStack()
            }
        }

        composable(
            NavScreen.PLC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            PlayfairCipherScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            NavScreen.AKVC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            AutoKeyVigenereCipherScreen{
                navController.popBackStack()
            }
        }

        composable(
            NavScreen.AFC.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            AffineCipherScreen{
                navController.popBackStack()
            }
        }
    }
}