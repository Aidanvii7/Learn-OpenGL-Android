package com.aidanvii.utils.opengles.glsl

import android.content.Context
import android.support.annotation.RawRes
import com.aidanvii.utils.opengles.v20.GLWrapper

/*fun GLWrapper.provideVertexShader(context: Context, @RawRes shaderResourceId: Int) =
        ShaderLoaderProvider(context).invoke(this, shaderResourceId).compileVertexShader()

fun GLWrapper.provideFragmentShader(context: Context, @RawRes shaderResourceId: Int) =
        ShaderLoaderProvider(context).invoke(this, shaderResourceId).compileFragmentShader()*/

class ShaderLoaderProvider(
        private val context: Context
) : (GLWrapper, Int) -> ShaderLoader {

    override fun invoke(glWrapper: GLWrapper, @RawRes shaderResourceId: Int) =
            ShaderLoader(context, glWrapper, shaderResourceId)
}