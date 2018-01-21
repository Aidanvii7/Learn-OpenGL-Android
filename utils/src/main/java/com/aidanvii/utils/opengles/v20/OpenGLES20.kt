package com.aidanvii.utils.opengles.v20

import android.opengl.GLES20
import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.support.annotation.FloatRange

interface OpenGLES20 {

    /**
     * C function void glClearColor ( GLclampf red, GLclampf green, GLclampf blue, GLclampf alpha )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glClearColor.xml)
     */
    fun glClearColor(
            @FloatRange(from = 0.0, to = 1.0) red: Float = 0f,
            @FloatRange(from = 0.0, to = 1.0) green: Float = 0f,
            @FloatRange(from = 0.0, to = 1.0) blue: Float = 0f,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0f
    ) {
        GLES20.glClearColor(red, green, blue, alpha)
    }

    /**
     * C function void glViewport ( GLint x, GLint y, GLsizei width, GLsizei height )
     */
    fun glViewport(
            width: Int = 0,
            height: Int = 0,
            x: Int = 0,
            y: Int = 0
    ) {
        GLES20.glViewport(x, y, width, height)
    }

    /**
     * C function void glClear ( GLbitfield mask )
     */
    fun glClear(mask: Int = GL_COLOR_BUFFER_BIT) = GLES20.glClear(mask)

}