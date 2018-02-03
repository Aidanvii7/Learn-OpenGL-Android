package com.aidanvii.airhockey

import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.glsl.UniformContainer
import com.aidanvii.utils.opengles.v20.GLWrapper

class AirHockeyUniformContainer(
        glWrapper: GLWrapper
) : UniformContainer(glWrapper) {

    @get:GLThread
    val uColor by uniform("u_Color")
}