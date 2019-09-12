package com.ccb.cdialog.`interface`

import android.view.View
import com.ccb.cdialog.BaseDialog

interface OnDialogButtonClickListener{
    fun onClick(dialog: BaseDialog , v : View)
}