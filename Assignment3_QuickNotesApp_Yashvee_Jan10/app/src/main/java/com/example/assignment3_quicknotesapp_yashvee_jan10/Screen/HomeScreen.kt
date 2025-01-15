package com.example.assignment3_quicknotesapp_yashvee_jan10.Screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3_quicknotesapp_yashvee_jan10.AddNoteActivity
import com.example.assignment3_quicknotesapp_yashvee_jan10.Note
import com.example.assignment3_quicknotesapp_yashvee_jan10.ViewEditActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(notes: MutableList<Note>, modifier: Modifier = Modifier,
               addNewNote: (Note) -> Unit,
               updateNote: (Note,selectedIndex:Int, deleteClicked:Boolean) -> Unit){
    val context = LocalContext.current
    // Create a mutable state to store selected index
    var selectedIndex by remember { mutableStateOf(0) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getParcelableExtra<Note>("note_data")
            //Handle the received Note object
            note?.let{
                //Update UI or State with received note
                addNewNote(note)
                Log.d("Received user", note.title)
            }
        }
    }
    val viewEditLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getParcelableExtra<Note>("note_data")
            val deleteClicked = result.data?.getBooleanExtra("deleteClicked",false)

            //Handle the received Note object
            note?.let{
                //Update UI or State with received note
                if (deleteClicked != null) {
                    updateNote(note, selectedIndex, deleteClicked)
                }
                Log.d("Received user", note.title)
            }
        }
    }

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
        //FloatingButton
        floatingActionButton = {
            // Floating Action Button (FAB)
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, AddNoteActivity::class.java)
                    launcher.launch(intent)
                }
            ) {
                // FAB icon
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        },
        modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier.fillMaxSize().padding(innerPadding)) {
            Spacer(Modifier.padding(10.dp))
            LazyColumn(
                modifier = Modifier.fillMaxHeight()) {
                items(notes.size) { index ->
                    Column (modifier = Modifier.clickable {
                        selectedIndex = index
                        val intent = Intent(context, ViewEditActivity::class.java)
                        intent.putExtra("selectedIndex",selectedIndex)
                        intent.putExtra("selectedNote",notes[selectedIndex])
                        viewEditLauncher.launch(intent)

                    }) {
                        val currentNote = notes[index]
                        Card(
                            modifier = Modifier.fillMaxHeight().fillMaxWidth()
                                .background(Color.White).padding(10.dp)
                                .border(1.dp,Color.DarkGray)
                        ) {
                            Text(modifier = Modifier.border(0.5.dp, Color.LightGray).padding(10.dp),
                                text = currentNote.title,
                                style = TextStyle(
                                    fontSize = 18.sp, // Set font size to 18sp
                                    fontStyle = FontStyle.Normal, // Set font style to Normal
                                    color = Color.Black // Optional: set text color (default is black)
                                ),)
                            Spacer(Modifier.padding(5.dp))
                            //Text(currentNote.content)
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = currentNote.content,
                                style = TextStyle(
                                    fontSize = 18.sp, // Set font size to 18sp
                                    fontStyle = FontStyle.Italic, // Set font style to Italic
                                    color = Color.DarkGray // Optional: set text color (default is black)
                                ),
                                maxLines = 2, // Limits the content to 2 lines
                                overflow = TextOverflow.Ellipsis // Adds "..." at the end if the content exceeds 2 lines
                            )
                        }
                    }
                }
            }

        }
    }

}