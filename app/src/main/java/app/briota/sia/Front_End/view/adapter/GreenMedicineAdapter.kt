package app.briota.sia.Front_End.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.Daily

class GreenMedicineAdapter(val context: Context,val medicineList: ArrayList<Daily>) :
        RecyclerView.Adapter<GreenMedicineAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtmedName: TextView = view.findViewById(R.id.med_name)
        var txtmedPuff: TextView = view.findViewById(R.id.med_puffs)
        var med_take_morning : TextView = view.findViewById(R.id.med_take_morning)
        var med_take_afternoon : TextView = view.findViewById(R.id.med_take_afternoon)
        var med_take_evening : TextView = view.findViewById(R.id.med_take_evening)
        var med_take_night : TextView = view.findViewById(R.id.med_take_night)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.green_condition_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){


        holder.txtmedName.text = medicineList[position].medicineName
        holder.txtmedPuff.text = medicineList[position].medicinePuffCount

        if(medicineList[position].morning == true)
        {
            holder.med_take_morning.setText(R.string.morning)
        }

        if(medicineList[position].afternoon == true)
        {
            holder.med_take_afternoon.setText(R.string.Afternoon)
        }

        if(medicineList[position].evening == true)
        {
            holder.med_take_evening.setText(R.string.Evening)
        }

        if(medicineList[position].night == true)
        {
            holder.med_take_night.setText(R.string.Night)
        }

    }

    override fun getItemCount(): Int {
        return medicineList.size
    }
}