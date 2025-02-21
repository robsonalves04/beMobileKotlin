package com.example.bemobilekotlin.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bemobilekotlin.models.EmployeeModel
import com.example.bemobilekotlin.models.EmployeeResponse
import com.example.bemobilekotlin.service.IEmployeeService
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStream


class EmployeeViewModel(
    private val employeeService: IEmployeeService,
    private val context: Context
) : ViewModel() {

    private val dados = MutableLiveData<List<EmployeeModel>>()
    val _dados: LiveData<List<EmployeeModel>> = dados

    //Função faz requisição direto na API
    fun fetchEmployees() {
        viewModelScope.launch {
            try {
                val response = employeeService.getEmployees()
                dados.postValue(response)
                response.forEach { employee ->
                    Log.d(
                        "API_RESPONSE",
                        "ID: ${employee.id}, Nome: ${employee.name}, Cargo: ${employee.job}"
                    )
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

    //Função faz requisição direto do arquivo json local (constante na pasta assets)
    fun fetchEmployeesFromJson(context: Context) {
        viewModelScope.launch {
            val list = loadEmployeesFromJson(context)
            dados.postValue(list) // Atualiza o LiveData
        }
    }

    // Faz tratamento do arquivo local json para ser consumido posteriormente
    fun loadEmployeesFromJson(context: Context): List<EmployeeModel> {
        return try {
            val inputStream: InputStream = context.assets.open("database.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val employeesResponse = gson.fromJson(jsonString, EmployeeResponse::class.java)
            employeesResponse.employees.forEach { employee ->
                Log.d(
                    "Json_Local_RESPONSE",
                    "ID: ${employee.id}, Nome: ${employee.name}, Cargo: ${employee.job}"
                )
            }
            employeesResponse.employees // Retorna a lista de funcionários atraves do json local

        } catch (e: Exception) {
            Log.e("JSON_local_ERROR", "Erro ao carregar JSON", e)
            emptyList() // Retorna uma lista vazia em caso de erro
        }
    }
}