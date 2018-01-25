package com.aidanvii.utils.opengles.glsl

import android.content.Context
import android.support.annotation.RawRes
import com.aidanvii.utils.opengles.v20.OpenGLES20
import com.aidanvii.utils.rawResourceAsString
import com.aidanvii.utils.opengles.GLThread
class ShaderLoader(
        private val context: Context,
        private val openGLES20: OpenGLES20,
        private val shaderResourceId: Int
) {

    /**
     * Loads and compiles a [VertexShader].
     */
    @GLThread
    fun compileVertexShader(@RawRes vertexShaderResourceId: Int) =
            VertexShader(
                    openGLES20 = openGLES20,
                    shaderSource = context.rawResourceAsString(shaderResourceId)
            )

    /**
     * Loads and compiles a [VertexShader]
     */
    @GLThread
    fun compileFragmentShader(@RawRes fragmentShaderResourceId: Int) =
            FragmentShader(
                    openGLES20 = openGLES20,
                    shaderSource = context.rawResourceAsString(shaderResourceId)
            )
}