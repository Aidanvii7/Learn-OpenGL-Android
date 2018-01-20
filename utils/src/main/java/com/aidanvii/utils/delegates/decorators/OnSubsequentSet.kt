package com.aidanvii.utils.delegates.decorators

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline infix fun <ThisRef, Value> ReadWriteProperty<ThisRef, Value>.onSubsequentSet(
        crossinline onPropertySet: (value: Value) -> Unit
): ReadWriteProperty<ThisRef, Value> = object : ReadWriteProperty<ThisRef, Value> by this {
    override fun setValue(thisRef: ThisRef, property: KProperty<*>, value: Value) {
        this@onSubsequentSet.setValue(thisRef, property, value)
        onPropertySet.invoke(value)
    }
}