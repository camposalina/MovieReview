package edu.northeastern.camposal

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogShowReview : DialogFragment (){
    //init review
    private var reviewPost: Review? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity!!)
        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_review, null)

        val txtTitle = dialogView.findViewById(R.id.txtTitle) as TextView
        val txtReview = dialogView.findViewById(R.id.txtReview) as TextView
        val txtRating = dialogView.findViewById(R.id.ratingShow) as TextView
        txtTitle.text = reviewPost!!.title
        txtReview.text = reviewPost!!.review
        txtRating.text = reviewPost!!.rating.toString()
        val txtRecommend = dialogView.findViewById(R.id.recommendShow) as TextView

        if (!reviewPost!!.recommend){
            txtRecommend.visibility = View.GONE
        }

        val btnOK = dialogView.findViewById(R.id.btnOK) as Button
        builder.setView(dialogView).setMessage("Your Review")
        btnOK.setOnClickListener {
            dismiss()
        }
        return builder.create()
    }

    //receiving a review from MainActivity
    fun sendReviewSelected(reviewSelected: Review) {
        reviewPost = reviewSelected
    }
}