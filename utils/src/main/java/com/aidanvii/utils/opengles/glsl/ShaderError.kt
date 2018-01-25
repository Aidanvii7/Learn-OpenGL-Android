package com.aidanvii.utils.opengles.glsl

sealed class ShaderError(error: String) : Error(error)

class CreateShaderError(
        shaderTypeName: String
) : ShaderError("Could not create $shaderTypeName.")

class CompileShaderError(
        shaderTypeName: String,
        compilationInfoLog: String
) : ShaderError("Could not compile $shaderTypeName. Log: $compilationInfoLog")