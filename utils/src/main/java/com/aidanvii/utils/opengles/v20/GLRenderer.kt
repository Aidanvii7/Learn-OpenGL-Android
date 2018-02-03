package com.aidanvii.utils.opengles.v20

import android.opengl.GLSurfaceView
import android.support.annotation.RestrictTo
import com.aidanvii.utils.Provider
import com.aidanvii.utils.databinding.ObservableViewModel
import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.glsl.ShaderLoaderProvider
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class GLRenderer(
        val shaderLoaderProvider: ShaderLoaderProvider
) : GLSurfaceView.Renderer, GLWrapper by Factory.provideOpenGLES20() {

    @GLThread
    abstract fun onSurfaceCreated(config: EGLConfig)

    @GLThread
    abstract fun onSurfaceChanged(width: Int, height: Int)

    @GLThread
    abstract fun onDrawFrame()

    @GLThread
    final override fun onDrawFrame(gl: GL10?) =
            onDrawFrame()

    @GLThread
    final override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) =
            onSurfaceChanged(width, height)

    @GLThread
    final override fun onSurfaceCreated(gl: GL10?, config: EGLConfig) =
            onSurfaceCreated(config)

    object Factory {
        private val DEFAULT_PROVIDE_GL_WRAPPER: Provider<GLWrapper> = { object : GLWrapper {} }
        private var provideGlWrapper = DEFAULT_PROVIDE_GL_WRAPPER

        internal fun provideOpenGLES20(): GLWrapper = provideGlWrapper()

        @RestrictTo(RestrictTo.Scope.TESTS)
        fun <T : ObservableViewModel> tested(
                mockGlWrapper: GLWrapper,
                provideTested: Provider<T>
        ): T {
            provideGlWrapper = { mockGlWrapper }
            return provideTested().also {
                provideGlWrapper = DEFAULT_PROVIDE_GL_WRAPPER
            }
        }
    }
}