package com.ccb.cdialog

import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ccb.cdialog.`interface`.OnDialogButtonClickListener
import com.ccb.cdialog.utils.LoadingView
import java.util.*
import kotlin.concurrent.timerTask

class LoadDialog {

    private var dialog: BaseDialog? = null
    private val TAG = "CCB"
    private var msg: String? = null
    private var backRes: Int = R.drawable.rect_selectdialog_ios_bkg_dark
    private var progressColor: Int = R.color.white
    private var msgColor: Int = R.color.white
    private var context: AppCompatActivity? = null

    fun bulid(context: AppCompatActivity): LoadDialog {
        this.context = context
        dialog = BaseDialog()
        dialog!!.setLayout(R.layout.dialog_load)
        dialog!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseDialog)
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

    private var tvInfo: TextView? = null
    private var layoutBck: RelativeLayout? = null
    private var progress: LoadingView? = null

    fun bulidView(rootView: View) {
        tvInfo = rootView.findViewById(R.id.txt_info)
        layoutBck = rootView.findViewById(R.id.box_body)
        progress = rootView.findViewById(R.id.progress)
        refreshView()
    }

    private fun refreshView() {
        if (isNull(msg)) {
            tvInfo!!.visibility = View.GONE
        } else {
            tvInfo!!.visibility = View.VISIBLE
            tvInfo!!.text = msg
        }
        layoutBck!!.setBackgroundResource(backRes)
        progress!!.setColor(context!!.resources.getColor(progressColor))
        tvInfo!!.setTextColor(context!!.resources.getColor(msgColor))
    }


    fun show() {
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    private var cancelable: Boolean = false
    fun setCancelable(enable: Boolean): LoadDialog {
        cancelable = enable
        return this
    }

    fun setMsg(msg: String?): LoadDialog {
        this.msg = msg
        return this
    }


    fun setBckRes(backRes: Int): LoadDialog {
        this.backRes = backRes
        return this
    }

    fun setMsgColor(msgColor: Int): LoadDialog {
        this.msgColor = msgColor
        return this
    }

    fun setProgressColor(progressColor: Int): LoadDialog {
        this.progressColor = progressColor
        return this
    }

    companion object {

        fun show(context: AppCompatActivity) {
            show(context, null, null, false)
        }

        fun show(context: AppCompatActivity, msg: String) {
            show(context, msg, null, false)
        }

        var load: LoadDialog? = null
        fun show(context: AppCompatActivity, msg: String?, back: Int?, cancelable: Boolean) {
            load = LoadDialog().bulid(context)
            load!!.setMsg(msg)
                    .setCancelable(cancelable)
                    .showDialog()
        }

        fun dismiss() {
            if (load != null && load!!.dialog != null) load!!.dialog!!.dismiss()
        }
    }

    private var cancelTimer: Timer? = null
    fun autoDismiss(time: Long): LoadDialog {
        if (cancelTimer == null)
            cancelTimer = Timer()
        cancelTimer!!.schedule(timerTask { dialog!!.dismiss() }, time)
        return this
    }

    private fun showDialog() {
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    fun isNull(s: String?): Boolean {
        return TextUtils.isEmpty(s) || "null" == s
    }
}