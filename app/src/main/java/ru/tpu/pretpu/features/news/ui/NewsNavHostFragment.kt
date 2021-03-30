package ru.tpu.pretpu.features.news.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentHostNewsBinding
import ru.tpu.pretpu.databinding.NavHeaderBinding
import ru.tpu.pretpu.features.news.presentation.NewsNavigator.navigateByItem
import ru.tpu.pretpu.features.news.presentation.NewsNavigator.navigateByItemPopStack
import ru.tpu.pretpu.features.news.presentation.NewsNavigator.navigateByItemSingleTop
import ru.tpu.pretpu.features.news.presentation.viewmodel.MainMenuViewModel

@AndroidEntryPoint
class NewsNavHostFragment : BaseFragment(R.layout.fragment_host_news), SwipeRefreshLayoutInterface {

    // Viewmodel главного меню, хранящая пункты 1 уровня
    override val viewModel: MainMenuViewModel by viewModels()

    private val binding: FragmentHostNewsBinding by viewBinding(FragmentHostNewsBinding::bind)

    override fun getSwipeRefreshLayout() = binding.swipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val drawerLayout = binding.drawerLayout
        val toolbar = binding.appBar.toolbar

        // При нажатии "назад" закрывает шторку
        drawerLayout.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS

        // Установка тулбара для активити
        // и кнопки "сендвича" для боковой шторки
        // (могут быть проблемы, т.к. вызывается метод активности)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            activity, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Получение navController внутри NewsNavHostFragment (графа nav_news)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.news_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Навигация осуществляется через изменение selectedItem
        viewModel.selectedItem.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { item ->
                when (viewModel.itemStatus) {
                    // В зависимости от выбрана
                    is MainMenuViewModel.ItemStatus.DrawerItem -> {
                        // Айтем выбран из шторки
                        navController.navigateByItemSingleTop(context, item)
                        if (drawerLayout.isOpen) {
                            drawerLayout.close()
                        }
                    }
                    is MainMenuViewModel.ItemStatus.RefreshedItem -> {
                        // Айтем обновлен
                        navController.navigateByItemPopStack(context, item)
                    }
                    else -> {
                        // Айтем был просто выбран в Links, Feed, Article Fragments
                        navController.navigateByItem(context, item)
                    }
                }
            }
        })

        // Настройка верхней части выдвижной шторки
        binding.navigationView.navView.getHeaderView(0).apply {
            // Переход в личный кабинет
            setOnClickListener {
                //navigate()
            }
            NavHeaderBinding.bind(this).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@NewsNavHostFragment.viewModel
            }
        }

        // Стандартно обрабатываем
        // ошибки Failure
        handleFailure()
    }
}