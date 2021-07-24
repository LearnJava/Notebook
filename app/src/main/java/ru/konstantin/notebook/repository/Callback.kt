package ru.konstantin.notebook.repository

interface Callback<T> {
    fun onSuccess(result: T)
}