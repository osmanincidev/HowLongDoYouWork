package com.developeros.howlongdoyouwork

class DataModel(var work:String,var Running:Boolean,time:Long) {
    var hour = time/3600
    var minutes = (time%3600)/60
    var seconds = time%60
    var Time : String = String.format("%02d:%02d:%02d", hour, minutes, seconds)
}