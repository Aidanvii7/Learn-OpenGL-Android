package com.aidanvii.utils.opengl.v20

import android.opengl.GLSurfaceView
import android.support.annotation.RestrictTo
import com.aidanvii.utils.Provider
import com.aidanvii.utils.databinding.ObservableViewModel
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class OpenGLES2Renderer : GLSurfaceView.Renderer, OpenGLES20 by Factory.provideOpenGLES20() {

    abstract fun onSurfaceCreated(config: EGLConfig)
    abstract fun onSurfaceChanged(width: Int, height: Int)
    abstract fun onDrawFrame()

    final override fun onDrawFrame(gl: GL10?) =
            onDrawFrame()

    final override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) =
            onSurfaceChanged(width, height)

    final override fun onSurfaceCreated(gl: GL10?, config: EGLConfig) =
            onSurfaceCreated(config)

    object Factory {
        private val DEFAULT_PROVIDE_OPEN_GLES_20: Provider<OpenGLES20> = { object : OpenGLES20 {} }
        private var provideOpenGlES20 = DEFAULT_PROVIDE_OPEN_GLES_20

        internal fun provideOpenGLES20(): OpenGLES20 = provideOpenGlES20()

        @RestrictTo(RestrictTo.Scope.TESTS)
        fun <T : ObservableViewModel> tested(
                mockOpenGLES20: OpenGLES20,
                provideTested: Provider<T>
        ): T {
            this.provideOpenGlES20 = { mockOpenGLES20 }
            return provideTested().also {
                this.provideOpenGlES20 = DEFAULT_PROVIDE_OPEN_GLES_20
            }
        }
    }
}