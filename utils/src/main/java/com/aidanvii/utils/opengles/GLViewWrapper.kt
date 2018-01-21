package com.aidanvii.utils.opengles

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.res.TypedArray
import android.opengl.GLSurfaceView
import android.opengl.GLTextureView
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.widget.FrameLayout
import com.aidanvii.utils.R
import com.aidanvii.utils.get
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLViewWrapper @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val rendererForwarder = RendererForwarder()

    private val glViewWrapperDelegate: GLViewWrapperDelegate =
            context.obtainStyledAttributes(attrs, R.styleable.GLViewWrapper).get {
                if (textureViewEnabled()) createTextureViewDelegate(context) else createSurfaceViewDelegate(context)
            }

    private var _renderer: GLSurfaceView.Renderer = emptyRenderer()
        set(value) {
            if (field !== value) {
                field = value
                glViewWrapperDelegate.setRenderer(value)
            }
        }

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

    fun onStart() {
        glViewWrapperDelegate.onStart()
    }

    fun onStop() {
        glViewWrapperDelegate.onStop()
    }

    private fun emptyRenderer(): GLSurfaceView.Renderer {
        return object : GLSurfaceView.Renderer {
            override fun onDrawFrame(gl: GL10?) {}
            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {}
        }
    }

    private fun TypedArray.textureViewEnabled() =
            getBoolean(R.styleable.GLViewWrapper_useTextureView, false)

    private fun createTextureViewDelegate(context: Context): GLViewWrapperDelegate {
        return object : GLViewWrapperDelegate {

            private val glTextureView = GLTextureView(context, null).also { glTextureView ->
                glTextureView.setRenderer(rendererForwarder)
                addView(glTextureView)
            }

            override fun setRenderer(renderer: GLSurfaceView.Renderer) {
                glTextureView.setRenderer(renderer)
            }

            override fun onStart() {
                glTextureView.onResume()
            }

            override fun onStop() {
                glTextureView.onPause()
            }
        }
    }

    private fun createSurfaceViewDelegate(context: Context): GLViewWrapperDelegate {
        return object : GLViewWrapperDelegate {

            private val glSurfaceView = GLSurfaceView(context, null).also { glSurfaceView ->
                glSurfaceView.setRenderer(rendererForwarder)
                addView(glSurfaceView)
            }

            override fun setRenderer(renderer: GLSurfaceView.Renderer) {
                glSurfaceView.setRenderer(renderer)
            }

            override fun onStart() {
                glSurfaceView.onResume()
            }

            override fun onStop() {
                glSurfaceView.onPause()
            }
        }
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

    private interface GLViewWrapperDelegate {
        fun setRenderer(renderer: GLSurfaceView.Renderer)
        fun onStart()
        fun onStop()
    }
}