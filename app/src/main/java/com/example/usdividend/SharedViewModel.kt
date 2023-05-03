package com.example.usdividend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var myVariable by mutableStateOf(false)
    var myCompany by mutableStateOf("")

    var email = ""
    var nickname = ""
}
