package app.briota.sia.Front_End.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RRescue

class RedMedicineAdapter(val context: Context, val redMedicineList: ArrayList<RRescue>) :
    RecyclerView.Adapter<RedMedicineAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtHeading : TextView = view.findViewById(R.id.medHeading)
        var txtmedName: TextView = view.findViewById(R.id.med_name)
        var txtmedPuff: TextView = view.findViewById(R.id.med_puffs)
        var med_take_morning: TextView = view.findViewById(R.id.med_take_morning)
        var med_take_afternoon: TextView = view.findViewById(R.id.med_take_afternoon)
        var med_take_evening: TextView = view.findViewById(R.id.med_take_evening)
        var med_take_night: TextView = view.findViewById(R.id.med_take_night)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.green_condition_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
        if(redMedicineList.size > 0) {
            if (redMedicineList[position].category.equals("rescue")) {
                holder.txtHeading.setText(R.string.your_rescue_medications)
            }
            holder.txtmedName.text = redMedicineList[position].medicineName
            holder.txtmedPuff.text = redMedicineList[position].medicinePuffCount

            if (redMedicineList[position].morning == true) {
                holder.med_take_morning.setText(R.string.morning)
            }

            if (redMedicineList[position].afternoon == true) {
                holder.med_take_afternoon.setText(R.string.Afternoon)
            }

            if (redMedicineList[position].evening == true) {
                holder.med_take_evening.setText(R.string.Evening)
            }

            if (redMedicineList[position].night == true) {
                holder.med_take_night.setText(R.string.Night)
            }
        }


    }

    override fun getItemCount(): Int {

        return redMedicineList.size

    }

}