package com.aidanvii.utils.opengles.glsl

import com.aidanvii.utils.opengles.v20.GLWrapper
import com.aidanvii.utils.opengles.GLThread

abstract class UniformContainer(val glWrapper: GLWrapper) {
    internal var programObjectId = 0
    protected fun uniform(uniformName: String): Lazy<Int> = lazy(LazyThreadSafetyMode.NONE) {
        glWrapper.glGetUniformLocation(programObjectId, uniformName)
    }
}