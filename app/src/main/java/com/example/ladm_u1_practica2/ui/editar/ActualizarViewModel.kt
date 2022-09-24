package com.example.ladm_u1_practica2.ui.editar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActualizarViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is actualizar"
    }
    val text: LiveData<String> = _text
}