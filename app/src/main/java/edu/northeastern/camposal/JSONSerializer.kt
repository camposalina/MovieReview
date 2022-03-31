package edu.northeastern.camposal

import android.content.Context
import org.json.JSONException
import org.json.JSONArray
import org.json.JSONTokener
import java.io.*

class JSONSerializer(private val filename: String, private val context: Context) {
    @Throws(IOException::class, JSONException::class)
    fun save(reviews: ArrayList<Review>) {
        // Make an array in JSON format
        val jArray = JSONArray()
        // And load it with the notes
        for (r in reviews)
            jArray.put(r.convertToJSON())
        // Now write it to the private disk space of our app
        var writer: Writer? = null
        try {
            val out = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(out)
            writer.write(jArray.toString())
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }
    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Review> {
        val reviewList = ArrayList<Review>()
        var reader: BufferedReader? = null
        try {
            val `in` = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(`in`))
            val jsonString = StringBuilder()
            for (line in reader.readLine()) {
                jsonString.append(line)
            }
            val jArray = JSONTokener(jsonString.toString()).
            nextValue() as JSONArray
            for (i in 0 until jArray.length()) {
                reviewList.add(Review(jArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
            // we will ignore this one, since it happens
            // when we start fresh. You could add a log here.
        } finally {
            // This will always run
            reader!!.close()
        }
        return reviewList
    }
}