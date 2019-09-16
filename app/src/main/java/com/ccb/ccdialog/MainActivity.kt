package com.ccb.ccdialog

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.ccb.cdialog.*
import com.ccb.cdialog.`interface`.OnDialogButtonClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_lufei.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testBtn1.setOnClickListener {AlertDialog.show(this,"通知","APP即将崩溃","MDZZ","我知道了")}
        testBtn2.setOnClickListener {AlertDialog().bulid(this).setTitle("崔崔崔")
                .setMsg("你好")
                .setBtnR("真帅",object: OnDialogButtonClickListener {
                    override fun onClick(dialog: BaseDialog, v: View) {
                       toast("帅")
                        dialog.dismiss()
                    }
                })
                .setCancelable(false)
                .show()}

        testBtn3.setOnClickListener {LoadDialog.show(this)
        testBtn3.postDelayed(Runnable {LoadDialog.dismiss()  },3000L )
        }
        testBtn4.setOnClickListener {LoadDialog().bulid(this)
                .autoDismiss(3333)
                .setMsg("嘿嘿")
                .setMsgColor(R.color.dark)
                .setProgressColor(R.color.dark)
                .setBckRes(R.drawable.rect_selectdialog_ios_bkg_light)
                .show()
        }

        testBtn5.setOnClickListener {
            BottomDialog.show(this,"挖掘机",Arrays.asList("中国","山东","找","蓝翔"),object: OnBottomItemClick {
                override fun onItemClick(dialog: BaseDialog, position: Int) {
                    toast(position)
                    dialog.dismiss()
                }
            },false)
        }

        testBtn6.setOnClickListener {
         CustomDialog.show(this,R.layout.layout_lufei,object: OnBindView {
             override fun onBind(dialog: BaseDialog?, view: View) {
                 val imageview = view.findViewById<AppCompatImageView>(R.id.iv)
                 val textview = view.findViewById<AppCompatTextView>(R.id.tv)
                 imageview.setOnClickListener{toast("快救我出来") }
                 textview.setOnClickListener{dialog!!.dismiss() }
             }
         }, false)
        }

    }

    fun toast(obj: Any) {
        Toast.makeText(baseContext, obj.toString(), Toast.LENGTH_SHORT).show()
    }
}
