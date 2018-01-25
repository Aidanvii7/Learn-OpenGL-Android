package com.aidanvii.airhockey

import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.v20.OpenGLES2Renderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig

class AirHockeyRenderer : OpenGLES2Renderer() {

    val tableVerticesWithTriangles = floatArrayOf(
            // triangle 1
            0f, 0f,
            9f, 14f,
            0f, 14f,
            //triangle 2
            0f, 0f,
            9f, 0f,
            9f, 14f,
            // line
            0f, 7f,
            9f, 7f,
            // mallets
            4.5f, 2f,
            4.5f, 12f
    )

    val vertexData = ByteBuffer
            // allocate block of native memory
            .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            // order the bytes in the platforms native order (http://en.wikipedia.org/wiki/Endianness)
            .order(ByteOrder.nativeOrder())
            // convert to FloatBuffer to not deal with bytes directly
            .asFloatBuffer().apply {
        // copy memory from Android runtime to native memory
        put(tableVerticesWithTriangles)
    }

    @GLThread
    override fun onSurfaceCreated(config: EGLConfig) {
        glClearColor(red = 1f, blue = 1f)
    }

    @GLThread
    override fun onSurfaceChanged(width: Int, height: Int) {
        glViewport(width, height)
    }

    @GLThread
    override fun onDrawFrame() {
        glClear()
    }

    companion object {
        const val BYTES_PER_FLOAT = 4
    }
}