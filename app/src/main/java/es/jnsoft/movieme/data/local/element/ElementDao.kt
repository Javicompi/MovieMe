package es.jnsoft.movieme.data.local.element

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementDao {

    @Query("SELECT * FROM element")
    fun getAll(): LiveData<List<Element>>

    @Query("SELECT * FROM element WHERE id = :id LIMIT 1")
    fun getElement(id: Long): LiveData<Element>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(element: Element)

    @Delete
    suspend fun deleteElement(element: Element)
}