package ru.tpu.pretpu.features.news.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentLinksBinding
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import ru.tpu.pretpu.features.news.presentation.adapter.LinkItemAdapter
import ru.tpu.pretpu.features.news.presentation.viewmodel.LinksViewModel
import ru.tpu.pretpu.features.news.presentation.viewmodel.MainMenuViewModel

@AndroidEntryPoint
class LinksFragment : BaseFragment(R.layout.fragment_links) {

    // Получение viewmodel из NewsNavHostFragment
    private val parentViewModel: MainMenuViewModel by parentViewModels()

    override val viewModel: LinksViewModel by viewModels()

    private val binding: FragmentLinksBinding by viewBinding(FragmentLinksBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.adapter = LinkItemAdapter()

        // Берем выбранный предмет из viewmodel главного фрагмента
        parentViewModel.selectedItem.value?.peekContent()?.let { item ->
            val linkItem = item as LinkItem
            // Получаем список пунктов
            viewModel.getLinkItems(linkItem)

            // Коллбэк при свайпе сверху вниз - обновление главного меню
            parentViewModel.setDefaultRefreshListener()
        }

        // Синхронизация полей viewmodel данного фрагмента
        // с viewmodel главного фрагмента (главное меню)
        viewModel.selectedLinkItem.observe(viewLifecycleOwner, { event ->
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