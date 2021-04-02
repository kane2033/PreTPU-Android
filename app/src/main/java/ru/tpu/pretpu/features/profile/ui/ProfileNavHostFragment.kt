package ru.tpu.pretpu.features.profile.ui

import androidx.fragment.app.viewModels
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.features.profile.presentation.ProfileViewModel

class ProfileNavHostFragment : BaseFragment(R.layout.fragment_host_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    private val binding:
}