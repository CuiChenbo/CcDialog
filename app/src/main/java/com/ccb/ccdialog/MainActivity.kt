package com.ccb.ccdialog

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.ccb.cdialog.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testBtn1.setOnClickListener { AlertDialog.show(this,"通知","APP即将崩溃","MDZZ","我知道了")}
        testBtn2.setOnClickListener {AlertDialog().bulid(this).setTitle("崔崔崔")
                .setMsg("你好")
                .setBtnR("真帅",object: OnDialogButtonClickListener {
                    override fun onClick(dialog: BaseDialog, v: View): Boolean {
                        toast("帅")
                        return true
                    }
                })
                .setCancelable(true)
                .show()}

        testBtn7.setOnClickListener {
//            AlertVerticalDialog().bulid(this).setTitle("提示")
//                .setMsg("选择一个")
//                .setItems(Arrays.asList("你看","这个碗","有大又圆"))
//                .setCancelable(true)
//                .setOnItemClickListeners(object: OnBottomItemClick {
//                    override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
//                        toast(position)
//                        return true
//                    }
//                })
//                .show()
            AlertVerticalDialog.show(this,"提示","选择一个",Arrays.asList("你看","这个碗","有大又圆"),object: OnBottomItemClick {
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    toast(position)
                    return true
                }
            }, false)
        }

        testBtn3.setOnClickListener {
            LoadDialog.show(this)
        testBtn3.postDelayed(Runnable {LoadDialog.dismiss()  },2000L )
        }
        testBtn4.setOnClickListener {LoadDialog().bulid(this)
                .autoDismiss(1333)
                .setMsg("嘿嘿")
                .setMsgColor(R.color.dark)
                .setProgressColor(R.color.dark)
                .setBckRes(R.drawable.rect_selectdialog_ios_bkg_light)
                .show()
        }

        testBtn5.setOnClickListener {
            BottomDialog.show(this,"选择图片",Arrays.asList("拍照","相册","系统图片","查看大图"),object: OnBottomItemClick {
                override fun onItemClick(dialog: BaseDialog, position: Int): Boolean {
                    toast(position)
                    return true
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
