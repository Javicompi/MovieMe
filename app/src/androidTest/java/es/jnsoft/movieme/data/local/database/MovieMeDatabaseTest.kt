package es.jnsoft.movieme.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieMeDatabaseTest {

    private lateinit var database: MovieMeDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
        val element = createElement(17)
        database.elementDao().insertElement(element)
        val saved = database.elementDao().getElement(element.id).getOrAwaitValue()
        assertThat(saved, notNullValue())
        assertThat(saved.movieDbId, `is`(17))
    }

    @Test
    fun getAllElements() = runBlocking {
        val element1 = createElement(1)
        val element2 = createElement(2)
        database.elementDao().insertElement(element1)
        database.elementDao().insertElement(element2)
        val elements = database.elementDao().getAll().getOrAwaitValue()
        assertThat(elements.size, `is`(2))
        assertThat(elements[0].movieDbId, `is`(1))
        assertThat(elements[1].movieDbId, `is`(2))
    }

    @Test
    fun deleteElement() = runBlocking {
        val element = createElement(17)
        database.elementDao().insertElement(element)
        val saved = database.elementDao().getElement(element.id).getOrAwaitValue()
        assertThat(saved.movieDbId, `is`(17))
        database.elementDao().deleteElement(element)
        val elements = database.elementDao().getAll().getOrAwaitValue()
        assertThat(elements, empty())
    }

    fun createElement(id: Long): Element {
        return Element(
            backdrop = "",
            id = "movie$id",
            language = "en",
            mediaType = "movie",
            movieDbId = id,
            overview = "Whatever",
            poster = "",
            releaseDate = "2020-10-17",
            title = "Nothing"
        )
    }
}