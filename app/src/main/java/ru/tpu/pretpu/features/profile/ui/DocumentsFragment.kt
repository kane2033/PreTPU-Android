package ru.tpu.pretpu.features.profile.ui

import androidx.fragment.app.viewModels
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.features.profile.presentation.DocumentsViewModel
import ru.tpu.pretpu.features.profile.presentation.ProfileViewModel

class DocumentsFragment : BaseFragment(R.layout.fragment_documents) {

    private val parentViewModel: ProfileViewModel by parentViewModels()

    override val viewModel: DocumentsViewModel by viewModels()
}