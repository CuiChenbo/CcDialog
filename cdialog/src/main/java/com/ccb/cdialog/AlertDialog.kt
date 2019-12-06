package com.ccb.cdialog

import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

/**
 * 底部按钮优先设置顺序 右 > 左 > 中
 */
class AlertDialog {

    private var dialog : BaseDialog? = null
    private val TAG = "CCB"
    private var title : String? = null
    private var msg : String? = null
    private var btnLift : String? = null
    private var btnCenter : String? = null
    private var btnRight : String? = null
    private var context: AppCompatActivity? = null

    fun bulid(context: AppCompatActivity) : AlertDialog{
        this.context = context
        dialog = BaseDialog()
        dialog!!.setLayout(R.layout.dialog_select_ios)
        dialog!!.setStyle(DialogFragment.STYLE_NORMAL,R.style.BaseDialog)
        dialog!!.setAnim(R.style.iOSDialogAnimStyle)
        setCentent()
        return this
    }

    private fun setCentent() {
        dialog!!.setOnDialogonCreateViewListener(object: BaseDialog.onDialogonCreateView {
            override fun onView(rootView: View) {
                bulidView(rootView)
            }
        })
    }

    var txtDialogTitle: TextView? = null
     var txtDialogTip: TextView? = null
     var boxCustom: RelativeLayout? = null
     var txtInput: EditText? = null
     var splitHorizontal: ImageView? = null
     var boxButton: LinearLayout? = null
     var btnSelectNegative: TextView? = null
     var splitVertical1: ImageView? = null
     var btnSelectOther: TextView? = null
     var splitVertical2: ImageView? = null
     var btnSelectPositive: TextView? = null

    fun bulidView(rootView : View){
        txtDialogTitle = rootView.findViewById(R.id.txt_dialog_title)
        txtDialogTip = rootView.findViewById(R.id.txt_dialog_tip)
        boxCustom = rootView.findViewById(R.id.box_custom)
        txtInput = rootView.findViewById(R.id.txt_input)
        splitHorizontal = rootView.findViewById(R.id.split_horizontal)
        boxButton = rootView.findViewById(R.id.box_button)
        btnSelectNegative = rootView.findViewById(R.id.btn_selectNegative)
        splitVertical1 = rootView.findViewById(R.id.split_vertical1)
        btnSelectOther = rootView.findViewById(R.id.btn_selectOther)
        splitVertical2 = rootView.findViewById(R.id.split_vertical2)
        btnSelectPositive = rootView.findViewById(R.id.btn_selectPositive)

        refreshView()
    }

    private fun refreshView() {
        if (isNull(title)){
            txtDialogTitle!!.visibility = View.GONE
        }else{
            txtDialogTitle!!.visibility = View.VISIBLE
            txtDialogTitle!!.text = title
        }
        if (isNull(msg)){
            txtDialogTip!!.visibility = View.GONE
        }else{
            txtDialogTip!!.visibility = View.VISIBLE
            txtDialogTip!!.text = msg
        }
        if (isNull(btnLift)){
            splitVertical2!!.visibility = View.GONE
            btnSelectNegative!!.visibility = View.GONE
        }else{
            splitVertical2!!.visibility = View.VISIBLE
            btnSelectNegative!!.visibility = View.VISIBLE
            btnSelectNegative!!.text = btnLift
        }
        if (isNull(btnCenter)){
            splitVertical1!!.visibility = View.GONE
            btnSelectOther!!.visibility = View.GONE
        }else{
            splitVertical1!!.visibility = View.VISIBLE
            btnSelectOther!!.visibility = View.VISIBLE
            btnSelectOther!!.text = btnCenter
        }
        if (isNull(btnRight)){
            btnSelectPositive!!.visibility = View.GONE
        }else{
            btnSelectPositive!!.visibility = View.VISIBLE
            btnSelectPositive!!.text = btnRight
        }

        btnSelectNegative!!.setOnClickListener {
            if (onButtonClick!=null)
                if (onButtonClick!!.onClickLift(dialog!!,btnSelectNegative!!)) else dialog!!.dismiss()
            if (onCancelButtonClickListener != null)
                if (!onCancelButtonClickListener!!.onClick(dialog!!,btnSelectNegative!!)) else dialog!!.dismiss()
            else dialog!!.dismiss()
        }
        btnSelectOther!!.setOnClickListener {
            if (onButtonClick!=null)
                if (onButtonClick!!.onClickCenter(dialog!!,btnSelectOther!!)) else dialog!!.dismiss()
            if (onOtherButtonClickListener != null)
                if (!onOtherButtonClickListener!!.onClick(dialog!!,btnSelectNegative!!)) else dialog!!.dismiss()
            else dialog!!.dismiss()
        }
        btnSelectPositive!!.setOnClickListener {
            if (onButtonClick!=null)
                if (onButtonClick!!.onClickRight(dialog!!,btnSelectPositive!!)) else dialog!!.dismiss()
            if (onOkButtonClickListener != null)
                if (!onOkButtonClickListener!!.onClick(dialog!!,btnSelectNegative!!)) else dialog!!.dismiss()
            else dialog!!.dismiss()
        }

    }


    fun show(){
        dialog!!.show(context!!.supportFragmentManager,TAG)
    }

    fun setCancelable(enable: Boolean): AlertDialog {
        if (dialog != null) dialog!!.isCancelable = enable
        return this
    }

    fun setTitle(title : String) : AlertDialog{
        this.title = title
        return this
    }
    fun setMsg(msg : String) : AlertDialog{
        this.msg = msg
        return this
    }
    fun setBtnL(btnLift: String?, onCancelButtonClickListener: OnDialogButtonClickListener?) : AlertDialog{
        this.btnLift = btnLift
        this.onCancelButtonClickListener = onCancelButtonClickListener
        return this
    }
    fun setBtnC(btnCenter: String?, onOtherButtonClickListener: OnDialogButtonClickListener?) : AlertDialog{
        this.btnCenter = btnCenter
        this.onOtherButtonClickListener = onOtherButtonClickListener
        return this
    }
    fun setBtnR(btnRight: String?, onOkButtonClickListener: OnDialogButtonClickListener?) : AlertDialog{
        this.btnRight = btnRight
        this.onOkButtonClickListener = onOkButtonClickListener
        return this
    }



    companion object {

        fun show(context: AppCompatActivity,title : String , msg : String , btnLift: String){
            show(context, title, msg,null,null, btnLift)
        }

        fun show(context: AppCompatActivity, title: String, msg: String, btnLift: String, btnRight: String) {
            show(context, title, msg, btnLift, null, btnRight)
        }

        fun show(context: AppCompatActivity, title: String, msg: String, btnLift: String?, btnCenter: String?, btnRight: String?) {
            AlertDialog().bulid(context)
                    .setTitle(title)
                    .setMsg(msg)
                    .setBtnL(btnLift, null)
                    .setBtnC(btnCenter, null)
                    .setBtnR(btnRight,null)
                    .showDialog()


        }
    }

   private fun showDialog(){
        dialog!!.show(context!!.supportFragmentManager,TAG)
    }

    fun isNull(s : String?) : Boolean{
      return TextUtils.isEmpty(s) || "null".equals(s)
    }

     var onOkButtonClickListener: OnDialogButtonClickListener? = null
     var onCancelButtonClickListener: OnDialogButtonClickListener? = null
     var onOtherButtonClickListener: OnDialogButtonClickListener? = null

    var onButtonClick : OnButtonClick? = null
    fun setOnButtonClickListener(onButtonClick : OnButtonClick) : AlertDialog{
        this.onButtonClick = onButtonClick
        return this
    }
}