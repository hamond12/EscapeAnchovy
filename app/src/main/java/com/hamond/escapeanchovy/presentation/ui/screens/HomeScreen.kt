package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.utils.AccountUtils.cancelAutoLogin
import com.hamond.escapeanchovy.utils.AccountUtils.getUserEmail

@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current

    val email = getUserEmail(context)!!

    var userName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Button(onClick = {
            cancelAutoLogin(context)
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.HOME) { inclusive = true }
            }
        }) {
            Text(text = userName)
        }
    }

    getUserNameByEmail(email) { name, error ->
        if (error == null) {
            userName = name!!
        } else {
            // 에러처리
        }
    }

}

fun getUserNameByEmail(email: String, callback: (String?, String?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("User")
        .whereEqualTo("email", email).limit(1).get()
        .addOnSuccessListener {
            val document = it.documents.first()
            val name = document.getString("name")
            callback(name, null)
        }
        .addOnFailureListener {
            callback(null, it.message)
        }
}

