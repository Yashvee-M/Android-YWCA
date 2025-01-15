package com.example.assignment3_quicknotesapp_yashvee_jan10


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment3_quicknotesapp_yashvee_jan10.Screen.HomeScreen
import com.example.assignment3_quicknotesapp_yashvee_jan10.ui.theme.Assignment3_QuickNotesApp_Yashvee_Jan10Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Assignment3_QuickNotesApp_Yashvee_Jan10Theme {
                val notes = remember { mutableStateListOf(
                    Note("Cloud Computing", "Cloud computing allows users to store and access data over the internet, rather than on local hard drives. It provides scalability, flexibility, and cost-efficiency. Popular cloud providers include AWS, Google Cloud, and Microsoft Azure."),
                    Note("Cybersecurity", "Cybersecurity involves protecting systems, networks, and data from cyberattacks. This includes encryption, firewalls, intrusion detection systems, and security protocols. It is critical for maintaining privacy and data integrity.")
                )}
                HomeScreen(
                    notes,
                    modifier = Modifier,
                    addNewNote = { note ->
                        notes.add(note)

                    },
                    updateNote = { note,index,deleteClicked ->
                        if(deleteClicked){
                            notes.removeAt(index)
                        }
                        else {
                            notes[index] = note
                        }
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment3_QuickNotesApp_Yashvee_Jan10Theme {
        //HomeScreen("Android")
    }
}