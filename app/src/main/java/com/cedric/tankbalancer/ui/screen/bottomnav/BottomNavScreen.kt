package com.cedric.tankbalancer.ui.screen.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cedric.tankbalancer.R

sealed class BottomNavigationScreen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    data object Balancer : BottomNavigationScreen("balancer", R.string.screen_title_balancer, R.drawable.ic_balance)
    data object Aircraft : BottomNavigationScreen("aircraft", R.string.screen_title_aircraft, R.drawable.ic_aircraft)
}