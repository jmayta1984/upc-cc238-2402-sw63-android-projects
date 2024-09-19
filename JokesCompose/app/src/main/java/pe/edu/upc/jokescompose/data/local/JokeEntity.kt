package pe.edu.upc.jokescompose.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey
    val description: String,
    @ColumnInfo("score")
    val score: Int
)
