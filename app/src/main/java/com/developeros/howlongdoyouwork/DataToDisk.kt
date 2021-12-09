package com.developeros.howlongdoyouwork
import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File

class DataToDisk(var context: Context): Thread() {
    var UserData:ArrayList<String> = ArrayList()
    var stringManipulation = StringManipulation()
     override fun run() {
        Log.d("run Thread called-->",UserData.toString())
        super.run()
         //save to disk for app

         context.openFileOutput(context.getString(R.string.Filename),Context.MODE_PRIVATE)
               .use {
                   it.write(UserData.toString().toByteArray())
               }
        UserData = ArrayList()
    }
    fun ReadfromDisk(){
        //read from there.
        UserData= ArrayList()
       var text= context.openFileInput(context.getString(R.string.Filename)).bufferedReader().useLines {
            lines -> lines.fold("") { some, text ->  some+text  }
        }
        text=text.replace("[","")
        text=text.replace("]","")
        UserData.add(text)
    }
}
