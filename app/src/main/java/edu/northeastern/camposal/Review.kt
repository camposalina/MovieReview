package edu.northeastern.camposal

import org.json.JSONException
import org.json.JSONObject

class Review {
    var title: String? = null
    var review: String? = null
    var rating: Double? = null
    var recommend: Boolean = false

    private val JSON_TITLE = "title"
    private val JSON_REVIEW = "review"
    private val JSON_RATING = "rating"
    private val JSON_RECOMMEND = "recommend"

    // Creating JSONObjects with Constructor
    @Throws(JSONException::class)
    constructor(jo: JSONObject) {
        title = jo.getString(JSON_TITLE)
        review = jo.getString(JSON_REVIEW)
        rating = jo.getDouble(JSON_RATING)
        recommend = jo.getBoolean(JSON_RECOMMEND)
    }
    // empty default constructor for when we create a review to pass to the new review dialog
    constructor() {

    }

    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
        val jObject = JSONObject()
        jObject.put(JSON_TITLE, title)
        jObject.put(JSON_REVIEW, review)
        jObject.put(JSON_RATING, rating)
        jObject.put(JSON_RECOMMEND, recommend)
        return jObject
    }
}