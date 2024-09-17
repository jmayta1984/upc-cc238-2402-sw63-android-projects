package pe.edu.upc.superherocompose.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("full_name")
    val fullName: String,
    @ColumnInfo("url")
    val url: String
)
