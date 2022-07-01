package app.briota.sia.Front_End.view.widget.edittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText


@SuppressLint("AppCompatCustomView")
class CustomEditText : EditText {


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    fun init() {
        if (!isInEditMode) {
            //Typeface tf = Typeface.createFromAsset(getContextDash().getAssets(), "OstrichSansRounded-Medium.otf");
            //            Typeface tf = Typeface.createFromAsset(getContextDash().getAssets(), "Roboto-Thin.ttf");
            val tf = Typeface.createFromAsset(context.assets, "DMSans-Regular.ttf")
            // Typeface tf = Typeface.createFromAsset(getContextDash().getAssets(), "sofiapro-light.otf");
            typeface = tf
        }

    }
}
