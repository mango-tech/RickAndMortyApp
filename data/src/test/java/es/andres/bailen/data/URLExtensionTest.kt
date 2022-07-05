package es.andres.bailen.data

import android.os.Build
import es.andres.bailen.data.extensions.getIntQueryParameter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class URLExtensionTest {

    @Test
    fun testUrlGetParam() {
        val stringURL = "https://rickandmortyapi.com/api/character/?page=20"
        val page = stringURL.getIntQueryParameter("page")
        Assert.assertEquals(20, page)
    }

    @Test
    fun testUrlGetParamError() {
        val stringURL = "rickandmortyapi.com/api/character/page=20"
        val page = stringURL.getIntQueryParameter("page")
        Assert.assertNull(page)
    }

    @Test
    fun testUrlGetParamNull() {
        val stringURL = "https://rickandmortyapi.com/api/character/"
        val page = stringURL.getIntQueryParameter("page")
        Assert.assertNull(page)
    }
}