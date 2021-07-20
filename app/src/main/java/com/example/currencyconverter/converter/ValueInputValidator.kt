package com.example.currencyconverter.converter

class ValueInputValidator {

    fun validate(value : String) : Boolean {
        return !(
                value.contains('-') ||
                value.contains(',') ||
                value.contains(" ")
                )
    }
}