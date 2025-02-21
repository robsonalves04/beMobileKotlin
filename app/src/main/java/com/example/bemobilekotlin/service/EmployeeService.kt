package com.example.bemobilekotlin.service

import com.example.bemobilekotlin.models.EmployeeModel
import com.example.bemobilekotlin.service.EmployeeService.RetrofitClient.apiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class EmployeeService : IEmployeeService {
    //Servico que faz a requisição na API
    object RetrofitClient {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // Para emulador Android
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService: IEmployeeService by lazy {
            retrofit.create(IEmployeeService::class.java)
        }
    }

    override suspend fun getEmployees(): List<EmployeeModel> {
        return apiService.getEmployees()
    }
}