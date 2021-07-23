package com.example.currencyconverter.converter

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.example.currencyconverter.converter.ValueInputValidator

class ValueInputValidatorTest {

    private lateinit var validator : ValueInputValidator

    @Before
    fun createValidator() {
        validator = ValueInputValidator()
    }

    @Test
    fun validate_doubleString_returnTrue() {
        // given
        val inputString = "1.999"
        // when
        val result = validator.validate(inputString)
        // then
        assertEquals(result, true)
    }

    @Test
    fun validate_textString_returnFalse() {
        // given
        val inputString = "texttexttext"
        // when
        val result = validator.validate(inputString)
        // then
        assertEquals(result, false)
    }

    @Test
    fun validate_negativeDoubleString_returnFalse() {
        // given
        val inputString = "-1.999"
        // when
        val result = validator.validate(inputString)
        // then
        assertEquals(result, false)
    }

    @Test
    fun validate_doubleStringWithComma_returnFalse() {
        // given
        val inputString = "-1,999"
        // when
        val result = validator.validate(inputString)
        // then
        assertEquals(result, false)
    }

    @Test
    fun validate_doubleStringWithSpace_returnFalse() {
        // given
        val inputString = "-1.9 99"
        // when
        val result = validator.validate(inputString)
        // then
        assertEquals(result, false)
    }
}