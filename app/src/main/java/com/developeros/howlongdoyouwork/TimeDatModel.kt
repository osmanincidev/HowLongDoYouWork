package com.developeros.howlongdoyouwork

import java.text.SimpleDateFormat
import java.util.*

class TimeDateModel() {
    val calender = Calendar.getInstance();
    val year = calender.get(Calendar.YEAR)
    val mont = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)
    val hour = calender.get(Calendar.HOUR_OF_DAY)
    val minute = calender.get(Calendar.MINUTE)
    override fun toString(): String {
        return "TimeDateModel(year=$year, month=$mont, day=$day, hour=$hour, minute=$minute)"
    }
}