package com.developeros.howlongdoyouwork

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
interface AddWorkDialogListener {
        fun onDialogPositiveClick(string: String)
        fun onDialogPositiveClick(int: Int)
        fun onDialogPositiveClick(int: Int,running:Boolean)
        fun onDialogNegativeClick(string: String)
    }
