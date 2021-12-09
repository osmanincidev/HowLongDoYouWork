package com.developeros.howlongdoyouwork

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),
        View.OnClickListener,
        AddWorkDialogListener{
    lateinit var recyclerView:RecyclerView
    lateinit var recyclewViewAdapter: RecyclewViewAdapter
   lateinit var dataModel: DataModel
    lateinit var addItem : ImageView
    var dataToDisk = DataToDisk(this)//save data to arraylist to later save to file
    var deleteIndex: Int = -1
    var newTask: AddNewTaskDialog = AddNewTaskDialog()
    var deleteWorkDialog = DeleteWorkDialog()
    var Items: ArrayList<DataModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addItem = findViewById(R.id.Add)
        addItem.setOnClickListener(this)
    }
    override fun onStart(){
        super.onStart()
        Log.d("onStart called-->","")
        try {
            dataToDisk.UserData=ArrayList()
            dataToDisk.ReadfromDisk()
            addSavedWork()
        }catch (e :Exception){
            Log.d("onStart-->",e.toString())
        }
    }
    override fun onStop() {
        super.onStop()
        dataToDisk.run()

    }
    fun addSavedWork(){

        var stringManipulation = StringManipulation()
        var savedDate = stringManipulation.StringToStringArray(dataToDisk.UserData[0])
        var jsonObject:JSONObject
        var year = ""
        var month = ""
        var day = ""
        var hour = ""
        var minute = ""
        var totalWork = ""
        var running = ""
        var work = ""
        /*
        UserData is the format of saveDataModel which is also saved to internal storage
        savedData is Json format of UserData.
         */
        for (item in savedDate){
            if(item.trim().length>0){
                jsonObject = JSONObject("{"+item+"}")
                Log.d("DataToDisk-year->", jsonObject.get(getString(R.string.year)) as String)
                year = jsonObject.get(getString(R.string.year)) as String
                month = jsonObject.get(getString(R.string.month)) as String
                day = jsonObject.get(getString(R.string.day)) as String
                hour = jsonObject.get(getString(R.string.hour)) as String
                minute = jsonObject.get(getString(R.string.minute)) as String
                totalWork = jsonObject.get(getString(R.string.totalWork)) as String
                running = jsonObject.get(getString(R.string.running)) as String
                work = jsonObject.get(getString(R.string.work)) as String

                dataModel= DataModel(work,running.toBoolean(),totalWork.toLong())
                Items.add(dataModel)

            }
        }
        iniRecView()
    }
    private fun AddNewWork(work: String){
        dataModel= DataModel(work,false,0)
        Items.add(dataModel)
        iniRecView()
    }
    private fun iniRecView(){
        recyclerView=findViewById(R.id.RecView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclewViewAdapter= RecyclewViewAdapter(this,Items)
        recyclerView.adapter = recyclewViewAdapter
    }
    private fun SaveTimeNow(){
        var timeDateModel  = TimeDateModel()
       var  prefences = this.getSharedPreferences(getString(R.string.TIME_DATE_MODEL),Context.MODE_PRIVATE)
        prefences.edit().putString(getString(R.string.TIME_DATE_MODEL),timeDateModel.toString()).apply()
    }
    private fun TimeFromPrefences(index:Int){
        var  prefences = this.getSharedPreferences(getString(R.string.TIME_DATE_MODEL),
                Context.MODE_PRIVATE)
        val time= prefences.getString(getString(R.string.TIME_DATE_MODEL),"")
        var string = time.toString()
        string = string.replace("=",":")
        string = string.replace("TimeDateModel(","{")
        string = string.replace(")","}")
        var jsonObject = JSONObject(string)
        var year = jsonObject.getString(getString(R.string.year))
        var month = jsonObject.getString(getString(R.string.month))
        var day = jsonObject.getString(getString(R.string.day))
        var hour = jsonObject.getString(getString(R.string.hour))
        var minute = jsonObject.getString(getString(R.string.minute))
        var timeManipulation = TimeManipulation()
        var before= timeManipulation.DateTimeToSecond(year,month,day,hour,minute)
        var timeDateModel : TimeDateModel = TimeDateModel()//gettin time now
        year = timeDateModel.year.toString()
        month = timeDateModel.mont.toString()
        day = timeDateModel.day.toString()
        hour = timeDateModel.hour.toString()
        minute = timeDateModel.minute.toString()
        var now= timeManipulation.DateTimeToSecond(year,month,day,hour,minute)
        var work = Items.get(index).work
        var running = Items.get(index).Running.toString()
        var totalWork=(now-before).toString()
        var dataToSave= SaveDataModel()
        dataToSave.DataToSave(year,month,day,hour, minute, totalWork, running, work)
        dataToDisk.UserData.add(dataToSave.toString())
        if(now-before < 0){

        }
    }
    override fun onClick(v: View?) {
        if(v?.id  == R.id.Add){
            newTask.show(supportFragmentManager,"Dialog")
        }
    }
    override fun onDialogPositiveClick(string: String) {
        AddNewWork(string)
    }
    override fun onDialogPositiveClick(int: Int) {
        if(deleteIndex==-1){
            //display dialog
            deleteIndex=int;
            deleteWorkDialog.show(supportFragmentManager,"Dialog")
        }else{
            //remove item
            Items.removeAt(deleteIndex)
            deleteIndex=-1;
            iniRecView()
        }
    }
    override fun onDialogPositiveClick(int: Int, running: Boolean) {
       if(running){
           //the user wants to run thi
            var dataModel = DataModel(Items.get(int).work,running,0)
           var firstData= Items.get(0)
           if(firstData.Running){
               firstData.Running=false
               TimeFromPrefences(0)
           }
           Items.set(int,firstData)
           Items.set(0,dataModel)
           SaveTimeNow()
           var  prefences = this.getSharedPreferences(getString(R.string.FIRS_INDEX_STATE),Context.MODE_PRIVATE)
           prefences.edit().putString(getString(R.string.FIRS_INDEX_STATE),"true").apply()

       }else{
           var currentItem= Items.get(int)
           if(currentItem.Running){
               currentItem.Running=false
               Items.set(int,currentItem)
           }
           TimeFromPrefences(int)
       }
        var  prefences = this.getSharedPreferences(getString(R.string.FIRS_INDEX_STATE),Context.MODE_PRIVATE)
        prefences.edit().putString(getString(R.string.FIRS_INDEX_STATE),"false").apply()
        iniRecView()
    }
    override fun onDialogNegativeClick(string: String) {}
}