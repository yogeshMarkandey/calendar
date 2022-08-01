package com.example.calendar.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


object ViewHelper {
    private val sdk = android.os.Build.VERSION.SDK_INT
    fun View.addDrawableToView(context: Context, @DrawableRes id : Int){
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackgroundDrawable(ContextCompat.getDrawable( context, id) );
        } else {
            this.setBackground(ContextCompat.getDrawable( context, id));
        }
    }

    fun getColorsFromResource(context: Context, @ColorRes id: Int) : Int {
       return ContextCompat.getColor(context, id)
    }
    fun  getRadius(context: Context, radius: Float) =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.resources.displayMetrics)

    fun checkConnection(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connMgr.activeNetworkInfo
        if (activeNetworkInfo != null) { // connected to the internet
            // connected to the mobile provider's data plan
            return if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                true
            } else activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
        }
        return false
    }
}