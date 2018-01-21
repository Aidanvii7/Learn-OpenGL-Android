package com.aidanvii.airhockey

import com.aidanvii.utils.opengles.v20.OpenGLES2Renderer
import javax.microedition.khronos.egl.EGLConfig

class AirHockeyRenderer : OpenGLES2Renderer() {

    override fun onSurfaceCreated(config: EGLConfig) {
        glClearColor(red = 1f, blue = 1f)
    }

    override fun onSurfaceChanged(width: Int, height: Int) {
        glViewport(width, height)
    }

    override fun onDrawFrame() {
        glClear()
    }
}