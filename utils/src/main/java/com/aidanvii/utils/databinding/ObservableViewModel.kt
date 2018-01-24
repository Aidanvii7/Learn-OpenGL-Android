package com.aidanvii.utils.databinding

import android.arch.lifecycle.ViewModel
import com.aidanvii.utils.databinding.notifiable.NotifiableObservable
import com.aidanvii.utils.databinding.delegates.bindable
import com.aidanvii.utils.leakingThis

/**
 * A convenience [ViewModel] class that implements [NotifiableObservable].
 *
 * Intended to be used with the [bindable] property delegate for data-binding.
 */
@Suppress(leakingThis)
abstract class ObservableViewModel : ViewModel(), NotifiableObservable by NotifiableObservable.delegate() {
    init {
        initDelegator(this)
    }
}