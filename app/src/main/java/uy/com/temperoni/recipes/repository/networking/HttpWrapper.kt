package uy.com.temperoni.recipes.repository.networking

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpWrapper(
    private var connection: HttpURLConnection? = null,
    private var reader: BufferedReader? = null
) {

    fun getJson(urlString: String): String {
        val url = URL(urlString)
        connection = url.openConnection() as? HttpURLConnection
        connection?.connect()

        val stream: InputStream? = connection?.inputStream

        reader = BufferedReader(InputStreamReader(stream))

        val buffer = StringBuffer()
        var line = reader?.readLine()

        while (line != null) {
            buffer.append(line + "\n");
            line = reader?.readLine()
        }

        return buffer.toString()
    }

    fun dispose() {
        connection?.disconnect()
        try {
            reader?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}