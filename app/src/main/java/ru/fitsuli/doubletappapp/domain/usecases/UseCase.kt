package ru.fitsuli.doubletappapp.domain.usecases

interface UseCase {

    interface Callback {
        fun onSuccess()
        fun onError(t: Throwable)
    }

}