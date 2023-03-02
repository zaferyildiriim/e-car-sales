package com.example.myapplication2

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(val mActivity :Activity) {

    private lateinit var isDialog:AlertDialog
    fun startLoading(){
        val infalter=mActivity.layoutInflater
        val dialogView=infalter.inflate(R.layout.loading,null)
        val builder=AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog=builder.create()
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }
}