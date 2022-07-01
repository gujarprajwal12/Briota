package app.briota.sia.Front_End.view.adapter



import android.content.ContentValues

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.PeakData

import java.text.SimpleDateFormat


import java.util.*


class PeakReadingAdapter(val context : Context, val peakList : ArrayList<PeakData>) :
    RecyclerView.Adapter<PeakReadingAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtDate: TextView = view.findViewById(R.id.date)
        var txtTime: TextView = view.findViewById(R.id.time)
        var image : ImageView = view.findViewById(R.id.myimage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.peak_reading_layout,parent,false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            val dateStr =  peakList[position].createdDatetime!!
            val df = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date? = df.parse(dateStr)
            df.timeZone = TimeZone.getDefault()
            val df3 = SimpleDateFormat("dd-MM-yyyy hh:mm a")
            val formattedDate: String = df3.format(date)
            holder.txtTime.text = formattedDate.substring(11,19)
            holder.txtDate.text = formattedDate.substring(0,10)
        }
        catch (e : Exception)
        {
            Log.d(ContentValues.TAG, "setReading: "+e)
        }


//        val inputFormatter: DateTimeFormatter =
//            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
//                .withZone(DateTimeZone.UTC)



      var  time = peakList[position].readingDatetime!!.substring(11,19)

//        Time timeFormat = new Time();
//         timeFormat.set(time+TimeZone.getDefault().getOffset(time));
//
//        long localTime = timeFormat.tiMillis(true);


        if(peakList[position].dailyZone!!.equals("Green"))
        {
            holder.image.setImageResource(R.drawable.greensymextrasmall)
        }
        if(peakList[position].dailyZone!!.equals("Yellow"))
        {
            holder.image.setImageResource(R.drawable.yellowextrasmall)
        }
        if(peakList[position].dailyZone!!.equals("Red"))
        {
            holder.image.setImageResource(R.drawable.redsymextrasmall)
        }
    }

    override fun getItemCount(): Int {
       return peakList.size
    }


}







