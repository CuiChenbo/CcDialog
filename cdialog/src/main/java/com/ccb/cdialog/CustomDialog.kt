package com.ccb.cdialog

import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class CustomDialog {

    private var dialog: BaseDialog? = null
    private val TAG = "CCB"
    private var context: AppCompatActivity? = null

    fun bulid(context: AppCompatActivity , layoutRes : Int): CustomDialog {
        this.context = context
        dialog = BaseDialog()
        dialog!!.setLayout(layoutRes)
        dialog!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseDialog)
        dialog!!.setAnim(R.style.iOSDialogAnimStyle)
        dialog!!.isCancelable = cancelable
        setCentent()
        return this
    }

    private fun setCentent() {
        dialog!!.setOnDialogonCreateViewListener(object : BaseDialog.onDialogonCreateView {
            override fun onView(rootView: View) {
                bulidView(rootView)
            }
        })
    }

    fun bulidView(rootView: View) {
       if (onBindView != null) onBindView!!.onBind(dialog,rootView)
        refreshView()
    }

    private fun refreshView() {

    }


    fun show() {
        if (!dialog!!.isAdded
                && !dialog!!.isVisible
                && !dialog!!.isRemoving)
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    private var cancelable: Boolean = false
    fun setCancelable(enable: Boolean): CustomDialog {
        cancelable = enable
        return this
    }

    companion object {
        fun show(context: AppCompatActivity, layoutRes: Int) {
            show(context,  layoutRes, null , true)
        }

        var customDialog: CustomDialog? = null
        fun show(context: AppCompatActivity, layoutRes: Int, onBindView: OnBindView? , cancelable: Boolean) {
            customDialog = CustomDialog().bulid(context,layoutRes)
            customDialog!!
                    .setOnBindView(onBindView)
                    .setCancelable(cancelable)
                    .showDialog()
        }

        fun dismiss() {
            if (customDialog != null && customDialog!!.dialog != null) customDialog!!.dialog!!.dismiss()
        }
    }

    private fun showDialog() {
        if (!dialog!!.isAdded
                && !dialog!!.isVisible
                && !dialog!!.isRemoving)
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    fun isNull(s: String?): Boolean {
        return TextUtils.isEmpty(s) || "null" == s
    }

    private var onBindView : OnBindView? = null
     fun setOnBindView(onBindView : OnBindView?) : CustomDialog{
        this.onBindView = onBindView
         return this
    }
}