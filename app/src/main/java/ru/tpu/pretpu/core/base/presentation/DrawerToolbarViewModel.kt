package ru.tpu.pretpu.core.base.presentation

import androidx.lifecycle.MutableLiveData

// ViewModel для лэйаута с боковой шторкой и тулбара,
// имеющей соответствующие поля
abstract class DrawerToolbarViewModel : BaseViewModel() {

    abstract val drawerMenu: MutableLiveData<List<String>?>

    val toolbarTitle = MutableLiveData("")

    var lastToolbarTitle = ""

    abstract val onDrawerItemSelected: DrawerItemSelectedInterface
}