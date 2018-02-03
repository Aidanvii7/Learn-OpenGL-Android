package com.aidanvii.utils.opengles.glsl

import android.opengl.GLES20
import com.aidanvii.utils.logger.logV
import com.aidanvii.utils.logger.logW
import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.v20.GLWrapper

import java.io.Closeable

sealed class Shader @GLThread constructor(
        private val glWrapper: GLWrapper,
        shaderSource: String,
        shaderType: Int
) : Closeable {

    protected abstract val shaderTypeName: String

    val shaderObjectId = loadShader(shaderType, shaderSource)

    @GLThread
    final override fun close() {
        glWrapper.glDeleteShader(shaderObjectId)
    }

    private fun loadShader(shaderType: Int, shaderSource: String): Int =
            glWrapper.run {
                glCreateShader(shaderType).let { shaderObjectId ->
                    if (shaderObjectId == GL_ERROR_CODE) {
                        logW("Could not create $shaderTypeName")
                        throw CreateShaderError(shaderTypeName)
                    } else {
                        glShaderSource(shaderObjectId, shaderSource)
                        glCompileShader(shaderObjectId)
                        val shaderCompilationStatus = intArrayOf(0)
                        glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, shaderCompilationStatus, 0)
                        val compilationInfoLog = glGetShaderInfoLog(shaderObjectId)
                        logV("$shaderTypeName compilation result: $compilationInfoLog")

                        if (shaderCompilationStatus[0] == GL_ERROR_CODE) {
                            glDeleteShader(shaderObjectId)
                            logW("Compilation of $shaderTypeName failed")
                            throw CompileShaderError(shaderTypeName, compilationInfoLog)
                        } else shaderObjectId
                    }
                }
            }
}

class VertexShader<out T : AttributeContainer> @GLThread internal constructor(
        glWrapper: GLWrapper,
        shaderSource: String,
        val attributeContainer: T

) : Shader(glWrapper, shaderSource, GLES20.GL_VERTEX_SHADER) {
    override val shaderTypeName: String
        get() = "vertex shader"
}

class FragmentShader<out T : UniformContainer> @GLThread internal constructor(
        glWrapper: GLWrapper,
        shaderSource: String,
        val uniformContainer: T
) : Shader(glWrapper, shaderSource, GLES20.GL_FRAGMENT_SHADER) {
    override val shaderTypeName: String
        get() = "fragment shader"
}