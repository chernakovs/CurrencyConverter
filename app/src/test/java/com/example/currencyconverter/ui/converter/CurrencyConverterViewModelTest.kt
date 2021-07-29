package com.example.currencyconverter.ui.converter

import com.example.currencyconverter.data.database.AppDatabaseDao
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterViewModelTest {

    @Mock
    private lateinit var mockDatabaseDao : AppDatabaseDao


    @Test
    fun setValue_setNewValueStringStateFlowValue() {
        // given
        val currencyAcronym = "test"
        val viewModel = CurrencyConverterViewModel(mockDatabaseDao, currencyAcronym)
        val value = "1.9999"
        // when
        viewModel.setValue(value)
        // then
        assertEquals(viewModel.valueString.value, value)
    }

    @Test
    fun setSearchQuery_setNewSearchQueryStateFlowValue() {
        // given
        val currencyAcronym = "test"
        val viewModel = CurrencyConverterViewModel(mockDatabaseDao, currencyAcronym)
        val searchQuery = "Test query"
        // when
        viewModel.setSearchQuery(searchQuery)
        // then
        assertEquals(viewModel.searchQuery.value, searchQuery)
    }

    @Test
    fun setValue_setNewNegativeValueStringStateFlowValue_inputErrorTrue() {
        // given
        val currencyAcronym = "test"
        val viewModel = CurrencyConverterViewModel(mockDatabaseDao, currencyAcronym)
        val value = "-1.9999"
        // when
        viewModel.setValue(value)
        // then
        assertEquals(viewModel.valueString.value, value)
        assertEquals(viewModel.valueInputError.value, true)
    }


}