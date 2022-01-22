package com.example.alarmproject.util.system

import com.example.alarmproject.BuildConfig

class Logger {

    companion object {
        private const val LOG_TAG = "LOGGER SAY : "
        private const val LOG_TYPE_PROCESS = 1
        private const val LOG_TYPE_VERBOSE = 2


        fun process(msg: String, type: Int = LOG_TYPE_PROCESS) {
            if (BuildConfig.DEBUG) {
                createLog(type, msg)
            }
        }

        private fun createLog(type: Int, msg: String) {
            when (type) {
                LOG_TYPE_PROCESS -> {
                    println("PROCESS -> ", msg)
                }
                LOG_TYPE_VERBOSE -> {}
            }
        }

        private fun println(tagHeader: String, msg: String) {
            println(LOG_TAG + tagHeader + msg)
        }
    }
}