package com.aidanvii.utils.delegates

import com.aidanvii.utils.Consumer
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

inline fun <T> distinctObservable(
        initialValue: T,
        crossinline onNewValue: Consumer<T>
) = object : DistinctObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onNewValue(newValue)
}

/**
 * An implementation of [ObservableProperty] that performs an equality check before changing the internal value;
 * the change is propagated if they are not equal.
 */
open class DistinctObservableProperty<T>(initialValue: T) : ObservableProperty<T>(initialValue) {
    final override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T) = oldValue != newValue
}