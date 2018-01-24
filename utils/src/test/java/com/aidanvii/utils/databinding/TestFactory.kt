package com.aidanvii.utils.databinding

import android.support.annotation.RestrictTo
import com.aidanvii.utils.BR
import com.aidanvii.utils.Provider
import com.aidanvii.utils.databinding.delegates.bindable


/**
 * TODO move to test artifact
 */
object TestViewModelFactory {
    /**
     * Creates an [ObservableViewModel], initialising the [PropertyMapper] with the given [brClass] beforehand.
     *
     * [PropertyMapper] requires the [brClass] whenever a [bindable] property is updated in the provided [ObservableViewModel],
     * and will fail if not set.
     *
     * Example:
     * ```
     * class MyViewModel : ObservableViewModel() {
     *    //... bindable properties
     * }
     * ```
     *
     * With test:
     * ```
     * class Test {
     *    val tested = TestViewModelFactory.create(brClass = BR::class.java) { MyViewModel() }
     *    // ... tests
     * }
     *
     * ```
     * @param brClass the generated 'BR' class. This will be in the package defined inyour manifest.
     * @param provideTested a provider for the [ObservableViewModel]
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    inline fun <TestedViewModel : ObservableViewModel> create(
            brClass: Class<*>,
            provideTested: Provider<TestedViewModel>
    ): TestedViewModel {
        PropertyMapper.initBRClass(brClass, locked = false)
        return provideTested()
    }
}