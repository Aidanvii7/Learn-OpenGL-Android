package com.aidanvii.utils.opengles.v20

import com.aidanvii.utils.arch.ViewModelFactory
import com.aidanvii.utils.databinding.ObservableViewModel

open class GLRendererViewModel<out Renderer : GLRenderer>(
        val renderer: Renderer
) : ObservableViewModel() {

    open class Factory<out Renderer : GLRenderer>(
            private val renderer: Renderer
    ) : ViewModelFactory.TypedFactory<GLRendererViewModel<Renderer>> {
        override fun create() = GLRendererViewModel(
                renderer = renderer
        )
    }
}