package es.jnsoft.movieme.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.data.local.element.ElementDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val elementDao: ElementDao
) : LocalDataSource {

    override suspend fun getElements(): LiveData<List<Element>> {
        return elementDao.getAll()
    }

    override suspend fun getElement(id: String): LiveData<Element> {
        return elementDao.getElement(id)
    }

    override suspend fun insertElement(element: Element) {
        elementDao.insertElement(element)
    }

    override suspend fun deleteElement(element: Element) {
        elementDao.deleteElement(element)
    }
}