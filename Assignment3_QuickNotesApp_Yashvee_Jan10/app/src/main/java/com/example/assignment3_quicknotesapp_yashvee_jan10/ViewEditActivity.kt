package com.example.assignment3_quicknotesapp_yashvee_jan10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment3_quicknotesapp_yashvee_jan10.Screen.AddNoteScreen
import com.example.assignment3_quicknotesapp_yashvee_jan10.Screen.ViewEditScreen
import com.example.assignment3_quicknotesapp_yashvee_jan10.ui.theme.Assignment3_QuickNotesApp_Yashvee_Jan10Theme

class ViewEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Assignment3_QuickNotesApp_Yashvee_Jan10Theme {
                val selectedIndex = intent.getIntExtra("selectedIndex",0)
                var selectedNote = intent.getParcelableExtra<Note>("selectedNote")

                Log.d("Index",selectedIndex.toString()+selectedNote.toString())
                if (selectedNote != null) {
                    ViewEditScreen(selectedNote.title, selectedNote.content, onEditNoteAdded = { note,deleteClicked ->
                        // Once the note is added, return the result
                        val resultIntent = Intent()
                        resultIntent.putExtra("note_data", note)
                        resultIntent.putExtra("deleteClicked",deleteClicked)
                        // Set result and finish the activity
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    })
                }

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Assignment3_QuickNotesApp_Yashvee_Jan10Theme {

    }
}