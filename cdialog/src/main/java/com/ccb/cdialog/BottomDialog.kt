package com.ccb.cdialog

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ccb.cdialog.utils.LoadingView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class BottomDialog {

    private var dialog: BaseDialog? = null
    private val TAG = "CCB"
    private var context: AppCompatActivity? = null

    fun bulid(context: AppCompatActivity): BottomDialog {
        this.context = context
        dialog = BaseDialog()
        dialog!!.setLayout(R.layout.dialog_bottom_menu)
        dialog!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
        dialog!!.setDialogWinsowState(BaseDialog.STATE.BOTTOM)
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

    private var boxBody: LinearLayout? = null
    private var boxList: RelativeLayout? = null
    private var txtTitle: TextView? = null
    private var boxCustom: RelativeLayout? = null
    private var titleSplitLine: ImageView? = null
    private var listMenu: ListView? = null
    private var boxCancel: ViewGroup? = null
    private var btnCancel: TextView? = null

    fun bulidView(rootView: View) {
        if (boxCustom != null) boxCustom!!.removeAllViews()
        boxBody = rootView.findViewById(R.id.box_body)
        boxList = rootView.findViewById(R.id.box_list)
        txtTitle = rootView.findViewById(R.id.txt_title)
        boxCustom = rootView.findViewById(R.id.box_custom)
        titleSplitLine = rootView.findViewById(R.id.title_split_line)
        listMenu = rootView.findViewById(R.id.list_menu)
        boxCancel = rootView.findViewById(R.id.box_cancel)
        btnCancel = rootView.findViewById(R.id.btn_cancel)
        refreshView()
    }

    var title : String ? = null
    private fun refreshView() {
        listMenu!!.adapter = NormalMenuArrayAdapter(context!!, R.layout.item_bottom_menu_ios, Arrays.asList("1","2","3","4"))
        boxList!!.setBackgroundResource(R.drawable.rect_selectdialog_ios_bkg_light)
        boxCancel!!.setBackgroundResource(R.drawable.rect_selectdialog_ios_bkg_light)
        btnCancel!!.text = "取消"
        if (isNull(title)) {
            txtTitle!!.visibility = View.GONE
            titleSplitLine!!.visibility = View.GONE
        } else{
            txtTitle!!.visibility = View.VISIBLE
            titleSplitLine!!.visibility = View.VISIBLE
            txtTitle!!.text = title
        }
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

    fun setTitle(t:String?) : BottomDialog{
        this.title = t
        return this;
    }

    fun show() {
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    private var cancelable: Boolean = true
    fun setCancelable(enable: Boolean): BottomDialog {
        cancelable = enable
        return this
    }

    companion object {

        fun show(context: AppCompatActivity) {
            show(context, null, null, false)
        }

        fun show(context: AppCompatActivity, msg: String) {
            show(context, msg, null, false)
        }

        var load: BottomDialog? = null
        fun show(context: AppCompatActivity, msg: String?, back: Int?, cancelable: Boolean) {
            load = BottomDialog().bulid(context)
            load!!
                    .setCancelable(cancelable)
                    .showDialog()
        }

        fun dismiss() {
            if (load != null && load!!.dialog != null) load!!.dialog!!.dismiss()
        }
    }

    private fun showDialog() {
        dialog!!.show(context!!.supportFragmentManager, TAG)
    }

    private fun isNull(s: String?): Boolean {
        return TextUtils.isEmpty(s) || "null" == s
    }
}