package com.aidanvii.utils.opengles.v20

import com.aidanvii.utils.arch.ViewModelFactory
import com.aidanvii.utils.databinding.ObservableViewModel

open class OpenGLES2ViewModel<out Renderer : GLRenderer>(
        val renderer: Renderer
) : ObservableViewModel() {

    open class Factory<out Renderer : GLRenderer>(
            private val renderer: Renderer
    ) : ViewModelFactory.TypedFactory<OpenGLES2ViewModel<Renderer>> {
        override fun create() = OpenGLES2ViewModel(
                renderer = renderer
        )
    }
}