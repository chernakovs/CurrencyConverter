package com.example.currencyconverter.converter

import java.lang.NumberFormatException

class ValueInputValidator {

    fun validate(value : String) : Boolean {
        return if (value.contains('-')) {
            false
        } else {
            try {
                value.toDouble()
                true
            } catch (e : NumberFormatException) {
                false
            }
        }
    }
}