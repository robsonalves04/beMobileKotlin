package com.example.bemobilekotlin.service


import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.bemobilekotlin.viewModels.EmployeeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

class Starting : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //==Inicialização pelo Koin
        startKoin {
            androidLogger()
            androidContext(this@Starting)

            modules(module {
                //== Injeção de dependencia do Service
                single <IEmployeeService> { EmployeeService() }

                //==Injeção do ViewModel
                viewModel { EmployeeViewModel(get(), get()) }
            })
        }
        //== Rodapé padrão do celular
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
    //== Variavel que faz com que a instacia inicie depois
    companion object {
        lateinit var instance: Starting
            private set
    }
}