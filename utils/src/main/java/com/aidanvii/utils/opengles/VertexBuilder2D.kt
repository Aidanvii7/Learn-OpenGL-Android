package com.aidanvii.utils.opengles

import android.support.annotation.IntRange

class VertexBuilder2D(initialSize: Int) {

    private var vertexData = FloatArray(initialSize)
    private var position = 0

    fun point(
            pointX: Int,
            pointY: Int
    ) = point(pointX.toFloat(), pointY.toFloat())

    fun point(
            pointX: Float,
            pointY: Float
    ) {
        if (vertexData.size < position + 2) {
            vertexData += 2
        }
        vertexData[position++] = pointX
        vertexData[position++] = pointY
    }

    fun line(
            point1X: Int,
            point1Y: Int,
            point2X: Int,
            point2Y: Int
    ) = line(point1X.toFloat(), point1Y.toFloat(), point2X.toFloat(), point2Y.toFloat())

    fun line(
            point1X: Float,
            point1Y: Float,
            point2X: Float,
            point2Y: Float
    ) {
        if (vertexData.size < position + 4) {
            vertexData += 4
        }
        vertexData[position++] = point1X
        vertexData[position++] = point1Y
        vertexData[position++] = point2X
        vertexData[position++] = point2Y
    }

    fun triangle(
            point1X: Int,
            point1Y: Int,
            point2X: Int,
            point2Y: Int,
            point3X: Int,
            point3Y: Int
    ) = triangle(point1X.toFloat(), point1Y.toFloat(), point2X.toFloat(), point2Y.toFloat(), point3X.toFloat(), point3Y.toFloat())

    fun triangle(
            point1X: Float,
            point1Y: Float,
            point2X: Float,
            point2Y: Float,
            point3X: Float,
            point3Y: Float
    ) {
        if (vertexData.size < position + 6) {
            vertexData += 6
        }
        vertexData[position++] = point1X
        vertexData[position++] = point1Y
        vertexData[position++] = point2X
        vertexData[position++] = point2Y
        vertexData[position++] = point3X
        vertexData[position++] = point3Y
    }

    fun build(): FloatArray {
        val newSize = position + 1
        if (vertexData.size > newSize) {
            val trim = newSize - vertexData.size
            vertexData -= trim
        }
        return vertexData
    }

    private operator fun FloatArray.plus(
            @IntRange(from = 1) padding: Int
    ) = FloatArray(size + padding).also { newFloatArray ->
        for (index in newFloatArray.indices) {
            newFloatArray[index] = if (index < size) this[index] else 0f
        }
    }

    private operator fun FloatArray.minus(
            @IntRange(from = 1) trim: Int
    ) = FloatArray(size - trim).also { newFloatArray ->
        for (index in newFloatArray.indices) {
            newFloatArray[index] = this[index]
        }
    }
}

inline fun vertexBuilder2D(
        initialSize: Int,
        block: VertexBuilder2D.() -> Unit
) = VertexBuilder2D(initialSize).executeBlock(block)

inline fun vertexBuilder2D(
        vertexBuilder2D: VertexBuilder2D,
        vertexBuilder: VertexBuilder2D.() -> Unit
) = vertexBuilder2D.executeBlock(vertexBuilder)

inline fun VertexBuilder2D.executeBlock(block: VertexBuilder2D.() -> Unit) = this.also { block() }