package com.rodrigocibils.jobsity

import com.rodrigocibils.jobsity.contracts.MainContract
import com.rodrigocibils.jobsity.models.Show
import com.rodrigocibils.jobsity.presenters.MainPresenter
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainPresenterTest {

    private lateinit var view: MainContract.ViewContract
    private lateinit var presenter: MainContract.PresenterContract

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        presenter = MainPresenter(view, mutableListOf())
    }

    @Test
    fun `test list being updated when get all api call succeeds`() {
        // Given
        val successCallback = presenter.javaClass.getDeclaredMethod("onGetShowsSuccess", MutableList::class.java)
        successCallback.isAccessible = true
        val shows = mutableListOf<Show>()
        val parameters = arrayOfNulls<Any>(1)
        shows.add(Show(0, "", "", "", "", listOf(), listOf()))
        parameters[0] = shows

        //When
        successCallback.invoke(presenter, *parameters)

        // Then
        verify { view.updateRecyclerView() }
    }

    @Test
    fun `test list being updated when search api call succeeds`() {
        // Given
        val successCallback = presenter.javaClass.getDeclaredMethod("onGetShowsSearchSuccess", MutableList::class.java)
        successCallback.isAccessible = true
        val shows = mutableListOf<Show>()
        val parameters = arrayOfNulls<Any>(1)
        shows.add(Show(0, "", "", "", "", listOf(), listOf()))
        parameters[0] = shows

        //When
        successCallback.invoke(presenter, *parameters)

        // Then
        verify { view.updateRecyclerView() }
    }

    @Test
    fun `test no results being shown when search is empty`() {
        // Given
        val successCallback = presenter.javaClass.getDeclaredMethod("onGetShowsSearchSuccess", MutableList::class.java)
        successCallback.isAccessible = true
        val shows = mutableListOf<Show>()
        val parameters = arrayOfNulls<Any>(1)
        parameters[0] = shows

        //When
        successCallback.invoke(presenter, *parameters)

        // Then
        verify { view.showNoResults() }
    }

    @Test
    fun `test error screen being shown when api call fails`() {
        // Given
        val errorCallback = presenter.javaClass.getDeclaredMethod("onGetShowsError")
        errorCallback.isAccessible = true

        // When
        errorCallback.invoke(presenter)

        // Then
        verify { view.showError() }
    }

    @Test
    fun `test loading being shown while getting page 0 shows`() {
        // When
        presenter.getShows(0)

        // Then
        verify { view.showLoading() }
    }

    @Test
    fun `test loading not being shown while getting further pages of shows`() {
        // When
        presenter.getShows(1)

        // Then
        verify(exactly = 0) { view.showLoading() }
    }

    @Test
    fun `test loading being shown while searching shows by name`() {
        // When
        presenter.getShows("show name")

        // Then
        verify { view.showLoading() }
    }
}