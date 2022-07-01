package app.briota.sia.Front_End.UI

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toBitmap
import app.briota.sia.R
import kotlinx.android.synthetic.main.activity_action_plan_template.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.ByteArrayOutputStream

class ActionPlanTemplateActivity : AppCompatActivity() {

    var sendimg: String? = null

    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_plan_template)

        initView()

        onClick()
    }

    fun initView() {

        txtTitle.text = resources.getString(R.string.ActionPlanTemplate)
        txtTitle1.visibility = View.VISIBLE

    }

    fun onClick() {

        back_tol.setOnClickListener {
            onBackPressed()
        }


        img_Asthama.setOnClickListener {

            chooseImageTemplate.visibility = View.GONE
            img_template.visibility = View.VISIBLE
            img_template1.visibility = View.GONE
            txtTitle.text = resources.getString(R.string.Asthmatemplet)
            txtTitle1.text = resources.getString(R.string.share)
            sendimg = "Asthma"
        }
        btn_Asthama.setOnClickListener {

            chooseImageTemplate.visibility = View.GONE
            img_template.visibility = View.VISIBLE
            img_template1.visibility = View.GONE
            txtTitle.text = resources.getString(R.string.Asthmatemplet)
            txtTitle1.text = resources.getString(R.string.share)
            sendimg = "Asthma"
        }


        img_COPD.setOnClickListener {
            chooseImageTemplate.visibility = View.GONE
            img_template.visibility = View.GONE
            img_template1.visibility = View.VISIBLE
            txtTitle.text = resources.getString(R.string.COPDtemplet)
            txtTitle1.text = resources.getString(R.string.share)
            sendimg = "Copd"
        }
        btn_COPD.setOnClickListener {
            chooseImageTemplate.visibility = View.GONE
            img_template.visibility = View.GONE
            img_template1.visibility = View.VISIBLE
            txtTitle.text = resources.getString(R.string.COPDtemplet)
            txtTitle1.text = resources.getString(R.string.share)
            sendimg = "Copd"
        }

        txtTitle1.setOnClickListener {
            showShareIntent()
        }


    }

    private fun showShareIntent() {


        val intent = Intent(Intent.ACTION_SEND).setType("image/*")

        // Step 2: Get Bitmap from your imageView
        if (sendimg == "Asthma") {

            bitmap = img_template.drawable.toBitmap()
        } else {

            bitmap = img_template1.drawable.toBitmap() // your imageView here.

        }


        // Step 3: Compress image
        val bytes = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        // Step 4: Save image & get path of it
        val path = MediaStore.Images.Media.insertImage(
            applicationContext.contentResolver,
            bitmap,
            "tempimage",
            null
        )

        // Step 5: Get URI of saved image
        val uri = Uri.parse(path)

        // Step 6: Put Uri as extra to share intent
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        // Step 7: Start/Launch Share intent
//    startActivity(intent)

        startActivity(Intent.createChooser(intent, "Share Via"))
    }
}