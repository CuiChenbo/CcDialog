package com.ccb.cdialog

import android.view.View

interface OnDialogButtonClickListener{
    fun onClick(dialog: BaseDialog , v : View) : Boolean
}