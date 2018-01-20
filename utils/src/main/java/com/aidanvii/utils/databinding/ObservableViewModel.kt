package com.aidanvii.utils.databinding

import android.arch.lifecycle.ViewModel
import android.support.annotation.RestrictTo
import com.aidanvii.utils.Provider
import com.aidanvii.utils.databinding.notifiable.NotifiableObservable
import com.aidanvii.utils.leakingThis

/**
 * A convenience view-model class that implements [NotifiableObservable].
 */
@Suppress(leakingThis)
open class ObservableViewModel : ViewModel(), NotifiableObservable by Factory.delegate() {

    init {
        initDelegator(this)
    }

    object Factory {
        private val defaultProvideDelegate: Provider<NotifiableObservable> = { NotifiableObservable.delegate() }
        private var provideDelegate = defaultProvideDelegate

        internal fun delegate(): NotifiableObservable = provideDelegate()

        @RestrictTo(RestrictTo.Scope.TESTS)
        fun <T : ObservableViewModel> tested(
                mockDelegate: NotifiableObservable = delegate(),
                brClass: Class<*>,
                provideTested: Provider<T>
        ): T {
            this.provideDelegate = { mockDelegate }
            brClass.let { PropertyMapper.initBRClass(it, locked = false) }
            return provideTested().also {
                this.provideDelegate = defaultProvideDelegate
            }
        }
    }
}