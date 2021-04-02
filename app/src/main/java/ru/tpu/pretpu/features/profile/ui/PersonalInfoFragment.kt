package ru.tpu.pretpu.features.profile.ui

import androidx.fragment.app.viewModels
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.features.profile.presentation.PersonalInfoViewModel
import ru.tpu.pretpu.features.profile.presentation.ProfileViewModel

class PersonalInfoFragment : BaseFragment(R.layout.fragment_personal_info) {

    private val parentViewModel: ProfileViewModel by parentViewModels()

    override val viewModel: PersonalInfoViewModel by viewModels()
}