package com.aidanvii.utils.opengles.glsl

import android.opengl.GLES20
import com.aidanvii.utils.logger.logV
import com.aidanvii.utils.logger.logW
import com.aidanvii.utils.opengles.v20.OpenGLES20
import com.aidanvii.utils.opengles.GLThread

class ShaderProgram @GLThread constructor(
        val openGLES20: OpenGLES20,
        val vertexShader: VertexShader,
        val fragmentShader: FragmentShader
) {

    val programObjectId = linkProgram()

    private fun linkProgram(): Int = openGLES20.run {
        glCreateProgram().let { programObjectId ->
            if (programObjectId == GL_ERROR_CODE) {
                logW("Could not create GL program")
                GL_ERROR_CODE
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
                    throw ShaderProgramError(linkInfoLog)
                } else programObjectId
            }
        }
    }
}