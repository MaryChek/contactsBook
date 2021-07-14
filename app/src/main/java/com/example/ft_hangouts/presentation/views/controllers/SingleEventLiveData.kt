package com.example.ft_hangouts.presentation.views.controllers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleEventLiveData<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }
}
