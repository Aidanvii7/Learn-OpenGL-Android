package com.aidanvii.utils.opengles

import android.app.Activity
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.databinding.BindingAdapter
import android.opengl.GLSurfaceView
import android.support.v4.app.FragmentActivity
import com.aidanvii.utils.R
import com.aidanvii.utils.activity.ActivityFinisher
import com.aidanvii.utils.databinding.getTrackedValue
import com.aidanvii.utils.databinding.setTrackedValue
import com.aidanvii.utils.databinding.trackInstance
import com.aidanvii.utils.opengles.v20.OpenGLES2SupportChecker


@BindingAdapter("android:renderer")
fun GLSurfaceViewWrapper._bind(renderer: GLSurfaceView.Renderer?) {
    trackInstance(
            instanceResId = R.id.gl_renderer,
            newInstance = renderer,
            onAttached = {
                if (!OpenGLES2SupportChecker.provide(context).supportsEs2) {
                    ActivityFinisher(context as Activity).finishWithToast("This device does not support OpenGL ES 2.0")
                } else onRendererAttached(it)
            }
    )
}

private fun GLSurfaceViewWrapper.onRendererAttached(renderer: GLSurfaceView.Renderer) {
    if (!rendererSet) {
        this.renderer = renderer
        tryAttachLifecycleObserver()
        rendererSet = true
    } else throwRendererSetMoreThanOnce()
}

private var GLSurfaceViewWrapper.rendererSet: Boolean
    get() = getTrackedValue(R.id.gl_has_renderer) ?: false
    set(value) = setTrackedValue(R.id.gl_has_renderer, value)

private fun GLSurfaceViewWrapper.tryAttachLifecycleObserver() {
    (context as? FragmentActivity)?.apply {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) = onStart()
            override fun onStop(owner: LifecycleOwner) = onStop()
        })
    }
}

private fun throwRendererSetMoreThanOnce(): Nothing =
        throw IllegalStateException("cannot set ${GLSurfaceView.Renderer::class.java.simpleName} more than once")