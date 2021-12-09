package com.developeros.howlongdoyouwork

class TimeManipulation {
    public fun DateTimeToSecond(year: String,month: String,day: String,hour: String,minute: String):Long{
        var Year = year.toLong()
        var Month = month.toLong()
        var Day = day.toLong()
        var Hour = hour.toLong()
        var Minute = minute.toLong()
        var seconds : Long =0
        seconds = Minute*60
        seconds= seconds + Hour*3600
        seconds= seconds + Day*86400
        seconds= seconds + Month*86400*30
        seconds= seconds + Year*86400*30*12
        return seconds
    }

}