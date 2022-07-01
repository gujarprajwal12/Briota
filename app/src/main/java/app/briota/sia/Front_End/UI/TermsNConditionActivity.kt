package app.briota.sia.Front_End.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import app.briota.sia.R

class TermsNConditionActivity : AppCompatActivity() {


    lateinit var webView: WebView
    lateinit var back: TextView

    lateinit var txtTitle: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)


        initView()
        onClick()


    }

    fun initView() {
        webView = findViewById(R.id.webView)
        back = findViewById(R.id.Back)

        txtTitle = findViewById(R.id.txtTitle)

        txtTitle.text = resources.getString(R.string.terms_conditions)

    }

    fun onClick() {

        back.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onResume() {
        super.onResume()

        try {

            webView.webViewClient = WebViewClient()

            var sh = getSharedPreferences("Briota", MODE_PRIVATE)
            var lang = sh.getString("Select_Lang", "")

            if (lang.equals("da")) {
                webView.loadUrl("http://3.7.36.252:8000/api/profiles/terms_and_conditions_danish")
            } else {
                webView.loadUrl("http://3.7.36.252:8000/api/profiles/terms_and_conditions")
            }

            webView.settings.javaScriptEnabled = true

            webView.settings.setSupportZoom(true)


            val handler = Handler()
            handler.postDelayed({

                //   Utility.sharedInstance.dismissProgressDialog()
            }, 15000)


        } catch (e: Exception) {

        }


    }
}