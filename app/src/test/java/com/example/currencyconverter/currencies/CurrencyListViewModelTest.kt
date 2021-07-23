package com.example.currencyconverter.currencies

import com.example.currencyconverter.database.AppDatabaseDao
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyListViewModelTest {

    @Mock
    private lateinit var mockDatabaseDao : AppDatabaseDao

    @Test
    fun setSearchQuery_setNewSearchQueryStateFlowValue() {
        // given
        val viewModel = CurrencyListViewModel(mockDatabaseDao)
        val searchQuery = "Test query"
        // when
        viewModel.setSearchQuery(searchQuery)
        // then
        assertEquals(viewModel.searchQuery.value, searchQuery)
    }

}