package app.briota.sia.Front_End.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.RedActionPlan.RSteroid

class RedSteroidAdapter(val context: Context, val redMedicineSteroid: ArrayList<RSteroid>?) :
    RecyclerView.Adapter<RedSteroidAdapter.ViewHolder>() {


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

        if(redMedicineSteroid!!.size > 0)
        {
            if (redMedicineSteroid[position].category.equals("steroid")) {
                holder.txtHeading.setText(R.string.your_oral_corsticosteroid_tablet)
            }
            holder.txtmedName.text = redMedicineSteroid[position].medicineName
            holder.txtmedPuff.text = redMedicineSteroid[position].medicinePuffCount

            if (redMedicineSteroid[position].morning == true) {
                holder.med_take_morning.setText(R.string.morning)
            }

            if (redMedicineSteroid[position].afternoon == true) {
                holder.med_take_afternoon.setText(R.string.Afternoon)
            }

            if (redMedicineSteroid[position].evening == true) {
                holder.med_take_evening.setText(R.string.Evening)
            }

            if (redMedicineSteroid[position].night == true) {
                holder.med_take_night.setText(R.string.Night)
            }
        }

    }

    override fun getItemCount(): Int {

        return redMedicineSteroid!!.size

    }

}