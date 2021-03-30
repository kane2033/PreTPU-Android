package ru.tpu.pretpu.core.base.ui

import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.navigation.NavigationView
import ru.tpu.pretpu.core.base.presentation.DrawerToolbarViewModel

interface DrawerLayoutManager {
    fun getNavigationView(): NavigationView
    fun getDrawerLayout(): DrawerLayout
    fun getToolbar(): Toolbar
    fun setViewModelBinding(viewModel: DrawerToolbarViewModel)
    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner)
}