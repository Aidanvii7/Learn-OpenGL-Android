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
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glViewport.xml)
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
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glClear.xml)
     */
    fun glClear(mask: Int = GL_COLOR_BUFFER_BIT) = GLES20.glClear(mask)


    /**
     * C function GLuint glCreateShader ( GLenum type )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glCreateShader.xml)
     */
    fun glCreateShader(type: Int = 0): Int = GLES20.glCreateShader(type)

    /**
     * C function void glShaderSource ( GLuint shader, GLsizei count, const GLchar ** string, const GLint* length )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glShaderSource.xml)
     */
    fun glShaderSource(shaderObjectId: Int, shaderSource: String) = GLES20.glShaderSource(shaderObjectId, shaderSource)

    /**
     * C function void glCompileShader ( GLuint shader )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glCompileShader.xml)
     */
    fun glCompileShader(shaderObjectId: Int) = GLES20.glCompileShader(shaderObjectId)

    /**
     * C function void glGetShaderiv ( GLuint shader, GLenum pname, GLint *params )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glGetShaderiv.xml)
     */
    fun glGetShaderiv(
            shaderObjectId: Int,
            pname: Int,
            params: IntArray,
            offset: Int
    ) = GLES20.glGetShaderiv(shaderObjectId, pname, params, offset)

    /**
     * C function void glCompileShader ( GLuint shader )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glGetShaderInfoLog.xml)
     */
    fun glGetShaderInfoLog(shaderObjectId: Int) = GLES20.glGetShaderInfoLog(shaderObjectId)


    /**
     * C function void glDeleteShader ( GLuint shader )
     *
     * [see here](https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glDeleteShader.xml)
     */
    fun glDeleteShader(shaderObjectId: Int) = GLES20.glDeleteShader(shaderObjectId)

}