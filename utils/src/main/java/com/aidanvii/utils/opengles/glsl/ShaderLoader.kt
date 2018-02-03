package com.aidanvii.utils.opengles.glsl

import android.content.Context
import com.aidanvii.utils.opengles.v20.GLWrapper
import com.aidanvii.utils.rawResourceAsString
import com.aidanvii.utils.opengles.GLThread

class ShaderLoader(
        private val context: Context,
        private val glWrapper: GLWrapper,
        private val shaderResourceId: Int
) {

    /**
     * Loads and compiles a [VertexShader].
     */
    @GLThread
    fun <T : AttributeContainer> compileVertexShader(attributeContainer: T) =
            VertexShader(
                    glWrapper = glWrapper,
                    shaderSource = context.rawResourceAsString(shaderResourceId),
                    attributeContainer = attributeContainer
            )

    /**
     * Loads and compiles a [VertexShader]
     */
    @GLThread
    fun <T : UniformContainer> compileFragmentShader(uniformContainer: T) =
            FragmentShader(
                    glWrapper = glWrapper,
                    shaderSource = context.rawResourceAsString(shaderResourceId),
                    uniformContainer = uniformContainer
            )
}