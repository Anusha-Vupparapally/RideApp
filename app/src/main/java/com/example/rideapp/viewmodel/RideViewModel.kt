package com.example.rideapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RideViewModel : ViewModel() {

    var pickup = mutableStateOf("")
    var drop = mutableStateOf("")

}