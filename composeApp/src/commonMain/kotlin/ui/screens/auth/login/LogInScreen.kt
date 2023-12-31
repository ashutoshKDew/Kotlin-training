package ui.screens.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.screens.auth.AuthViewModel
import ui.screens.auth.LogInUiState
import ui.screens.auth.signup.SignUpScreen
import ui.screens.home.HomeScreen

class LogInScreen : Screen {

    @Composable
    override fun Content() {

        val authViewModel = getViewModel(LogInScreen().key, viewModelFactory { AuthViewModel() })
        val uiState = authViewModel.logInUiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LogInScreenContent(
            uiState = uiState.value,
            onLogInClick = { mail, pass ->
                println("XXX onLogInClick $mail")
                authViewModel.logIn(mail, pass)
            },
            onSignUpClick = { navigator.push(SignUpScreen()) },
            resetResult = {
                authViewModel.resetResult()
            }
        )
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun LogInScreenContent(
        uiState: LogInUiState,
        onLogInClick: (String, String) -> Unit,
        onSignUpClick: () -> Unit,
        resetResult: () -> Unit
    ) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current


        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (uiState.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Spacer to push content to the top
                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(
                        uiState.result.isNotEmpty(),
                        enter = slideInHorizontally(initialOffsetX = { it }),
                        exit = slideOutHorizontally(targetOffsetX = { it }),
                    ) {
                        Text(
                            text = uiState.result,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(24.dp)
                        )
                        resetResult()
                    }


                    // Text Field for User Email
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        }
                    )

                    // Spacer to add vertical space between email and password fields
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text Field for Password
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        }
                    )

                    // Spacer to add vertical space between password and login button
                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to perform login action
                    Button(
                        onClick = { onLogInClick(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text("Log In")
                    }

                    // Spacer to add vertical space between login button and signup text
                    Spacer(modifier = Modifier.height(16.dp))

                    // Text "Don't have an account, create one" followed by Sign Up button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account,")
                        Spacer(modifier = Modifier.width(4.dp))
                        TextButton(
                            onClick = onSignUpClick
                        ) {
                            Text("Sign Up")
                        }
                    }
                }
            }
        }

    }
}
