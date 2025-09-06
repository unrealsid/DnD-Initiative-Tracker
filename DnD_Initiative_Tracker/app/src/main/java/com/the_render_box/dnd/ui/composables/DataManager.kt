package com.the_render_box.dnd.ui.composables


import EntryRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.the_render_box.dnd.MainActivity
import com.the_render_box.dnd.data.EntryToDnDData
import com.the_render_box.dnd.data.LoadAllDnDData
import com.the_render_box.dnd.data.SaveDnDData

// Data class to hold the state of an entry row
data class PlayerEntry(
    val id: String = java.util.UUID.randomUUID().toString(),
    var initiative: String = "",
    var name: String = "",
    var hp: String = "",
    var ac: String = ""
)

/**
 * A composable function that displays a list of EntryRow components and a button
 * to add new entries.
 */
@Composable
fun InfoManagerComposable(modifier: Modifier = Modifier) {
    var entries by remember { mutableStateOf(listOf<PlayerEntry>()) }
    val context = LocalContext.current

    //Load existing data
    LaunchedEffect(Unit) {
        val data = try {
            LoadAllDnDData(context)
                .map {
                    PlayerEntry(
                        id = it.id,
                        initiative = it.initiative.toString(),
                        name = it.name,
                        hp = it.hp.toString(),
                        ac = it.ac.toString()
                    )
                }
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")

            // Return a single empty entry on error or initial load
            listOf(PlayerEntry())
        }
        entries = data
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E403F))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Use a Column to show the list of entries
        Column(
            modifier = Modifier.weight(1f)
        ) {
            entries.forEach { entry ->
                //println(entry)
                EntryRow(
                    onSaveClick = { SaveDnDData(MainActivity.appContext,EntryToDnDData(entry)) },
                    onDeleteClick = {
                        entries = entries.filter { it.id != entry.id }
                    },
                    onValueChange = { init, name, hp, ac ->
                        entry.initiative = init?.toString() ?: "Error"
                        entry.name = name
                        entry.hp = hp?.toString() ?:"Error"
                        entry.ac = ac?.toString() ?:"Error"
                    }
                )
            }
        }

        Button(
            onClick = {
                entries = entries + PlayerEntry()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Add Entry")
        }

        Button(
            onClick = {
                // TODO: Add sorting logic here
            },
            modifier = Modifier.fillMaxWidth(),

        ) {
            Text("Sort")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoManagerComposablePreview() {
    InfoManagerComposable(modifier = Modifier.fillMaxSize())
}