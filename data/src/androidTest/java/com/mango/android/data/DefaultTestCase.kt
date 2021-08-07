package com.mango.android.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mango.android.data.util.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

open class DefaultTestCase: TestCase() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()
}