import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Import the sp unit for font size

// Custom color palette based on the provided image
val DarkGreen = Color(0xFF2D6053)
val LightGreen = Color(0xFF589B85)
val DarkRed = Color(0xFF9B5858) // Based on the color of the right-most cell in the image

/**
 * A composable function that creates a single row with "Save" and "Delete" buttons
 * followed by four text entry fields. This is designed to be a component within a
 * larger table-like structure. The styling is customized to match the provided image.
 *
 * @param onSaveClick The callback to be invoked when the "Save" button is clicked.
 * This is typically used to persist the row's data.
 * @param onDeleteClick The callback to be invoked when the "Delete" button is clicked.
 * This is typically used to remove the row from the table.
 * @param onValueChange A lambda function that provides the updated text from the four entry fields.
 * It takes four strings representing the content of each text field.
 */
@Composable
fun EntryRow(
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onValueChange: (initiative: Int?, name: String, hp: Int?, ac: Int?) -> Unit
) {
    // Use remember and mutableStateOf to manage the state of each of the four text fields
    var initiativeText by remember { mutableStateOf("") }
    var nameText by remember { mutableStateOf("") }
    var hpText by remember { mutableStateOf("") }
    var armorClassText by remember { mutableStateOf("") }

    // Use a Column composable to stack the text fields row and the buttons row.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // The four text entry spaces are styled with a white background and dark border.
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            disabledBorderColor = Color.White,
            focusedLabelColor = DarkGreen,
            unfocusedLabelColor = DarkGreen,
            cursorColor = DarkGreen
        )

        // First row for the four text fields
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = initiativeText,
                onValueChange = { input ->
                    if(input.all { it.isDigit() }){
                        initiativeText = input
                        onValueChange(initiativeText.toIntOrNull(), nameText, hpText.toIntOrNull(), armorClassText.toIntOrNull())
                    }
                },
                label = { Text("Initiative", color = DarkGreen, fontSize = 12.sp) },
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                value = nameText,
                onValueChange = {
                    nameText = it
                    onValueChange(initiativeText.toIntOrNull(), nameText, hpText.toIntOrNull(), armorClassText.toIntOrNull())
                },
                label = { Text("Name", color = DarkGreen, fontSize = 12.sp) },
                colors = textFieldColors,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                value = hpText,
                onValueChange = { input ->
                    if(input.all{ it.isDigit() }) {
                        hpText = input
                        onValueChange(initiativeText.toIntOrNull(), nameText, hpText.toIntOrNull(), armorClassText.toIntOrNull())
                    }
                },
                label = { Text("HP", color = DarkGreen, fontSize = 12.sp) },
                colors = textFieldColors,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedTextField(
                value = armorClassText,
                onValueChange = { input ->
                    if (input.all { it.isDigit() }) {
                        armorClassText = input
                        onValueChange(initiativeText.toIntOrNull(), nameText, hpText.toIntOrNull(), armorClassText.toIntOrNull())
                    }
                },
                label = { Text("AC", color = DarkGreen, fontSize = 12.sp) },
                colors = textFieldColors,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        //TODO: Remove and make data save asynchronously
        // Second row for the buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            // "Save" button with a custom background color and border
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(color = DarkGreen)
                    .border(BorderStroke(1.dp, Color.White))
            ) {
                Button(
                    onClick = onSaveClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // "Delete" button with a custom red background and border
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(color = DarkRed)
                    .border(BorderStroke(1.dp, Color.White))
            ) {
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkRed,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

/**
 * A preview function to display the EntryRow composable in Android Studio's preview pane.
 * This demonstrates how to use the composable with dummy callbacks.
 */
@Preview(showBackground = true)
@Composable
fun EntryRowPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF589B85)) // Set a background to match the preview image
    ) {
        EntryRow(
            onSaveClick = { /* Handle save click */ },
            onDeleteClick = { /* Handle delete click */ },
            onValueChange = { _, _, _, _ -> /* Handle value change */ }
        )
    }
}
