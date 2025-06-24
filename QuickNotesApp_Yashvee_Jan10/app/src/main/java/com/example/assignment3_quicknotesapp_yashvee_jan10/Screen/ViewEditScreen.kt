package com.example.assignment3_quicknotesapp_yashvee_jan10.Screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.assignment3_quicknotesapp_yashvee_jan10.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEditScreen(sTitle:String, sContent:String,
                   onEditNoteAdded: (Note,Boolean) -> Unit){
    val noteTitle = remember { mutableStateOf(sTitle) }
    val noteContent = remember { mutableStateOf(sContent) }
    val deleteClicked = remember{ mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Quick Notes App",
                    color = Color.White)
            },
                // Set background color of the TopAppBar
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE) // Set background color (purple in this case)
                ))

        },
        modifier = Modifier.fillMaxSize()) { innerPadding ->


        Column(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "View And Edit Note", // Title of the activity
                        style = MaterialTheme.typography.titleLarge // Use appropriate typography for title
                    )
                }
            )
            Spacer(Modifier.padding(5.dp))
            TextField(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                value = noteTitle.value,
                onValueChange = {
                    noteTitle.value = it
                },
                label = { Text("Edit Title : ") },
                placeholder = { Text(" Title ") }
            )
            Spacer(Modifier.padding(10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                value = noteContent.value,
                onValueChange = {
                    noteContent.value = it
                },
                label = { Text("Edit the Content") },
                placeholder = { Text("Content") }
            )
            Spacer(Modifier.padding(10.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {
                    // Make sure context is an Activity before calling finish
                    (context as? Activity)?.finish()
                }) {
                    Text("Cancel")
                }
                Button(onClick = {
                    if (noteTitle.value.isEmpty() || noteContent.value.isEmpty()) {
                        Toast.makeText(context, "Missing Info!!", Toast.LENGTH_LONG).show()
                    } else {
                        // Create the note object
                        val note = Note(noteTitle.value, noteContent.value)

                        // Notify the Activity with the new note
                        onEditNoteAdded(note,deleteClicked.value)
                    }
                }) {
                    Text("Save")
                }
                Button(onClick = {
                    // Notify the Activity with the new note
                    val note = Note(noteTitle.value, noteContent.value)
                    deleteClicked.value = true
                    onEditNoteAdded(note,deleteClicked.value)
                }) {
                    Text("Delete")
                }
            }


        }
    }
}
