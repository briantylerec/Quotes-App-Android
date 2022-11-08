package com.monksoft.examplemvvm.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monksoft.examplemvvm.data.model.QuoteModel
import com.monksoft.examplemvvm.data.model.QuoteProvider
import com.monksoft.examplemvvm.domain.GetQuotesUseCase
import com.monksoft.examplemvvm.domain.GetRandomQuoteUseCase
import kotlinx.coroutines.launch

//live data permite a la activity suscribirse a un modelo de datos y se llame automaticamente cuando se realiza cambios en el modelo
class QuoteViewModel : ViewModel() {

    //encapsular el modelo en live data, metido en mutable porque el valor de QuoteModel va a ir cambiando
    val quoteModel = MutableLiveData<QuoteModel>()
    val isLoading = MutableLiveData<Boolean>()

    val getQuotesUseCase = GetQuotesUseCase()
    val getRandomQuoteUseCase = GetRandomQuoteUseCase()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuotesUseCase()

            if(!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }
    }

    fun randomQuote(){
        isLoading.postValue(true)
        val quote = getRandomQuoteUseCase()
        if(quote!=null) quoteModel.postValue(quote)
        isLoading.postValue(false)
    }


}