package com.aidanvii.firstopenglproject

import com.aidanvii.utils.opengl.v20.OpenGLES2Renderer
import javax.microedition.khronos.egl.EGLConfig

class FirstOpenGLRenderer : OpenGLES2Renderer() {

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