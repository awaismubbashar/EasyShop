package com.example.easyshop.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HeaderView(modifier: Modifier) {
    var name by remember { mutableStateOf("") }
    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        val uid = Firebase.auth.currentUser?.uid
        uid?.let {
            firestore.collection("users")
                .document(it).get()
                .addOnCompleteListener { task ->
                    name = task.result.get("name").toString().split(" ")[0]
                }
        }
    }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "Welcome Back", style = TextStyle(
                    fontSize = 14.sp
                )
            )
            Text(
                name, style = TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
            )
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    }
}

@Preview
@Composable
fun HeaderViewPreview() {
    HeaderView(modifier = Modifier)
}