package edu.northeastern.camposal

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.View
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class DialogNewReview : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        /*
        - init AlertDialog.Builder object
        - init LayoutInflater object to turn xml layout into an object
        - set our dialog view by using .inflate function
         */
        val builder = AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_new_review, null)

        /*
        - referencing UI widgets
         */
        val editTitle = dialogView.findViewById(R.id.editTitle) as EditText
        val editReview = dialogView.findViewById(R.id.editReview) as EditText
        val editRating = dialogView.findViewById(R.id.editRating) as EditText
        val checkBoxRecommend = dialogView.findViewById(R.id.checkBoxRecommend) as CheckBox
        val btnCancel = dialogView.findViewById(R.id.btnCancel) as Button
        val btnOK = dialogView.findViewById(R.id.btnOK) as Button

        /*
        - setting the message of the dialog with *builder*
         */
        builder.setView(dialogView).setMessage("Add New Review")

        /*
        - handling cancel button so it closes the dialog window
         */
        btnCancel.setOnClickListener {
            dismiss()
        }
        /*
        - handling ok button clicks so it created a new review and then closes the window
         */
        btnOK.setOnClickListener {
            //creating an instance of Review
            val newReview = Review()
            //matching its properties to the inputs of the user
            newReview.title = editTitle.text.toString()
            newReview.review = editReview.text.toString()
            newReview.rating = editRating.text.toString().toDouble()
            newReview.recommend = checkBoxRecommend.isChecked
            //reference to MainActivity
            val callingActivity = activity as MainActivity?
            //passing a new review to MainActivity
            callingActivity!!.createNewReview(newReview)
            //close the dialog
            dismiss()

        }
        return builder.create()
    }
}