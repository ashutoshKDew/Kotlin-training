package ui.screens.auth

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LogInUiState(
    var loading: Boolean = false,
    var result: String = ""
)

data class SignUpInUiState(
    val loading: Boolean = false,
    val result: String = ""
)

class AuthViewModel : ViewModel() {

    private val _logInUiState = MutableStateFlow(LogInUiState())
    val logInUiState = _logInUiState.asStateFlow()

    private val _signUpUiState = MutableStateFlow(SignUpInUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    fun logIn(mail: String, password: String) {
        println("XXX AuthViewModel $mail")
        _logInUiState.value = LogInUiState(true)

        viewModelScope.launch {
            delay(3_000)
            _logInUiState.value = LogInUiState(false, "Welcome back")
        }

    }

    fun signUp(mail: String, password: String) {
        _signUpUiState.value = SignUpInUiState(true)

        viewModelScope.launch {
            delay(3_000)
            _signUpUiState.value = SignUpInUiState(false)
        }
    }

    fun resetResult() {
        viewModelScope.launch {
            delay(1_000)
            _logInUiState.value = LogInUiState(false)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}