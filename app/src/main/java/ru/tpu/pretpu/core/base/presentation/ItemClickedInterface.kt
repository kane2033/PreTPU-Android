package ru.tpu.pretpu.core.base.presentation

fun interface ItemClickedInterface<T> {
    fun onClick(item: T)
}