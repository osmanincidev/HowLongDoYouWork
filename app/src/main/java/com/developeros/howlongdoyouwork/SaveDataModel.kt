package com.developeros.howlongdoyouwork

import java.time.Month

class SaveDataModel {
    var Year=""
    var Month=""
    var Day=""
    var Hour=""
    var Minute=""
    var TotalWork=""
    var Running=""
    var Work=""
    fun DataToSave(year: String,month: String,day: String,hour: String,minute: String,
                          totalWork:String,running:String,work:String){
         Year=year
         Month=month
         Day=day
         Hour=hour
         Minute=minute
         TotalWork=totalWork
         Running= running
         Work=work
    }
    override fun toString(): String {
        return "{year:'$Year'; month='$Month'; day:'$Day'; hour:'$Hour'; minute:'$Minute';" +
                " totalWork:'$TotalWork'; running:'$Running'; work:'$Work'}"
    }

}