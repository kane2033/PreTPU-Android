package ru.tpu.pretpu.features.news.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentFeedBinding
import ru.tpu.pretpu.features.news.presentation.adapter.FeedItemAdapter
import ru.tpu.pretpu.features.news.presentation.viewmodel.FeedViewModel
import ru.tpu.pretpu.features.news.presentation.viewmodel.MainMenuViewModel

@AndroidEntryPoint
class FeedFragment : BaseFragment(R.layout.fragment_feed) {

    // Получение viewmodel из NewsNavHostFragment
    private val parentViewModel: MainMenuViewModel by parentViewModels()

    override val viewModel: FeedViewModel by viewModels()

    private val binding: FragmentFeedBinding by viewBinding(FragmentFeedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.adapter = FeedItemAdapter()

        // Берем выбранный предмет из viewmodel главного фрагмента
        parentViewModel.selectedItem.value?.peekContent()?.let { item ->
            // Получаем список статей
            viewModel.getFeedItems(item.id)

            // Коллбэк при свайпе сверху вниз - обновляем список статей
            parentViewModel.refreshListener.value = SwipeRefreshLayout.OnRefreshListener {
                viewModel.getFeedItems(item.id, true)
            }
        }

        // Синхронизация полей viewmodel данного фрагмента
        // с viewmodel главного фрагмента (главное меню)
        viewModel.selectedFeedItem.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                parentViewModel.setSelectedItemValue(it)
            }
        })

        viewModel.toolbarTitle.observe(viewLifecycleOwner, {
            parentViewModel.setToolbarTitleValue(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            parentViewModel.setIsLoadingValue(it)
        })

        handleFailure()
    }
}