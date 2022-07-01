package app.briota.sia.Front_End.view.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.UI.MedicationsActivity
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.data
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GetActionPlanDetailsModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreationListAdapter(val context: Context, val createdLists: ArrayList<data>,
                          val createdListModel : GetActionPlanDetailsModel
) :
    RecyclerView.Adapter<CreationListAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var creator_name: TextView = view.findViewById(R.id.creator_name)
        var creator_date : TextView = view.findViewById(R.id.creator_date)
        var creator_time : TextView = view.findViewById(R.id.creator_time)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.created_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        try {

            val dateStr =  createdLists[position].date!!
            val df = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date? = df.parse(dateStr)
            df.timeZone = TimeZone.getDefault()
            val df3 = SimpleDateFormat("hh:mm:ss a")
            val formattedDate: String = df3.format(date)
            holder.creator_time.text = formattedDate

        }
        catch (e : Exception)
        {
            Log.d(ContentValues.TAG, "setReading: "+e)
        }


        if(createdLists[position].doctor_name.equals("0"))
        {
            holder.creator_name.setText(R.string.created_by_myself)
        }
        else {
            holder.creator_name.text = context.resources.getString(R.string.created_by_Dr)+ " " +createdLists[position].doctor_name
        }

        holder.creator_date.text = createdLists[position].date!!.substring(0,10)

        holder.itemView.setOnClickListener {

            Log.d(TAG, "onBindViewHolder: "+createdLists[position].id)
            Log.d(TAG, "onBindViewHolder: "+createdLists[position].id)

            val intent = Intent(context , MedicationsActivity::class.java)
            intent.putExtra("id",createdLists[position].id)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return createdLists.size
    }
}