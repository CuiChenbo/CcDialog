package com.ccb.cdialog

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ccb.cdialog.utils.DisplayHelper

/**
 * 竖直提示弹框
 */
class AlertVerticalDialog {

    private var dialog : BaseDialog? = null
    private val TAG = "CCB"
    private var title : String? = null
    private var msg : String? = null
    private var context: AppCompatActivity? = null

    fun bulid(context: AppCompatActivity) : AlertVerticalDialog{
        this.context = context
        dialog = BaseDialog()
        dialog!!.setLayout(R.layout.dialog_select_v)
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
    private var listMenu: ListView? = null

    fun bulidView(rootView : View){
        txtDialogTitle = rootView.findViewById(R.id.txt_dialog_title)
        txtDialogTip = rootView.findViewById(R.id.txt_dialog_tip)
        boxCustom = rootView.findViewById(R.id.box_custom)
        txtInput = rootView.findViewById(R.id.txt_input)
        splitHorizontal = rootView.findViewById(R.id.split_horizontal)
        listMenu = rootView.findViewById(R.id.list_menu)
        refreshView()
        //列表的高度不大于设置的最大高度
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val maxH = rootView.height * 5 / 8
                val cutterH = listMenu!!.height
                val useH = if (maxH < cutterH) maxH else cutterH
                listMenu!!.layoutParams.height = useH + DisplayHelper.dp2px(context,1)
            }
        })
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
        listMenu!!.adapter = NormalMenuArrayAdapter(context!!, R.layout.item_menu_ios, items)
        listMenu!!.setOnItemClickListener { adapterView, view, i, l ->
            if(itemClick != null)
                if (!itemClick!!.onItemClick(dialog!!,i)) else dialog!!.dismiss()
            else dialog!!.dismiss()
        }

    }

    private var items : List<String> = arrayListOf()
    fun setItems(items : List<String>) : AlertVerticalDialog{
        this.items = items
        return this
    }


    inner class NormalMenuArrayAdapter(context: Context, var resoureId: Int, var objects: List<String>) : ArrayAdapter<Any>(context, resoureId, objects) {

        inner class ViewHolder {
            internal var textView: TextView? = null
        }

        override fun getCount(): Int {
            return objects.size
        }

        override fun getItem(position: Int): String? {
            return objects[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            var viewHolder: ViewHolder? = null
            if (convertView == null) {
                viewHolder = ViewHolder()
                val mInflater = LayoutInflater.from(context)
                convertView = mInflater.inflate(resoureId, null)
                viewHolder.textView = convertView!!.findViewById(R.id.text)
                convertView.tag = viewHolder
            } else {
                viewHolder = convertView.tag as ViewHolder
            }
            val text = objects[position]
            if (null != text) {
                viewHolder.textView!!.text = text

            }

            return convertView
        }
    }


    fun show(){
        if (!dialog!!.isAdded
                && !dialog!!.isVisible
                && !dialog!!.isRemoving)
        dialog!!.show(context!!.supportFragmentManager,TAG)
    }

    fun setCancelable(enable: Boolean): AlertVerticalDialog {
        if (dialog != null) dialog!!.isCancelable = enable
        return this
    }

    fun setTitle(title : String) : AlertVerticalDialog{
        this.title = title
        return this
    }
    fun setMsg(msg : String) : AlertVerticalDialog{
        this.msg = msg
        return this
    }

    companion object {

        fun show(context: AppCompatActivity, items: List<String>,itemClick: OnBottomItemClick?) {
            show(context, "", "",items, itemClick, false)
        }

        var mDialog: AlertVerticalDialog? = null
        fun show(context: AppCompatActivity, title: String, msg: String?, items: List<String>,itemClick: OnBottomItemClick? , cancelable: Boolean) {
            mDialog = AlertVerticalDialog().bulid(context)
            mDialog!!.setOnItemClickListeners(itemClick)
            mDialog!!.setTitle(title)
                    .setMsg(msg!!)
                    .setItems(items!!)
                    .setCancelable(cancelable)
                    .showDialog()
        }

        fun dismiss() {
            if (mDialog != null && mDialog!!.dialog != null) mDialog!!.dialog!!.dismiss()
        }
    }

   private fun showDialog(){
       if (!dialog!!.isAdded
               && !dialog!!.isVisible
               && !dialog!!.isRemoving)
        dialog!!.show(context!!.supportFragmentManager,TAG)
    }

    private fun dismiss(){
        if (dialog!!.isAdded
                && dialog!!.isVisible
                && dialog!!.isRemoving)
            dialog!!.dismiss()
    }
    fun isNull(s : String?) : Boolean{
      return TextUtils.isEmpty(s) || "null".equals(s)
    }

    private var itemClick : OnBottomItemClick? = null

    public fun setOnItemClickListeners(itemClick : OnBottomItemClick?):AlertVerticalDialog{
        this.itemClick = itemClick
        return this
    }

}