package com.aidanvii.utils.logger

fun logD(tag: String, message: String) = CompositeLogger.d(tag, message)
fun logW(tag: String, message: String) = CompositeLogger.w(tag, message)
fun logV(tag: String, message: String) = CompositeLogger.v(tag, message)

fun Any.logD(message: String) = logD(javaClass.simpleName, message)
fun Any.logW(message: String) = logD(javaClass.simpleName, message)
fun Any.logV(message: String) = logD(javaClass.simpleName, message)

interface LoggerDelegate {
    fun d(tag: String, message: String)
    fun w(tag: String, message: String)
    fun v(tag: String, message: String)
}

interface CompositeLoggerDelegate : LoggerDelegate {
    fun attachDelegate(delegate: LoggerDelegate)
    fun detachDelegate(delegate: LoggerDelegate)
}