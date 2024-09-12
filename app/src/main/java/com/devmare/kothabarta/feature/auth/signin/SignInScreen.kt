package com.devmare.kothabarta.feature.auth.signin

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
fun SignInScreen(navController: NavController) {

    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(
        key1 = uiState.value,
    ) {
        when (uiState.value) {
            is SignInState.Success -> {
                navController.navigate("home")
            }

            is SignInState.Error -> {
                Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show()
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

            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignInState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.signIn(email, password)
                    },
                    enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10),
                    contentPadding = PaddingValues(15.dp)
                ) {
                    Text("Sign In")
                }
            }

            TextButton(
                onClick = {
                    navController.navigate("signup")
                }
            ) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navController = rememberNavController())
}

