package edu.northeastern.camposal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter (
    private val mainActivity: MainActivity,
    private val reviewList: List<Review>)
    : RecyclerView.Adapter<ReviewAdapter.ListItemHolder>() {

    inner class ListItemHolder (view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        internal var title = view.findViewById<View>(R.id.textViewTitle) as TextView
        internal var review = view.findViewById<View>(R.id.textViewReview) as TextView
        internal var rating = view.findViewById<View>(R.id.textViewRating) as TextView
        internal var status = view.findViewById<View>(R.id.textViewReccomendStatus) as TextView

        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mainActivity.showReview(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return ListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val reviewBlock = reviewList[position]

        holder.title.text = reviewBlock.title

        holder.review.text = reviewBlock.review!!.substring(0)

        holder.rating.text = reviewBlock.rating.toString()

        when {
            reviewBlock.recommend -> holder.status.text =
                mainActivity.resources.getString(R.string.recommend_text)

        }

    }

    override fun getItemCount(): Int {
        if (reviewList != null) {
            return reviewList.size
        }
        //error
        return -1
    }
}


