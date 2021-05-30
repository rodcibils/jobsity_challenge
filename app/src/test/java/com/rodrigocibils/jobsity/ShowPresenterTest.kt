package com.rodrigocibils.jobsity

import com.rodrigocibils.jobsity.contracts.ShowContract
import com.rodrigocibils.jobsity.models.Episode
import com.rodrigocibils.jobsity.presenters.ShowPresenter
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ShowPresenterTest {

    private lateinit var view: ShowContract.ViewContract
    private lateinit var presenter: ShowContract.PresenterContract

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        presenter = ShowPresenter(view, mutableListOf())
    }

    @Test
    fun `test loading being shown when fetching episodes list from api`() {
        //When
        presenter.getEpisodes(0)

        // Then
        verify { view.showLoading() }
    }

    @Test
    fun `test list being updated when api call succeeds`() {
        // Given
        val successCallback = presenter.javaClass.getDeclaredMethod("onGetEpisodesSuccess", MutableList::class.java)
        successCallback.isAccessible = true
        val episodes = mutableListOf<Episode>()
        val parameters = arrayOfNulls<Any>(1)
        parameters[0] = episodes

        // When
        successCallback.invoke(presenter, *parameters)

        // Then
        verify { view.updateRecyclerView() }
    }

    @Test
    fun `test error screen being shown when api call fails`() {
        // Given
        val errorCallback = presenter.javaClass.getDeclaredMethod("onGetEpisodesError")
        errorCallback.isAccessible = true

        // When
        errorCallback.invoke(presenter)

        // Then
        verify { view.showError() }
    }
}