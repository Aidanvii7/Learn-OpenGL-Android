package com.aidanvii.utils.opengles.glsl

sealed class GLError(error: String) : Error(error)

class CreateShaderError(shaderTypeName: String) : GLError("Could not create $shaderTypeName.")

class CompileShaderError(
        shaderTypeName: String,
        compilationInfoLog: String
) : GLError("Could not compile $shaderTypeName. Log: $compilationInfoLog")

class CreateShaderProgramError : GLError("Could not create shader program")

class LinkShaderProgramError(linkInfoLog: String) : GLError("Could not link shader program. Log: $linkInfoLog.")

class GetAttributeLocationError(attributeName: String) : GLError("Could not get attribute location for: $attributeName.")

class GetUniformLocationError(attributeName: String) : GLError("Could not get uniform location for: $attributeName.")