package es.jnsoft.movieme.data.local.element

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementDao {

    @Query("SELECT * FROM element")
    fun getAll(): LiveData<List<Element>>

    @Query("SELECT * FROM element WHERE id = :id LIMIT 1")
    fun getElement(id: String): LiveData<Element>

    @Query("SELECT EXISTS (SELECT * FROM element WHERE id = :id LIMIT 1)")
    fun getElementId(id: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(element: Element)

    @Delete
    suspend fun deleteElement(element: Element)
}