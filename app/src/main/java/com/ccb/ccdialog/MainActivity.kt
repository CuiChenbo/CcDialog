package com.ccb.ccdialog

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ccb.cdialog.*
import com.ccb.cdialog.`interface`.OnDialogButtonClickListener
import kotlinx.android.synthetic.main.activity_main.*

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

        testBtn5.setOnClickListener {BottomDialog().bulid(this)
                .setTitle("挖掘机技术哪家强")
                .show()
        }

    }

    fun toast(obj: Any) {
        Toast.makeText(baseContext, obj.toString(), Toast.LENGTH_SHORT).show()
    }
}
