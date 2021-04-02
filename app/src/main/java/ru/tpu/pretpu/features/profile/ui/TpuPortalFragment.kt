package ru.tpu.pretpu.features.profile.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.ui.BaseFragment
import ru.tpu.pretpu.databinding.FragmentTpuPortalBinding
import ru.tpu.pretpu.features.profile.presentation.ProfileViewModel
import ru.tpu.pretpu.features.profile.presentation.TpuPortalViewModel

class TpuPortalFragment : BaseFragment(R.layout.fragment_tpu_portal) {

    private val parentViewModel: ProfileViewModel by parentViewModels()

    override val viewModel: TpuPortalViewModel by viewModels()

    private val binding: FragmentTpuPortalBinding by viewBinding(FragmentTpuPortalBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonAcademicPlan.setOnClickListener {
                //parentViewModel...
            }
            buttonSchedule.setOnClickListener {
                //parentViewModel...
            }
            buttonPersonalCard.setOnClickListener {
                goToPortalLink("private")
            }
            buttonGrades.setOnClickListener {
                goToPortalLink("study")
            }
            buttonScholarship.setOnClickListener {
                goToPortalLink("stipend")
            }
            buttonPayment.setOnClickListener {
                goToPortalLink("oplata")
            }
            buttonMyPayments.setOnClickListener {
                goToPortalLink("oplata/mypayment")
            }
            buttonMail.setOnClickListener {
                goToLink("https://mail.tpu.ru/")
            }
        }
    }

    // Переход на одну из страниц портала ТПУ
    private fun goToPortalLink(url: String) {
        val fullUrl = "https://portal.tpu.ru/desktop/student/$url"
        try {
            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Переход на любой url
    private fun goToLink(fullUrl: String) {
        try {
            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}