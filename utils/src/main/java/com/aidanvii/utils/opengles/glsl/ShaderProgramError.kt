package com.aidanvii.utils.opengles.glsl

class ShaderProgramError(
        linkInfoLog: String
) : Error("Could not link shader program. Log: $linkInfoLog.")