package com.gameshow.button.presentation.utils

import android.text.InputFilter

object EnterCodeValidator {

    val enterCodeValidator = InputFilter { source, start, end, dest, dstart, dend ->
        val maxLength = 5
        val inputLength = dest.length + source.length - (dend - dstart)
        if (inputLength > maxLength) {
            return@InputFilter ""
        }
        for (i in start until end) {
            if (!Character.isUpperCase(source[i]) && !Character.isDigit(source[i])) {
                return@InputFilter ""
            }
        }
        null
    }
}