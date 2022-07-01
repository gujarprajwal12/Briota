package app.briota.sia.Front_End.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YRescue

class YellowRescueAdapter(val context: Context,val yellowRescueList: ArrayList<YRescue>?) :

    RecyclerView.Adapter<YellowRescueAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtHeading : TextView = view.findViewById(R.id.medHeading)
        var txtmedName: TextView = view.findViewById(R.id.med_name)
        var txtmedPuff: TextView = view.findViewById(R.id.med_puffs)
        var med_take_morning : TextView = view.findViewById(R.id.med_take_morning)
        var med_take_afternoon : TextView = view.findViewById(R.id.med_take_afternoon)
        var med_take_evening : TextView = view.findViewById(R.id.med_take_evening)
        var med_take_night : TextView = view.findViewById(R.id.med_take_night)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.green_condition_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (yellowRescueList!!.size > 0)
        {
            if(yellowRescueList[position].category.equals("rescue"))
            {
                holder.txtHeading.setText(R.string.your_rescue_medications)
            }
            holder.txtmedName.text = yellowRescueList[position].medicineName

            holder.txtmedPuff.text = yellowRescueList[position].medicinePuffCount



            if (yellowRescueList[position].morning == true) {
                holder.med_take_morning.setText(R.string.morning)
            }

            if (yellowRescueList[position].afternoon == true) {
                holder.med_take_afternoon.setText(R.string.Afternoon)
            }

            if (yellowRescueList[position].evening == true) {
                holder.med_take_evening.setText(R.string.Evening)
            }

            if (yellowRescueList[position].night == true) {
                holder.med_take_night.setText(R.string.Night)
            }


        }



    }

    override fun getItemCount(): Int {

       return yellowRescueList!!.size

    }


}