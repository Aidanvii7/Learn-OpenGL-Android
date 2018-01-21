package com.aidanvii.utils

import android.content.res.TypedArray

inline fun TypedArray.use(block: TypedArray.() -> Unit) {
    try {
        block()
    } finally {
        recycle()
    }
}

inline fun <T> TypedArray.get(block: TypedArray.() -> T): T {
    try {
        return block()
    } finally {
        recycle()
    }
}