package com.devmare.kothabarta.feature.auth.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devmare.kothabarta.R

@Composable
fun SignUpScreen(navController: NavController) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(
        key1 = uiState.value,
    ) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate("home")
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }

            else -> {

            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(it)
                .background(color = Color.White)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.size(32.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { it ->
                    username = it
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10),
                label = {
                    Text("Username")
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { it ->
                    email = it
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10),
                label = {
                    Text("Email")
                }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { it ->
                    password = it
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10),
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text("Password")
                }
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { it ->
                    confirmPassword = it
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10),
                visualTransformation = PasswordVisualTransformation(),
                isError = password != confirmPassword && confirmPassword.isNotEmpty() && password.isNotEmpty(),
                label = {
                    Text("Confirm Password")
                }
            )



            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignUpState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.signUp(email, password, username)
                    },
                    enabled = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10),
                    contentPadding = PaddingValues(15.dp)
                ) {
                    Text("Sign Up")
                }
            }

            TextButton(
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Text("Already have an account? Sign In")
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}

