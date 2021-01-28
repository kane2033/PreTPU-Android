package ru.tpu.pretpu.core.base.infrastructure

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import ru.tpu.pretpu.core.base.domain.infrastructure.ConfigChanger
import java.util.*
import javax.inject.Inject

class ConfigChangerImpl
@Inject constructor() : ConfigChanger {

    override fun updateLocale(context: Context, languageCode: String): ContextWrapper {
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        // Можно попробовать LocaleListCompat, чтобы не использовать if else
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(Locale(languageCode))
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            ContextWrapper(context.createConfigurationContext(configuration))
        } else {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)

            /*activity.getBaseContext().getResources().updateConfiguration(
                config,
                displayMetrics
            )
            //без обновления конфига application context не все строки будут переведены (пр.: ошибки в полях ввода)
            activity.getApplicationContext().getResources().updateConfiguration(
                config,
                displayMetrics
            )*/

            ContextWrapper(context)
        }
    }
}