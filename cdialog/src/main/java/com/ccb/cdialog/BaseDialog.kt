package com.ccb.cdialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

 class BaseDialog : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       var basedialog = super.onCreateDialog(savedInstanceState)
        setDialogWinsow(basedialog)
        return basedialog
    }

     var rootView : View? = null
    private var  layoutId : Int? = null
    private var  animResId : Int? = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutId!!, null)
        if (animResId != 0) dialog.window!!.setWindowAnimations(animResId!!)
        if (onRootView != null) onRootView!!.onView(rootView!!)
        return rootView
    }

     enum class STATE{
         BOTTOM , CUSTOM , NULL
     }
     private var state : STATE = STATE.NULL

     fun setDialogWinsowState(state: STATE) {
         this.state = state
     }
     private fun setDialogWinsow(dialog: Dialog?) {
         if (dialog != null) {
             if (state == STATE.CUSTOM) {
                 dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                 val window = dialog.window
                 window!!.decorView.setPadding(0, 0, 0, 0)
                 val windowManager = activity!!.windowManager
                 val display = windowManager.defaultDisplay
                 val lp = window.attributes
                 lp.width = display.width
                 window.attributes = lp
             }else if (state == STATE.BOTTOM){
                 dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                 val window = dialog.window
                 window!!.decorView.setPadding(0, 0, 0, 0)
                 val windowManager = activity!!.windowManager
                 val display = windowManager.defaultDisplay
                 val lp = window.attributes
                 lp.width = display.width
                 lp.windowAnimations = R.style.bottomMenuAnim
                 window.setGravity(Gravity.BOTTOM)
                 window.setWindowAnimations(R.style.bottomMenuAnim)
                 window.attributes = lp
             }
         }
     }

     fun setLayout(layoutId : Int){
        this.layoutId = layoutId
    }

    fun setAnim(animId : Int){
        this.animResId = animId
    }

     interface onDialogonCreateView{
        fun onView(rootView : View)
     }
      var onRootView : onDialogonCreateView? = null

     fun setOnDialogonCreateViewListener(onRootView : onDialogonCreateView){
          this.onRootView = onRootView
     }



}


