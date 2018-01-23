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
    fun compileVertexShader(@RawRes vertexShaderResourceId: Int): Int =
            openGLES20.compileShader(GL_VERTEX_SHADER, context.rawResourceAsString(shaderResourceId))

    /**
     * Loads and compiles a fragment shader, returning the OpenGL object ID.
     */
    fun compileFragmentShader(@RawRes fragmentShaderResourceId: Int): Int =
            openGLES20.compileShader(GL_FRAGMENT_SHADER, context.rawResourceAsString(shaderResourceId))

    /**
     * Compiles a shader, returning the OpenGL object ID.
     */
    private fun OpenGLES20.compileShader(shaderType: Int, shaderSource: String): Int {
        glCreateShader(shaderType).let { shaderObjectId ->
            if (shaderObjectId == 0) {
                logW("Could not create shader")
                return 0
            }

            glShaderSource(shaderObjectId, shaderSource)

            glCompileShader(shaderObjectId)

            val shaderCompilationStatus = intArrayOf(0)
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, shaderCompilationStatus, 0)
            logV("shader compilation result: ${glGetShaderInfoLog(shaderObjectId)}")

            return if (shaderCompilationStatus[0] == 0) {
                glDeleteShader(shaderObjectId)
                logW("Compilation of shader failed")
                0
            } else shaderObjectId
        }
    }
}