package com.aidanvii.utils.opengles.glsl

import com.aidanvii.utils.opengles.v20.GLWrapper
import com.aidanvii.utils.opengles.GLThread

abstract class AttributeContainer(val glWrapper: GLWrapper) {
    internal var programObjectId = 0
    protected fun attribute(attributeName: String): Lazy<Int> = lazy(LazyThreadSafetyMode.NONE) {
        glWrapper.glGetAttribLocation(programObjectId, attributeName)
    }
}