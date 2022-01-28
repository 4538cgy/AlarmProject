package com.example.alarmproject.util.system

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Window
import com.example.alarmproject.R
import java.lang.Exception
import java.lang.ref.WeakReference

object ProgressDialog {
    private var progress: WeakReference<Dialog?>? = null
    private var isRequested = false

    fun show(activity: Activity?) {
        showInternal(
            activity, R.layout.progress_layout_transparent, R.style.Theme_Transparent, ColorDrawable(0), null, false
        )
    }

    fun show(activity: Activity?, listener: DialogInterface.OnKeyListener?, isCancellable: Boolean) {
        showInternal(
            activity, R.layout.progress_layout_transparent, 0, null, listener, isCancellable
        )
    }

    private fun showInternal(
        activity: Activity?,
        layoutResId: Int,
        styleId: Int,
        backgroundDrawable: Drawable?,
        listener: DialogInterface.OnKeyListener?,
        isCancellable: Boolean
    ) {
        if (activity == null || activity.window == null || isRequested) {
            return
        }
        try {
            val mInflater = activity.layoutInflater
            @SuppressLint("InflateParams") val dialogLayout = mInflater.inflate(layoutResId, null)
            val dialog: Dialog
            dialog = if (0 != styleId) {
                Dialog(activity, styleId)
            } else {
                Dialog(activity)
            }
            if (null != backgroundDrawable) {
                dialog.window!!.setBackgroundDrawable(backgroundDrawable)
            }
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogLayout)
            dialog.setCancelable(isCancellable)
            dialog.setOnKeyListener(listener)
            dialog.setCanceledOnTouchOutside(isCancellable)
            dialog.show()
            progress = WeakReference(dialog)
            isRequested = true
        } catch (ignore: Exception) {
            isRequested = false
            Logger.process("ProgressDialog Error while displaying ProgressDialog, but ignored.")
        }
    }

    val isShowing: Boolean
        get() {
            try {
                if (progress != null && progress!!.get() != null && progress!!.get()!!.isShowing) {
                    return true
                }
            } catch (e: Exception) {
                Logger.process("ProgressDialog Fatal error in ProgressDialogHelper#isShowing")
            }
            return false
        }

    fun dismiss() {
        isRequested = false
        try {
            if (progress != null && progress!!.get() != null && progress!!.get()!!.isShowing) {
                progress!!.get()!!.dismiss()
                progress = null
            }
        } catch (e: Exception) {
            Logger.process("ProgressDialog Fatal error in ProgressDialogHelper#dismiss")
        }
    }
}