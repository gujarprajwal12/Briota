package app.briota.sia.Front_End.view.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.Report.byDairy.data
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ZoneDairyAdapter(val context: Context, val zoneDairyList: ArrayList<data>,
) :
    RecyclerView.Adapter<ZoneDairyAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var zone_dairy_date: TextView = view.findViewById(R.id.zone_dairy_date)
        var zone_dairy_time : TextView = view.findViewById(R.id.zone_dairy_time)
        var zone_dairy_image : ImageView = view.findViewById(R.id.zone_dairy_img)

    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.zone_dairy_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        try {
            holder.zone_dairy_date.text = zoneDairyList[position].addedDate
            holder.zone_dairy_time.text = zoneDairyList[position].time



            if(zoneDairyList[position].followed == false)
            {
                holder.zone_dairy_image.setImageResource(R.drawable.wronglogo)
            }

        }catch (e : Exception)
        {
            Log.d(TAG, "onBindViewHolder: "+e)
        }

    }

    override fun getItemCount(): Int {
        return zoneDairyList.size
    }
}