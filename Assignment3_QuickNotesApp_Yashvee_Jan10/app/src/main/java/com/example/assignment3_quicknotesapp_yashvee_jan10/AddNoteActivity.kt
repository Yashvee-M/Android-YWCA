package com.example.assignment3_quicknotesapp_yashvee_jan10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment3_quicknotesapp_yashvee_jan10.Screen.AddNoteScreen
import com.example.assignment3_quicknotesapp_yashvee_jan10.ui.theme.Assignment3_QuickNotesApp_Yashvee_Jan10Theme

class AddNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3_QuickNotesApp_Yashvee_Jan10Theme {
                AddNoteScreen(onNoteAdded = { note ->
                    // Once the note is added, return the result
                    val resultIntent = Intent()
                    resultIntent.putExtra("note_data", note)

                    // Set result and finish the activity
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Assignment3_QuickNotesApp_Yashvee_Jan10Theme {

    }
}