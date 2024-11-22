package com.example.crm_escuelabaile.controllers

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State


class LogicaMenu : ViewModel() {
    private val _drawerState = mutableStateOf(DrawerValue.Closed)
    val drawerState: State<DrawerValue> = _drawerState

    fun openDrawer() {
        _drawerState.value = DrawerValue.Open
    }

    fun closeDrawer() {
        _drawerState.value = DrawerValue.Closed
    }
}