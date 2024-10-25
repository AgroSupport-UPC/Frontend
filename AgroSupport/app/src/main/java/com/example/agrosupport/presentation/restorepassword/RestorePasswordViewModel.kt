package com.example.agrosupport.presentation.restorepassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes

class RestorePasswordViewModel(
    private val navController: NavController
) : ViewModel() {

    private val _newPassword = mutableStateOf("")
    val newPassword: State<String> get() = _newPassword

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> get() = _confirmPassword

    private val _isNewPasswordVisible = mutableStateOf(false)
    val isNewPasswordVisible: State<Boolean> get() = _isNewPasswordVisible

    private val _isConfirmPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible: State<Boolean> get() = _isConfirmPasswordVisible

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun toggleNewPasswordVisibility() {
        _isNewPasswordVisible.value = !_isNewPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    fun setNewPassword(newPassword: String) {
        _newPassword.value = newPassword
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun validatePasswords(): Boolean {
        return _newPassword.value.isNotEmpty() && _confirmPassword.value.isNotEmpty() && _newPassword.value == _confirmPassword.value
    }
}