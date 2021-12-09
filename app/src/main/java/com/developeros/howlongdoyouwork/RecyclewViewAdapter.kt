package com.developeros.howlongdoyouwork

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class RecyclewViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private var Items:List<DataModel> = ArrayList();

    var context: Context

    constructor(context:Context,Items: List<DataModel>) : super() {
        this.Items = Items
        this.context= context

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_recview,parent,false)
        )

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RecViewHolder ->{
                holder.bind(context,Items.get(position),position)
            }
        }

    }
    override fun getItemCount(): Int {
        return Items.size
    }
    class RecViewHolder constructor(itemView: View):RecyclerView.ViewHolder(itemView){
        val work :TextView = itemView.findViewById(R.id.Work)
        val workTime :TextView = itemView.findViewById(R.id.Worktime)
        val deleteTask : ImageView =itemView.findViewById(R.id.deleteItem)
        val PlayStop: ImageView = itemView.findViewById(R.id.StartStop)
        lateinit var addWorkDialogListener: AddWorkDialogListener
        fun bind(context:Context,dataModel: DataModel,position: Int){
            work.setText(dataModel.work)
            workTime.setText(dataModel.Time)
            if(dataModel.Running){
                PlayStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_play_circle_24))
            }else{
                PlayStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_stop_circle_24))

            }
            deleteTask.setOnClickListener(){
                    addWorkDialogListener=context as AddWorkDialogListener
                    addWorkDialogListener.onDialogPositiveClick(position)
                }

            PlayStop.setOnClickListener (){
                addWorkDialogListener=context as AddWorkDialogListener
                addWorkDialogListener.onDialogPositiveClick(position,!dataModel.Running)
            }
        }

    }
}