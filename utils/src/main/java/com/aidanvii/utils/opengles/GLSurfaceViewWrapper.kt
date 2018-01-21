package com.aidanvii.utils.opengles

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.widget.FrameLayout
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLSurfaceViewWrapper @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val rendererForwarder = RendererForwarder()

    private val glSurfaceView = GLSurfaceView(context, null).also { glSurfaceView ->
        glSurfaceView.setRenderer(rendererForwarder)
        addView(glSurfaceView)
    }

    private var _renderer: GLSurfaceView.Renderer = emptyRenderer()
    var renderer: GLSurfaceView.Renderer?
        get() = _renderer
        set(value) {
            _renderer = if (value != null) value else emptyRenderer()
        }

    init {
        (context as? FragmentActivity)?.apply {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) = onStart()
                override fun onStop(owner: LifecycleOwner) = onStop()
            })
        }
    }

    private fun emptyRenderer(): GLSurfaceView.Renderer {
        return object : GLSurfaceView.Renderer {
            override fun onDrawFrame(gl: GL10?) {}
            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {}
        }
    }

    fun onStart() {
        glSurfaceView.onResume()
    }

    fun onStop() {
        glSurfaceView.onPause()
    }

    private inner class RendererForwarder : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10?) {
            _renderer.onDrawFrame(gl)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            _renderer.onSurfaceChanged(gl, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            _renderer.onSurfaceCreated(gl, config)
        }
    }
}