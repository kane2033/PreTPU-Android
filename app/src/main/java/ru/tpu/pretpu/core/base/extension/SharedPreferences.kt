package ru.tpu.pretpu.core.base.extension

import android.content.SharedPreferences

fun SharedPreferences.putString(key: String, value: String?) {
    with(this.edit()) {
        putString(key, value)
        apply()
    }
}

fun SharedPreferences.putStrings(strings: Map<String, String?>) {
    with(this.edit()) {
        strings.forEach { putString(it.key, it.value) }
        apply()
    }
}

fun SharedPreferences.removeStrings(vararg keys: String) {
    with(this.edit()) {
        keys.forEach { remove(it) }
        apply()
    }
}