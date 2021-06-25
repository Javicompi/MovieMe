package es.jnsoft.movieme.data.local

import androidx.lifecycle.LiveData
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.local.element.Element
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getElements(): LiveData<List<Element>>

    suspend fun getElement(id: String): LiveData<Element>

    suspend fun getElementId(id: String): LiveData<Boolean>

    suspend fun insertElement(element: Element)

    suspend fun deleteElement(element: Element)
}