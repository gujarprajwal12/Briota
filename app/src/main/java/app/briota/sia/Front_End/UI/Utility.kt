package app.briota.sia.Front_End.UI


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import app.briota.sia.R
import java.text.SimpleDateFormat
import java.util.*


class Utility {


    companion object {
        private var mUtility: Utility? = null
        private var dialog: Dialog? = null

        val sharedInstance: Utility
            get() {
                if (mUtility == null) {
                    mUtility = Utility()
                }
                return mUtility as Utility
            }
    }

    fun showDialog(context: Context) {

        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.dialog_progress)

        dialog!!.window!!.attributes.width = ActionBar.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER



        dialog!!.show()

    }

    fun showToastSuccess(mContext: Context?, text: String) {
        val toast = Toast(mContext)
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.custom_toast, null)
        val textView =
            view.findViewById<View>(R.id.txtToast) as TextView
        textView.setBackgroundResource(R.color.TextBlue)
        textView.text = "" + text
        toast.view = view
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.show()

        val handler = Handler()
        handler.postDelayed(Runnable { toast.cancel() }, 3000)
    }

    fun showToastError(mContext: Context?, text: String) {

        try {
            val toast = Toast(mContext)
            val view: View =
                LayoutInflater.from(mContext).inflate(R.layout.custom_toast, null)
            val textView =
                view.findViewById<View>(R.id.txtToast) as TextView
            textView.text = "" + text
            textView.setBackgroundResource(R.color.text_red)
            toast.view = view
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.show()
            val handler = Handler()
            handler.postDelayed(Runnable { toast.cancel() }, 3000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun showProgressDialog(mContext: Context) {

        try {

            dialog = Dialog(mContext)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog!!.setContentView(R.layout.dialog_progress)
            // dialog_custom.getWindow().getAttributes().width = ActionBar.LayoutParams.FILL_PARENT;
            dialog!!.show()
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgressDialog() {
        try {
            if (dialog != null && dialog!!.isShowing)
                dialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
