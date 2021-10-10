package com.grammer.grammerchecker.utils

import java.sql.Timestamp
import java.time.LocalDateTime

class DataTimeUtils {
    companion object{
        fun timestampOf(time : LocalDateTime) =
            Timestamp.valueOf(time)

        fun dateTimeOf(timestamp: Timestamp) =
            timestamp.toLocalDateTime()
    }
}