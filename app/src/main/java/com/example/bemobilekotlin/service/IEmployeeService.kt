package com.example.bemobilekotlin.service


import com.example.bemobilekotlin.models.EmployeeModel
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response

interface IEmployeeService {
    //Implementação do GET
    @GET("employees")
    suspend fun getEmployees(): List<EmployeeModel>
}