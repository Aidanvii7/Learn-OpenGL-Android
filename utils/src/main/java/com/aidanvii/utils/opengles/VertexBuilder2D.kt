package com.aidanvii.utils.opengles

import android.support.annotation.FloatRange
import android.support.annotation.IntRange

class VertexBuilder2D(initialSize: Int) {

    private var vertexData = FloatArray(initialSize)
    private var position = 0

    fun point(
            @IntRange(from = -1, to = 1) pointX: Int,
            @IntRange(from = -1, to = 1) pointY: Int
    ) = point(pointX.toFloat(), pointY.toFloat())

    fun point(
            @FloatRange(from = -1.0, to = 1.0) pointX: Float,
            @FloatRange(from = -1.0, to = 1.0) pointY: Float
    ) {
        if (vertexData.size < position + 2) {
            vertexData += 2
        }
        vertexData[position++] = pointX
        vertexData[position++] = pointY
    }

    fun line(
            @IntRange(from = -1, to = 1) point1X: Int,
            @IntRange(from = -1, to = 1) point1Y: Int,
            @IntRange(from = -1, to = 1) point2X: Int,
            @IntRange(from = -1, to = 1) point2Y: Int
    ) = line(point1X.toFloat(), point1Y.toFloat(), point2X.toFloat(), point2Y.toFloat())

    fun line(
            @FloatRange(from = -1.0, to = 1.0) point1X: Float,
            @FloatRange(from = -1.0, to = 1.0) point1Y: Float,
            @FloatRange(from = -1.0, to = 1.0) point2X: Float,
            @FloatRange(from = -1.0, to = 1.0) point2Y: Float
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
            @IntRange(from = -1, to = 1) point1X: Int,
            @IntRange(from = -1, to = 1) point1Y: Int,
            @IntRange(from = -1, to = 1) point2X: Int,
            @IntRange(from = -1, to = 1) point2Y: Int,
            @IntRange(from = -1, to = 1) point3X: Int,
            @IntRange(from = -1, to = 1) point3Y: Int
    ) = triangle(point1X.toFloat(), point1Y.toFloat(), point2X.toFloat(), point2Y.toFloat(), point3X.toFloat(), point3Y.toFloat())

    fun triangle(
            @FloatRange(from = -1.0, to = 1.0) point1X: Float,
            @FloatRange(from = -1.0, to = 1.0) point1Y: Float,
            @FloatRange(from = -1.0, to = 1.0) point2X: Float,
            @FloatRange(from = -1.0, to = 1.0) point2Y: Float,
            @FloatRange(from = -1.0, to = 1.0) point3X: Float,
            @FloatRange(from = -1.0, to = 1.0) point3Y: Float
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