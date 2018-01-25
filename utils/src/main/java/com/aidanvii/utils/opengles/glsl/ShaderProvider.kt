package com.aidanvii.utils.opengles.glsl

import android.content.Context
import android.support.annotation.RawRes
import com.aidanvii.utils.opengles.v20.OpenGLES20

class ShaderProvider(
        private val context: Context
) : (OpenGLES20, Int) -> ShaderLoader {

    override fun invoke(openGLES20: OpenGLES20, @RawRes shaderResourceId: Int) =
            ShaderLoader(context, openGLES20, shaderResourceId)
}