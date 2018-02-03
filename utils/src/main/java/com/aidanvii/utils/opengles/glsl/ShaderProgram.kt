package com.aidanvii.utils.opengles.glsl

import android.opengl.GLES20
import com.aidanvii.utils.logger.logV
import com.aidanvii.utils.logger.logW
import com.aidanvii.utils.logger.whenLoggingEnabled
import com.aidanvii.utils.opengles.v20.GLWrapper
import com.aidanvii.utils.opengles.GLThread
import java.io.Closeable

class ShaderProgram<out T1 : AttributeContainer, out T2 : UniformContainer> @GLThread constructor(
        val glWrapper: GLWrapper,
        val vertexShader: VertexShader<T1>,
        val fragmentShader: FragmentShader<T2>
) : Closeable {

    @GLThread
    override fun close() {
        glWrapper.glDeleteProgram(programObjectId)
        vertexShader.close()
        fragmentShader.close()
    }

    val programObjectId = linkProgram()

    private fun linkProgram(): Int = glWrapper.run {
        glCreateProgram().let { programObjectId ->
            if (programObjectId == GL_ERROR_CODE) {
                logW("Could not create GL program")
                throw CreateShaderProgramError()
            } else {
                glAttachShader(programObjectId, vertexShader.shaderObjectId)
                glAttachShader(programObjectId, fragmentShader.shaderObjectId)
                glLinkProgram(programObjectId)
                val glLinkStatus = intArrayOf(0)
                glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, glLinkStatus, 0)
                val linkInfoLog = glGetProgramInfoLog(programObjectId)
                logV("Results of linking program: $linkInfoLog")
                if (glLinkStatus[0] == GL_ERROR_CODE) {
                    glDeleteProgram(programObjectId)
                    logW("Linking of program failed.")
                    throw LinkShaderProgramError(linkInfoLog)
                } else programObjectId.also {
                    whenLoggingEnabled {
                        glValidateProgram(programObjectId)
                        val glValidateProgramStatus = intArrayOf(0)
                        glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, glValidateProgramStatus, 0)
                        logV("Results of validating GL program ${glValidateProgramStatus[0]} \nLog: ${glGetProgramInfoLog(programObjectId)}")
                    }
                    vertexShader.attributeContainer.programObjectId = programObjectId
                    fragmentShader.uniformContainer.programObjectId = programObjectId
                }
            }
        }
    }
}