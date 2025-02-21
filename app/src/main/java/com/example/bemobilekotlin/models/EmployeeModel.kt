package com.example.bemobilekotlin.models

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    val employees: List<EmployeeModel>
)
data class EmployeeModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("job") val job: String,
    @SerializedName("admission_date") val admission_date: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("image") val image: String
)