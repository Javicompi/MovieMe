package es.jnsoft.movieme.data.local.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.jnsoft.movieme.data.local.element.Element
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieMeDatabaseTest {

    private lateinit var database: MovieMeDatabase

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieMeDatabase::class.java
        ).build()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertElement_getElement() = runBlocking {
        val element = Element(
            backdrop = "",
            id = 17,
            mediaType = "movie",
            overview = "Whatever",
            poster = "",
            title = "Nothing"
        )
        database.elementDao().insertElement(element)
        val saved = database.elementDao().getElement(element.id)
        assert(saved.id == element.id)
    }

    @Test
    fun getAllElements() = runBlocking {
        val element1 = Element(
            backdrop = "",
            id = 1,
            mediaType = "movie",
            overview = "Whatever",
            poster = "",
            title = "Nothing"
        )
        database.elementDao().insertElement(element1)
        val element2 = Element(
            backdrop = "",
            id = 2,
            mediaType = "movie",
            overview = "Whatever",
            poster = "",
            title = "Nothing"
        )
        database.elementDao().insertElement(element2)
        val elements = database.elementDao().getAll()
        assert(elements.size == 2)
        assert(elements[0].id == 1L)
        assert(elements[1].id == 2L)
    }

    @Test
    fun deleteElement() = runBlocking {
        val element = Element(
            backdrop = "",
            id = 17,
            mediaType = "movie",
            overview = "Whatever",
            poster = "",
            title = "Nothing"
        )
        database.elementDao().insertElement(element)
        val saved = database.elementDao().getElement(element.id)
        assert(saved.id == element.id)
        database.elementDao().deleteElement(element)
        val elements = database.elementDao().getAll()
        assert(elements.isEmpty())
    }
}