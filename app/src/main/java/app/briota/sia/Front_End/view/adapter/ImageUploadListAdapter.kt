package app.briota.sia.Front_End.view.adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.UI.MedicationsActivity
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlanList.data
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GetActionPlanDetailsModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ImageUploadListAdapter(val context: Context, val imgList: ArrayList<data>) :
    RecyclerView.Adapter<ImageUploadListAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var uploader_name: TextView = view.findViewById(R.id.uploaderName)
        var uploader_date : TextView = view.findViewById(R.id.uploaderDate)
        var btn_actionplan : Button =  view.findViewById(R.id.btn_actionplan)
       // var uploader_time : TextView = view.findViewById(R.id.creator_time)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.upload_image_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        try {

            val dateStr1 =  imgList[position].date!!
            val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            df1.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date? = df1.parse(dateStr1)
            df1.timeZone = TimeZone.getDefault()
            val df2 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss a")
            val formattedDate: String = df2.format(date)
            holder.uploader_date.text = formattedDate.substring(0,10)  + "   " +formattedDate.substring(11,16)+" "+formattedDate.substring(19,22)

        }
        catch (e : Exception)
        {
            Log.d(ContentValues.TAG, "setReading: "+e)
        }


        if(imgList[position].doctor_name.equals("0"))
        {
            holder.uploader_name.setText(R.string.created_by_myself)
        }
        else {
            holder.uploader_name.text = context.resources.getString(R.string.created_by_Dr)+ " " +imgList[position].doctor_name
        }

        //holder.uploader_date.text = imgList[position].date!!.substring(0,10)

//        holder.itemView.setOnClickListener {
//
//            Log.d(ContentValues.TAG, "onBindViewHolder: "+imgList[position].id)
//            Log.d(ContentValues.TAG, "onBindViewHolder: "+imgList[position].id)
//
//            val intent = Intent(context , MedicationsActivity::class.java)
//            intent.putExtra("id",imgList[position].id)
//            context.startActivity(intent)
//        }


        holder.btn_actionplan.setOnClickListener {

            Log.d(ContentValues.TAG, "onBindViewHolder: "+imgList[position].id)
            Log.d(ContentValues.TAG, "onBindViewHolder: "+imgList[position].id)

            val intent = Intent(context , MedicationsActivity::class.java)
            intent.putExtra("id",imgList[position].id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return imgList.size
    }
}