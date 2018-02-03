package com.aidanvii.utils

import android.content.Context
import android.content.res.Resources
import android.support.annotation.RawRes
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun Context.rawResourceAsString(@RawRes resourceId: Int): String {
    return StringBuilder().run {
        try {
            resources.openRawResource(resourceId).use { inputStream ->
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                bufferedReader.readLines().forEach { line ->
                    append(line)
                    append('\n')
                }
                toString()
            }
        } catch (ioException: IOException) {
            throw RuntimeException("Could not open resource: " + resourceId, ioException)
        } catch (resourceNotFoundException: Resources.NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId", resourceNotFoundException)
        }
    }
}