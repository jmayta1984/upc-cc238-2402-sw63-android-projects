package pe.edu.upc.jokescompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface JokeDao {

    @Insert
    suspend fun insert(entity: JokeEntity)

    @Delete
    suspend fun delete(entity: JokeEntity)

    @Update
    suspend fun update(entity: JokeEntity)

    @Query("select * from jokes where description =:description")
    suspend fun fetchById(description: String): JokeEntity?

    @Query("select * from jokes")
    suspend fun fetchAll(): List<JokeEntity>

}