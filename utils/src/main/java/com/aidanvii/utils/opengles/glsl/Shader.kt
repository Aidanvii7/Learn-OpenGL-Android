package com.aidanvii.utils.opengles.glsl

import android.opengl.GLES20
import com.aidanvii.utils.logger.logV
import com.aidanvii.utils.logger.logW
import com.aidanvii.utils.opengles.v20.OpenGLES20

sealed class Shader(
        private val openGLES20: OpenGLES20,
        shaderSource: String,
        shaderType: Int
) {

    class CompilationError(compilationInfoLog: String) : Error("Could not compile shader. Log: $compilationInfoLog")

    class Vertex(
            openGLES20: OpenGLES20,
            shaderSource: String
    ) : Shader(openGLES20, shaderSource, GLES20.GL_VERTEX_SHADER)

    class Fragment(
            openGLES20: OpenGLES20,
            shaderSource: String
    ) : Shader(openGLES20, shaderSource, GLES20.GL_FRAGMENT_SHADER)

    val shaderObjectId = loadShader(shaderType, shaderSource)

    private fun loadShader(shaderType: Int, shaderSource: String): Int =
            openGLES20.run {
                glCreateShader(shaderType).let { shaderObjectId ->
                    if (shaderObjectId == 0) {
                        logW("Could not create shader")
                        0
                    } else {
                        glShaderSource(shaderObjectId, shaderSource)
                        glCompileShader(shaderObjectId)
                        val shaderCompilationStatus = intArrayOf(0)
                        glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, shaderCompilationStatus, 0)
                        val compilationInfoLog = glGetShaderInfoLog(shaderObjectId)
                        logV("shader compilation result: $compilationInfoLog")

                        if (shaderCompilationStatus[0] == 0) {
                            glDeleteShader(shaderObjectId)
                            logW("Compilation of shader failed")
                            throw CompilationError(compilationInfoLog)
                            0
                        } else shaderObjectId
                    }
                }
            }
}