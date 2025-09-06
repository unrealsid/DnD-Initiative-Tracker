package com.the_render_box.dnd.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.the_render_box.dnd.ui.composables.PlayerEntry
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.FileNotFoundException

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class DnDData(
    val id: String = "",
    var initiative: Int = 0,
    var name: String = "",
    var hp: Int = 0,
    var ac: Int = 0
)

const val dndDataFileName = "dnd_data";

fun EntryToDnDData(playerEntry: PlayerEntry): DnDData
{
    return DnDData(id = playerEntry.id,
        initiative = playerEntry.initiative.toInt(),
        name = playerEntry.name,
        hp = playerEntry.hp.toInt(),
        ac = playerEntry.ac.toInt()
    )
}

fun SaveDnDData(context: Context, data: DnDData)
{
    val allData = LoadAllDnDData(context).toMutableList()

    val idx = allData.indexOfFirst { it.id == data.id }
    if (idx > 0)
    {
        allData[idx] = data
    }
    else
    {
        allData.add(data)
    }
    SaveAllDnDData(context, allData)
}

@OptIn(ExperimentalSerializationApi::class)
fun SaveAllDnDData(context: Context, data: List<DnDData>)
{
    println("SaveAllDnDData: $data")
    val fileOutputStream = context.openFileOutput(dndDataFileName, Context.MODE_PRIVATE)
    Json.encodeToStream(data, fileOutputStream)
    Log.d("DnDData", "Encoded Data")
}

@OptIn(ExperimentalSerializationApi::class)
fun LoadAllDnDData(context: Context) : List<DnDData>
{
    var result : List<DnDData>
    try
    {
        val fileInputStream = context.openFileInput(dndDataFileName)
        result = Json.decodeFromStream<List<DnDData>>(fileInputStream)
    }
    catch (e: FileNotFoundException)
    {
        result = listOf<DnDData>()
        println("Could not load the data. Starting with empty list.")
    }

    println("LoadAllDnDData returns: $result");
    return result;
}