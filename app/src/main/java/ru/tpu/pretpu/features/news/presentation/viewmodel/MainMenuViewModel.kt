package ru.tpu.pretpu.features.news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tpu.pretpu.core.base.domain.interactor.None
import ru.tpu.pretpu.core.base.presentation.BaseViewModel
import ru.tpu.pretpu.core.base.presentation.DrawerItemSelectedInterface
import ru.tpu.pretpu.core.base.presentation.Event
import ru.tpu.pretpu.core.credentials.domain.entity.User
import ru.tpu.pretpu.features.news.domain.entity.Item
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.domain.interactor.GetHeaderUserInfo
import ru.tpu.pretpu.features.news.domain.interactor.GetMainMenu
import ru.tpu.pretpu.features.news.presentation.ArticleUrlParser
import javax.inject.Inject

/**
 * Главная ViewModel в модуле news.
 * Содержит поля, связанные с персистивными элементами интерфейса
 * через data binding:
 * [drawerItems] - главное меню.
 * [drawerMenu] - отображение меню в DrawerLayout (строковой массив).
 * [toolbarTitle] - Заголовок в тулбаре.
 * [toolbarVisibility] - видимость тулбара (требуется его спрятать в ArticleFragment).
 * [selectedItem] - выбранный [Item], который следует открыть в соответсвующем фрагменте
 * (по нему осуществляется навигация).
 * (персистивные эл-ы интерфейса - toolbar, drawerLayout, progressBar)
 * */
@HiltViewModel
class MainMenuViewModel
@Inject constructor(
    private val getMainMenu: GetMainMenu,
    private val getHeaderUserInfo: GetHeaderUserInfo
) : BaseViewModel() {

    // Заголовок тулбара
    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    fun setToolbarTitleValue(title: String) {
        _toolbarTitle.value = title
    }

    // Видимость тулбара (можно прятать через toggleToolbar)
    private val _toolbarVisibility = MutableLiveData(true)
    val toolbarVisibility: LiveData<Boolean> = _toolbarVisibility

    fun toggleToolbar(isVisible: Boolean) {
        _toolbarVisibility.value = isVisible
    }

    // Информация о юзере в хедере выдвижной шторки
    private val _headerUserInfo = MutableLiveData<User>()
    val headerUserInfo: LiveData<User> = _headerUserInfo

    // Настоящий список предметов из шторки
    private val _drawerItems = MutableLiveData<List<LinkItem>>()
    val drawerItems: LiveData<List<LinkItem>> = _drawerItems

    // То, что будет отображаться в боковой шторке (список строк)
    // (формируется на основе drawerItems)
    private val _drawerMenu = MutableLiveData<List<String>?>()
    val drawerMenu: LiveData<List<String>?> = _drawerMenu

    // Выбранный айтем, по которому осуществляется навигация
    private val _selectedItem = MutableLiveData<Event<Item>>()
    val selectedItem: LiveData<Event<Item>> = _selectedItem

    // Статус предмета необходим для навигации, соответсвующей тому,
    // как был выбран предмет
    var itemStatus: ItemStatus = ItemStatus.UsualItem

    fun setSelectedItemValue(item: Item, itemStatus: ItemStatus = ItemStatus.UsualItem) {
        this.itemStatus = itemStatus
        _selectedItem.value = Event(item)
    }

    // Коллбэк при обновлении главного меню через swipeRefreshLayout
    private val mainMenuRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        loadMainMenuItems(true)
    }

    // Используемый в data binding коллбэк (по дефолту исп-я обновление главного меню),
    // который изменяется в каждом фрагменте соответствующим образом
    var refreshListener = MutableLiveData(mainMenuRefreshListener)

    // Установка коллбэка обновления на обновление главного меню
    // (используется в в LinksFragment)
    fun setDefaultRefreshListener() {
        refreshListener.value = mainMenuRefreshListener
    }

    // Коллбэк при нажатии на элемент шторки
    val onDrawerItemSelected = DrawerItemSelectedInterface { position ->
        drawerItems.value?.get(position)?.let {
            setSelectedItemValue(it, ItemStatus.DrawerItem)
        }
    }

    // Действие при открытии диплинка
    // (в ArticleFragment)
    val onDeepLinkClicked = { url: String ->
        ArticleUrlParser.navigateDeepLink(
            url,
            drawerItems.value ?: emptyList(),
            viewModelScope
        ) { item -> item?.let { setSelectedItemValue(it) } }
    }

    init {
        // Загружаем главное меню
        // и инфо о юзере
        // при инициализации viewmodel
        loadMainMenuItems()
        getHeaderUserInfo()
    }

    // Загружаем главное меню,
    // только если поле с меню null
    // (на случай, когда метод вызывается в onViewCreated)
    // или при обновлении через swipeRefreshLayout (doRefresh = true)
    private fun loadMainMenuItems(doRefresh: Boolean = false) {
        if (doRefresh || _drawerItems.value == null) {
            _isLoading.value = true
            getMainMenu.invoke(
                params = None(),
                onResult = {
                    it.fold(::handleFailure) { items ->
                        handleMainMenuItemsLoaded(
                            items,
                            doRefresh
                        )
                    }
                },
                job = job
            )
        }
    }

    private fun handleMainMenuItemsLoaded(mainMenuItems: List<LinkItem>, doRefresh: Boolean) {
        _drawerItems.value = mainMenuItems
        _drawerMenu.value = mainMenuItems.map { it.name }
        val itemStatus = if (doRefresh) ItemStatus.RefreshedItem else ItemStatus.DrawerItem
        setSelectedItemValue(mainMenuItems[0], itemStatus)
        _isLoading.value = false
    }

    private fun getHeaderUserInfo() {
        getHeaderUserInfo.invoke(
            params = None(),
            onResult = { it.fold(::handleFailure, ::handleHeaderUserInfoLoaded) },
            job = job
        )
    }

    private fun handleHeaderUserInfoLoaded(user: User) {
        _headerUserInfo.value = user
    }

    // Иерархия определяет состояние selectedItem
    // (необходимо для навигации)
    sealed class ItemStatus {
        object DrawerItem : ItemStatus() // Элемент шторки
        object RefreshedItem : ItemStatus() // Элемент обновлен через swipeRefreshLayout
        object UsualItem : ItemStatus() // Предмет выбран в другом фрагменте
    }
}