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
open class ObservableViewModel : ViewModel(), NotifiableObservable by NotifiableObservable.delegate() {

    init {
        initDelegator(this)
    }

    object Factory {

        @RestrictTo(RestrictTo.Scope.TESTS)
        inline fun <T : ObservableViewModel> tested(
                brClass: Class<*>,
                provideTested: Provider<T>
        ): T {
            PropertyMapper.initBRClass(brClass, locked = false)
            return provideTested()
        }
    }
}