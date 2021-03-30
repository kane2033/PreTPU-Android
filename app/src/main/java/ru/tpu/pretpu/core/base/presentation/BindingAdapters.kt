package ru.tpu.pretpu.core.base.presentation

import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.WebView
import android.widget.ImageView
import androidx.core.view.size
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tpu.pretpu.R
import ru.tpu.pretpu.core.base.domain.Constants
import ru.tpu.pretpu.core.base.domain.validation.exception.ValidationFailure

object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["validationFailure", "errorText"], requireAll = false)
    fun setErrorMessage(view: TextInputEditText, failure: ValidationFailure?, errorText: String?) {
        failure?.let { view.error = errorText ?: view.context.getString(R.string.validation_error) }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl"])
    fun ImageView.setImageFromUrl(url: String?) {
        if (url != null && url.isNotBlank()) {
            //если есть картинка
            //устанавливаем картинку из url в imageView форматом Bitmap
            Picasso.get().load(url).into(this)
        }
    }

    // Установка картинки у ImageView
    // с помощью декодирования байтового массива в bitmap
    // (выполняется асинхронно)
    @JvmStatic
    @BindingAdapter(value = ["imageByteArray"])
    fun ImageView.setImageFromByteArray(strBase64: String?) {
        if (strBase64 != null && strBase64.isNotBlank()) {
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    val decodedString = Base64.decode(strBase64, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                }
                setImageBitmap(bitmap)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["adapter"])
    fun <T> RecyclerView.bindRecyclerViewAdapter(adapter: BaseListAdapter<T>) {
        this.adapter = adapter
        //setHasFixedSize(true)
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter(value = ["onClick"])
    fun <T> RecyclerView.setOnClickListener(onClick: ItemClickedInterface<T>?) {
        onClick?.let {
            (adapter as BaseListAdapter<T>).itemClickedInterface = it
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["loading"])
    fun ContentLoadingProgressBar.setLoading(isLoading: Boolean) {
        if (isLoading) show() else hide()
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter(value = ["items"])
    fun <T> RecyclerView.setRecyclerItems(items: List<T>?) {
        (adapter as BaseListAdapter<T>).submitList(items)
    }

    @JvmStatic
    @BindingAdapter(value = ["items"])
    fun NavigationView.setNavigationViewMenu(items: List<String>?) {
        if (menu.size > 0) {
            menu.clear()
        }
        items?.forEachIndexed { index, item ->
            menu.add(1, index, 0, item)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["itemSelected"])
    fun NavigationView.setItemSelectedListener(function: DrawerItemSelectedInterface?) {
        function?.let { f ->
            setNavigationItemSelectedListener { menuItem ->
                f.onItemSelected(menuItem.itemId)
                return@setNavigationItemSelectedListener true
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["data"])
    fun WebView.loadData(data: String?) {
        data?.let {
            loadDataWithBaseURL(
                Constants.BASE_URL, it,
                "text/html", "UTF-8", null
            )
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["refreshListener"])
    fun SwipeRefreshLayout.setRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
        listener?.let {
            setOnRefreshListener {
                it.onRefresh()
                isRefreshing = false
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["loading"])
    fun SwipeRefreshLayout.setLoading(isRefreshing: Boolean?) {
        this.isRefreshing = isRefreshing ?: false
    }
}