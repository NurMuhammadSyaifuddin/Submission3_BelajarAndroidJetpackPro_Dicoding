package com.project.lastsubmission.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.project.lastsubmission.R
import com.project.lastsubmission.utils.EspressoIdlingResource
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovieAndTvShowTest() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        onView(withId(R.id.navigation_movie)).perform(click())
    }

    @Test
    fun viewTest() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText(R.string.tab_title_movie)).perform(click())
        onView(withText(R.string.tab_title_tvshow)).perform(click())

        onView(withId(R.id.navigation_movie)).perform(click())
    }

    @Test
    fun insertAndUpdateFavoriteTest() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        Espresso.pressBack()

        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        Espresso.pressBack()

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        Espresso.pressBack()

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText(R.string.tab_title_movie)).perform(click())
        onView(withText(R.string.tab_title_tvshow)).perform(click())
        onView(withId(R.id.rv_favorite_tvshow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        Espresso.pressBack()

    }

    @Test
    fun detailMovieTest() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(R.id.rv_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )

        onView(withId(R.id.img_detail_hightlight))
            .check(matches(isDisplayed()))
        onView(withId(R.id.img_detail_poster))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc))
            .check(matches(isDisplayed()))

        Espresso.pressBack()
    }

    @Test
    fun detailTvShowTest() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(R.id.rv_tvshow))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )

        onView(withId(R.id.img_detail_hightlight))
            .check(matches(isDisplayed()))
        onView(withId(R.id.img_detail_poster))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc))
            .check(matches(isDisplayed()))

        Espresso.pressBack()
    }

}