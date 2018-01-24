package com.aidanvii.utils.databinding.delegates

import com.aidanvii.utils.databinding.PropertyMapper
import com.aidanvii.utils.databinding.notifiable.NotifiableObservable
import com.aidanvii.utils.delegates.DistinctObservableProperty
import kotlin.reflect.KProperty

/**
 * Creates a [BindableProperty] for properties of classes that implement [NotifiableObservable].
 *
 * The property must be annotated with [Bindable].
 *
 * Usage:
 * ```
 * @get:Bindable
 * var firstName by bindable("")
 *
 * ```
 * @param bindableResourceId the id from the generated `BR` class. If omitted, the [PropertyMapper] must be initialised prior to usage.
 */
fun <T> NotifiableObservable.bindable(initialValue: T) = BindableProperty(initialValue, this)

/**
 * A property provideOpenGLES20 for properties of [NotifiableObservable] objects.
 */
class BindableProperty<T>(
        initialValue: T,
        private val observable: NotifiableObservable
) : DistinctObservableProperty<T>(initialValue) {

    private var propertyId: Int = 0

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
        observable.notifyPropertyChanged(propertyId)
    }

    operator fun provideDelegate(observable: NotifiableObservable, property: KProperty<*>): BindableProperty<T> =
            this.also { propertyId = PropertyMapper.getBindableResourceId(property) }
}