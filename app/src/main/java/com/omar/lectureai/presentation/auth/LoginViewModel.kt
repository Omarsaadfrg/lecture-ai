package com.omar.lectureai.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.lectureai.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {

            android.util.Log.d("LOGIN_DEBUG", "CLICKED 🔥")

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                isLoginSuccess = false
            )

            try {
                val result = repository.login(
                    _uiState.value.email,
                    _uiState.value.password
                )

                result.onSuccess {
                    android.util.Log.d("LOGIN_DEBUG", "SUCCESS ✅🔥")

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                }

                result.onFailure {
                    android.util.Log.e("LOGIN_DEBUG", "FAILED ❌ ${it.message}")

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                }

            } catch (e: Exception) {
                android.util.Log.e("LOGIN_DEBUG", "CRASH 💣 ${e.message}")

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun register() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                isRegisterSuccess = false
            )

            val result = repository.register(
                _uiState.value.email,
                _uiState.value.password
            )

            result.onSuccess {
                android.util.Log.d("LOGIN_DEBUG", "REGISTER SUCCESS ✅")

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRegisterSuccess = true
                )
            }

            result.onFailure {
                android.util.Log.e("LOGIN_DEBUG", "REGISTER FAILED ❌ ${it.message}")

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = it.message
                )
            }
        }
    }
}