package ru.tpu.pretpu.core.base.ui

import android.view.Menu
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView

/**
 * Через данный интерфейс фрагмент имеет доступ
 * к управлению интерфейса главной активности,
 * который не используется на всех экранах, но,
 * если используется, то в множестве экранах.
 *
 * Интерфейс реализуется в [MainActivity],
 * вызывается в [BaseFragment]
 * */
interface MainActivityUiToggle {
    fun toggleNavigationDrawer(toggle: Boolean)
    fun toggleToolbar(toggle: Boolean)
    fun setToolbarTitle(title: String)
    fun setupToolbarAndDrawerNavigation(navController: NavController)
    fun getNavigationView(): NavigationView
    fun getNavigationDrawerMenu(): Menu
}