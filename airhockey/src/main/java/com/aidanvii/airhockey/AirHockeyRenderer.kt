package com.aidanvii.airhockey

import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_LINES
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.GL_TRIANGLES
import com.aidanvii.utils.opengles.GLThread
import com.aidanvii.utils.opengles.vertexBuilder2D
import com.aidanvii.utils.opengles.glsl.ShaderProgram
import com.aidanvii.utils.opengles.glsl.ShaderLoaderProvider
import com.aidanvii.utils.opengles.v20.GLRenderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig

class AirHockeyRenderer(
        shaderLoaderProvider: ShaderLoaderProvider
) : GLRenderer(shaderLoaderProvider) {

    val tableVerticesWithTriangles = vertexBuilder2D(20) {
        triangle(
                point1X = -0.5f, point1Y = -0.5f,
                point2X = 0.5f, point2Y = 0.5f,
                point3X = -0.5f, point3Y = 0.5f
        )
        triangle(
                point1X = -0.5f, point1Y = -0.5f,
                point2X = 0.5f, point2Y = -0.5f,
                point3X = 0.5f, point3Y = 0.5f
        )
        line(
                point1X = -0.5f, point1Y = 0f,
                point2X = 0.5f, point2Y = 0f
        )
        point(pointX = 0f, pointY = -0.25f)
        point(pointX = 0f, pointY = 0.25f)
    }.build()

    val vertexData = ByteBuffer
            // allocate block of native memory
            .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            // order the bytes in the platforms native order (http://en.wikipedia.org/wiki/Endianness)
            .order(ByteOrder.nativeOrder())
            // convert to FloatBuffer to not deal with bytes directly
            .asFloatBuffer().apply {
        // copy memory from Android runtime to native memory
        put(tableVerticesWithTriangles)
    }

    @get:GLThread
    val shaderProgram by lazy {
        ShaderProgram(
                glWrapper = this,
                vertexShader = shaderLoaderProvider(
                        glWrapper = this,
                        shaderResourceId = R.raw.simple_vertex_shader
                ).compileVertexShader(AirHockeyAttributeContainer(this)),
                fragmentShader = shaderLoaderProvider(
                        glWrapper = this,
                        shaderResourceId = R.raw.simple_fragment_shader
                ).compileFragmentShader(AirHockeyUniformContainer(this))
        )
    }

    @GLThread
    override fun onSurfaceCreated(config: EGLConfig) {
        glClearColor(red = 0f, green = 0f, blue = 0f)
        glUseProgram(shaderProgram.programObjectId)
        vertexData.position(0)
        shaderProgram.vertexShader.attributeContainer.apply {
            shaderProgram.fragmentShader.uniformContainer.apply {
                glVertexAttribPointer(
                        attributeIndex = aPosition,
                        size = 2,
                        type = GL_FLOAT,
                        normalized = false,
                        stride = 0,
                        ptr = vertexData
                )
                glEnableVertexAttribArray(aPosition)
            }
        }

    }

    @GLThread
    override fun onSurfaceChanged(width: Int, height: Int) {
        glViewport(width, height)
    }

    @GLThread
    override fun onDrawFrame() {
        glClear()
        shaderProgram.vertexShader.attributeContainer.apply {
            shaderProgram.fragmentShader.uniformContainer.apply {
                drawTable()
                drawDividingLine()
                drawMallet1()
                drawMallet2()
            }
        }
    }

    private fun AirHockeyUniformContainer.drawTable() {
        glUniform4f(uColor, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)
    }

    private fun AirHockeyUniformContainer.drawDividingLine() {
        glUniform4f(uColor, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 6, 2)
    }

    private fun AirHockeyUniformContainer.drawMallet1() {
        glUniform4f(uColor, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_POINTS, 8, 1)
    }

    private fun AirHockeyUniformContainer.drawMallet2() {
        glUniform4f(uColor, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_POINTS, 9, 1)
    }

    companion object {
        const val BYTES_PER_FLOAT = 4
    }
}