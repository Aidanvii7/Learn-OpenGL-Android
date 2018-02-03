package com.aidanvii.airhockey

import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.glsl.AttributeContainer
import com.aidanvii.utils.opengles.v20.GLWrapper

class AirHockeyAttributeContainer(
        glWrapper: GLWrapper
) : AttributeContainer(glWrapper) {

    @get:GLThread
    val aPosition by attribute("a_position")
}