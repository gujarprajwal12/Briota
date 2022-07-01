package app.briota.sia.Front_End.view.adapter

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.UI.Notification
import app.briota.sia.Front_End.UI.ReminderActivity
import app.briota.sia.Front_End.UI.Utility
import app.briota.sia.Front_End.UI.titleExtra
import app.briota.sia.R
import app.briota.sia.integration_layer.models.User_Detail.ReminderModel.data
import java.util.*
import kotlin.collections.ArrayList


class ReminderListAdapter(val context : Context,val remList: ArrayList<data>)
    : RecyclerView.Adapter<ReminderListAdapter.ViewHolder>(){

    var medicationStatus : Boolean = false
    var exerciseStatus : Boolean = false
    var dietStatus : Boolean = false
    var dialog : Dialog? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtReminder : TextView = view.findViewById(R.id.txtReminder)
        var txtTime : TextView = view.findViewById(R.id.txtTime)


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.reminder_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if(remList[position].medication == true)
        {
            holder.txtReminder.setText(R.string.medicationreminder)
        }
        else if(remList[position].exercise == true)
        {
            holder.txtReminder.setText(R.string.exercisereminder)
        }
        else
        {
            holder.txtReminder.setText(R.string.dietreminder)
        }
        holder.txtTime.text = remList[position].added_time

        holder.itemView.setOnClickListener {


            dialog = Dialog(context)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.alaram_dialog)
            dialog!!.show()


            var   btn_save_reminder : Button = dialog!!.findViewById(R.id.btn_save_reminder)
            btn_save_reminder.visibility = View.GONE

            var   btn_update_reminder : Button = dialog!!.findViewById(R.id.btn_update_reminder)
            btn_update_reminder.visibility = View.VISIBLE
            var rg_Medication : RadioGroup = dialog!!.findViewById(R.id.rg_Medication)
            var btn_Radio_Medication : RadioButton = dialog!!.findViewById(R.id.btn_Radio_Medication)
            var rg_Exercise : RadioGroup = dialog!!.findViewById(R.id.rg_Exercise)
            var btn_Radio_Exercise : RadioButton = dialog!!.findViewById(R.id.btn_Radio_Exercise)
            var rg_Diet : RadioGroup = dialog!!.findViewById(R.id.rg_Diet)
            var btn_Radio_Diet : RadioButton = dialog!!.findViewById(R.id.btn_Radio_Diet)

            var timePicker : TimePicker = dialog!!.findViewById(R.id.timePicker)
            timePicker.setIs24HourView(true)

            if(remList[position].medication == true)
            {
                btn_Radio_Medication.isChecked = true
            }
            else if(remList[position].exercise == true)
            {
                btn_Radio_Exercise.isChecked = true
            }
            else{
                btn_Radio_Diet.isChecked = true
            }

            btn_Radio_Medication.setOnClickListener {
                rg_Exercise.clearCheck()
                rg_Diet.clearCheck()
                medicationStatus = true
            }

            btn_Radio_Exercise.setOnClickListener {
                rg_Medication.clearCheck()
                rg_Diet.clearCheck()
                exerciseStatus = true
            }

            btn_Radio_Diet.setOnClickListener {
                rg_Medication.clearCheck()
                rg_Exercise.clearCheck()
                dietStatus = true
            }



            btn_update_reminder.setOnClickListener {
                (context as ReminderActivity).createNotificationChannel()

                var timePicker : TimePicker = dialog!!.findViewById(R.id.timePicker)
                timePicker.setIs24HourView(true)

                val ReminderHour = timePicker.hour
                val ReminderMinute = timePicker.minute
                val ReminderTime = "$ReminderHour:$ReminderMinute"

                Log.d(ContentValues.TAG, "onClick: "+ReminderTime)
                print(ReminderTime)

                (context).updateReminder(
                    remList[position].id,medicationStatus,exerciseStatus,dietStatus,ReminderTime)

                val intent = Intent(context, Notification::class.java)

                var title : String? = null

                if(medicationStatus == true)
                {
                    title = "Medication"

                }
                else if(exerciseStatus == true)
                {
                    title = "Exercise"
                }
                else
                {
                    title = "Diet"
                }

                intent.putExtra(titleExtra, title)

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    remList[position].id!!,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


                val time =getTime()
                Log.d(TAG, "onBindViewHolder: "+time)
                print(time)

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                )

                dialog!!.dismiss()
            }
        }
    }


    override fun getItemCount(): Int {

        return remList.size
    }

    fun getTime(): Long {

        val timePicker : TimePicker = dialog!!.findViewById(R.id.timePicker)
        val datePicker : DatePicker = dialog!!.findViewById(R.id.datePicker)


        val minute = timePicker.minute
        val hour = timePicker.hour
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }


}