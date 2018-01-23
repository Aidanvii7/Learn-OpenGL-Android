package com.aidanvii.utils.logger

object CompositeLogger : CompositeLoggerDelegate {

    private var tagPrefix = ""

    private val delegates: MutableList<LoggerDelegate> = mutableListOf()

    @Synchronized
    override fun attachDelegate(delegate: LoggerDelegate) {
        delegates.add(delegate)
    }

    @Synchronized
    override fun detachDelegate(delegate: LoggerDelegate) {
        delegates.remove(delegate)
    }

    fun setTagPrefix(tagPrefix: String) {
        CompositeLogger.tagPrefix = tagPrefix
    }

    override fun d(tag: String, message: String) = delegates.forEach { it.d(tagPrefix + tag, message) }
}