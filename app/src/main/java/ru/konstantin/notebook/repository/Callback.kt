package ru.konstantin.notebook.repository;

public interface Callback<T> {

    void onSuccess(T result);
}
