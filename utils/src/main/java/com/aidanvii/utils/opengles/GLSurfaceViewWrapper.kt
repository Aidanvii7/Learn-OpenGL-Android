package com.aidanvii.utils.opengles

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.widget.FrameLayout
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLSurfaceViewWrapper @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val rendererImpl = RendererImpl()

    private val glSurfaceView = GLSurfaceView(context, null).also { glSurfaceView ->
        glSurfaceView.setRenderer(rendererImpl)
        addView(glSurfaceView)
    }

    var renderer: GLSurfaceView.Renderer? = null


    fun onStart() {
        glSurfaceView.onResume()
    }

    fun onStop() {
        glSurfaceView.onPause()
    }

    private inner class RendererImpl : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10?) {
            renderer?.onDrawFrame(gl)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            renderer?.onSurfaceChanged(gl, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            renderer?.onSurfaceCreated(gl, config)
        }
    }
}