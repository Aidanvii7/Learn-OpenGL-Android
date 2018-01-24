package com.aidanvii.utils.opengles.glsl

import android.content.Context
import android.opengl.GLES20.GL_COMPILE_STATUS
import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_VERTEX_SHADER
import android.support.annotation.RawRes
import com.aidanvii.utils.logger.logV
import com.aidanvii.utils.logger.logW
import com.aidanvii.utils.opengles.v20.OpenGLES20
import com.aidanvii.utils.rawResourceAsString

class ShaderLoader(
        private val context: Context,
        private val openGLES20: OpenGLES20,
        private val shaderResourceId: Int
) {

    /**
     * Loads and compiles a vertex shader, returning the OpenGL object ID.
     */
    fun compileVertexShader(@RawRes vertexShaderResourceId: Int) =
            Shader.Vertex(
                    openGLES20 = openGLES20,
                    shaderSource = context.rawResourceAsString(shaderResourceId)
            )

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     */
    fun compileFragmentShader(@RawRes fragmentShaderResourceId: Int) =
            Shader.Fragment(
                    openGLES20 = openGLES20,
                    shaderSource = context.rawResourceAsString(shaderResourceId)
            )
}