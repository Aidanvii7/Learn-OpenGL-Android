package com.aidanvii.utils.opengles

import android.app.Activity
import android.databinding.BindingAdapter
import android.opengl.GLSurfaceView
import com.aidanvii.utils.R
import com.aidanvii.utils.activity.ActivityFinisher
import com.aidanvii.utils.databinding.trackInstance
import com.aidanvii.utils.opengles.v20.OpenGLES2SupportChecker


@BindingAdapter("android:renderer")
fun GLViewWrapper._bind(renderer: GLSurfaceView.Renderer?) {
    trackInstance(
            instanceResId = R.id.gl_renderer,
            newInstance = renderer,
            onAttached = {
                this.renderer = it
            },
            onDetached = {
                this.renderer = null
            }
    )
}