package com.example.bemobilekotlin.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bemobilekotlin.models.EmployeeModel
import com.example.bemobilekotlin.service.IEmployeeService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EmployeeViewModel (private val employeeService: IEmployeeService) : ViewModel() {

    private val dados = MutableLiveData<List<EmployeeModel>>()
    val _dados: LiveData<List<EmployeeModel>> = dados

    fun fetchEmployees() {
        viewModelScope.launch {
            try {
                val response = employeeService.getEmployees()
                dados.postValue(response)
                response.forEach { employee ->
                    Log.d("API_RESPONSE", "ID: ${employee.id}, Nome: ${employee.name}, Cargo: ${employee.job}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao buscar funcionários", e)
            }
        }
    }
    // Busca funcionários por nome
    fun searchByName(query: String): List<EmployeeModel> {
        return dados.value?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: emptyList()
    }
}