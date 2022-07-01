package app.briota.sia.Front_End.UI

import android.R
import android.app.PendingIntent.getActivity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.ZoneDairyAdapter
import app.briota.sia.Network.retrofit.api.RetrofitClient
import app.briota.sia.integration_layer.models.User_Detail.Report.byDairy.ReportbyDairy
import app.briota.sia.integration_layer.models.User_Detail.Report.byDairy.data
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_report_deatail_dairy.*
import kotlinx.android.synthetic.main.report_toolbar.*
import kotlinx.android.synthetic.main.toolbar.back_tol
import kotlinx.serialization.json.JsonNull.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ReportDeatailDairyActivity : AppCompatActivity() {

    val now: LocalDateTime = LocalDateTime.now()
    var diarydata: String? = null
    var dairy_start_date: String? = null
    var dairy_end_date: String? = null
    var dairy_start_date1: String? = null
    var dairy_end_date1: String? = null
    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    var output = formatter.format(now)
    var output1 = formatter.format(now.minusMonths(1))
    private var llPdf: LinearLayout? = null
    private var bitmap: Bitmap? = null
    lateinit var zoneDairyList: ArrayList<data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(app.briota.sia.R.layout.activity_report_deatail_dairy)

        diarydata = intent.getStringExtra("dairy")
        dairy_start_date = intent.getStringExtra("Start")
        dairy_end_date = intent.getStringExtra("End")
        dairy_start_date1 = intent.getStringExtra("Start1")
        dairy_end_date1 = intent.getStringExtra("End1")

        llPdf = findViewById(app.briota.sia.R.id.ReportLayout)


        initView()

        onClick()


        }

    fun initView() {


        img_cal.visibility = View.INVISIBLE

        if (diarydata.equals("exercise")) {
            zone_plan.text = "Exercise"
        } else if (diarydata.equals("diet")) {
            zone_plan.text = "Diet"
        } else {
            zone_plan.text = "Medication"
        }


    }


    fun onClick() {

        generate_Report.setOnClickListener {
            bitmap = loadBitmapFromView(llPdf!!, llPdf!!.getWidth(), llPdf!!.getHeight());

            generatePDF()
        }

        back_tol.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap? {
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.draw(c)
        return b
    }

    private fun generatePDF() {

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager

        val displaymetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displaymetrics)
        val hight = displaymetrics.heightPixels.toFloat()
        val width = displaymetrics.widthPixels.toFloat()

        val convertHighet = hight.toInt()
        val convertWidth = width.toInt()

        val document = PdfDocument()
        val pageInfo = PageInfo.Builder(convertWidth, convertHighet, 1).create()
        val page = document.startPage(pageInfo)

        val canvas: Canvas = page.getCanvas()

        val paint = Paint()
        canvas.drawPaint(paint)

        bitmap = Bitmap.createScaledBitmap(bitmap!!, convertWidth, convertHighet, true)

        paint.color = Color.BLUE
        canvas.drawBitmap(bitmap!!, 0f, 0f, null)
        document.finishPage(page)

        // write the document content

        // write the document content
        val targetPdf = "/sdcard/page.pdf"
        val filePath: File
        filePath = File(targetPdf)
        val mypath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "file.pdf"
        )
        try {
            document.writeTo(FileOutputStream(mypath))
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show()
        }


        // close the document

        // close the document
        document.close()
        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show()

//        val document = PdfDocument()
//        val pageInfo = PageInfo.Builder(convertWidth, convertHighet, 1).create()
//        val page = document.startPage(pageInfo)
//
//        val canvas: Canvas = page.canvas
//
//        val paint = Paint()
//        canvas.drawPaint(paint)
//
//        bitmap = Bitmap.createScaledBitmap(bitmap!!, convertWidth, convertHighet, true)
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap!!, 0F, 0F, null)
//        document.finishPage(page)
//
//        val targetPdf = "/sdcard/pdffromlayout.pdf"
//        val filePath: File
//        filePath = File(targetPdf)
//        try {
//            document.writeTo(FileOutputStream(filePath))
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show()
//        }
//
//        document.close();
//        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF()

    }

    private fun openGeneratedPDF() {

        val file = File("file.pdf")
        if (file.exists())
        {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri: Uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this@ReportDeatailDairyActivity,
                "No Application available to view pdf",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    override fun onResume() {
        super.onResume()

        var sh = getSharedPreferences("Briota", MODE_PRIVATE)
        var token = sh.getString("bearer_token", "")
        Log.d(ContentValues.TAG, "onResume: ++++" + token)

        var uId = getSharedPreferences("USER_ID", MODE_PRIVATE)
        var user_id = uId.getString("user_Id", "")!!.toInt()
        Log.d(ContentValues.TAG, "onResponse: +++++++" + user_id)

        var end_date = now

        Log.d(ContentValues.TAG, "onResume: " + end_date)


        val start_date = now.minusMonths(1)


        Log.d(ContentValues.TAG, "onResume: " + start_date)

        if (dairy_start_date1 == null) {
            dairy_start_date = now.minusMonths(1).toString()
        }
        if (dairy_end_date1 == null) {
            dairy_end_date = now.toString()
        }

        if (dairy_start_date1 == null && dairy_end_date1 == null) {
            from_to_dairy.text = "${resources.getString(app.briota.sia.R.string.ReportFrom)} ${
                output1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(app.briota.sia.R.string.To)} ${output!!.substring(0, 10)}"
        } else {
            from_to_dairy.text = "${resources.getString(app.briota.sia.R.string.ReportFrom)} ${
                dairy_start_date1!!.substring(
                    0,
                    10
                )
            } ${resources.getString(app.briota.sia.R.string.To)} ${dairy_end_date1!!.substring(0, 10)}"
        }
        getreportDetailsbyDairy(
            token!!,
            user_id,
            diarydata!!,
            dairy_start_date.toString(),
            dairy_end_date.toString()
        )

    }

    private fun getreportDetailsbyDairy(
        token: String?,
        user_id: Int,
        diarydata: String,
        start_date: String,
        end_date: String
    ) {

        RetrofitClient.getRetrofitInstance.getReportdailydairy(
            "Bearer " + token,
            user_id,
            diarydata,
            start_date,
            end_date
        )
            .enqueue(object : Callback<Any> {

                override fun onFailure(call: Call<Any>, t: Throwable) {


                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {

                    if (response.code() == 200) {


                        Log.d(ContentValues.TAG, "onResponse: +++++++" + response.body())


                        try {

                            var gson = Gson()
                            var json: String = gson.toJson(response.body())


                            Log.d(ContentValues.TAG, "onResponse: " + json)


//                            var getReportModel : ReportModel =
//                                gson.fromJson(json , ReportModel::class.java)


                            var getReportbyDairy: ReportbyDairy =
                                gson.fromJson(json, ReportbyDairy::class.java)

                            Log.d(ContentValues.TAG, "onResponse: " + getReportbyDairy)


                            followed_count.text = getReportbyDairy.followed.toString()
                            unfollowed_count.text = getReportbyDairy.unfollowed.toString()

                            setReportDatabydairy(getReportbyDairy)


                        } catch (t: Throwable) {
                            Log.e(
                                "My App", "" + t

                            )
                        }

//                        Utility.sharedInstance.dismissProgressDialog()
                        //  Utility.sharedInstance.dismissProgressDialog()
                    } else {
                        Utility.sharedInstance.showToastError(
                            this@ReportDeatailDairyActivity,
                            response.body().toString()
                        )


                    }
                }
            })


    }

    private fun setReportDatabydairy(reportbyDairy: ReportbyDairy) {



        zoneDairyList = reportbyDairy.data!!


        if (zoneDairyList.size == 0) {
            reportdetalinodata.visibility = View.VISIBLE
        } else {

            reportdetalinodata.visibility = View.GONE
        }


        recycle_by_dairy.adapter = ZoneDairyAdapter(this, zoneDairyList)

        recycle_by_dairy.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

}





