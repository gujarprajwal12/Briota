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
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.Report.data
import okio.blackholeSink
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ZoneListAdapter(val context: Context, val zoneLists: ArrayList<data>,
) :
    RecyclerView.Adapter<ZoneListAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var zone_date: TextView = view.findViewById(R.id.zone_date)
        var zone_time : TextView = view.findViewById(R.id.zone_time)
        var zone_fev1 : TextView = view.findViewById(R.id.zone_fev1)
        var zone_pef : TextView = view.findViewById(R.id.zone_pef)

    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.zone_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        holder.zone_date.text = zoneLists[position].date!!.substring(0,10)
        //holder.zone_time.text = zoneLists[position].time!!.substring(0,8)


        try {

            if(zoneLists[position].fev_1 == null)
            {
                holder.zone_fev1.text = "0"
            }
            else {
                holder.zone_fev1.text = zoneLists[position].fev_1.toString()
            }
            if(zoneLists[position].pef == null)
            {
                holder.zone_pef.text = "0"
            }
            else {
                holder.zone_pef.text = zoneLists[position].pef.toString()
            }
        }catch (e:Exception)
        {
            Log.d(TAG, "onBindViewHolder: "+e)
        }

        try {


            holder.zone_time.text = zoneLists[position].time

        }
        catch (e : Exception)
        {
            Log.d(ContentValues.TAG, "setReading: "+e)
        }


    }

    override fun getItemCount(): Int {
        return zoneLists.size
    }
}