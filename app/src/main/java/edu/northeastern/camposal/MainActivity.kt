package edu.northeastern.camposal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.northeastern.camposal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mSerializer: JSONSerializer? = null
    private var reviewList: ArrayList<Review>? = null
    //private var reviewList = ArrayList<Review>()
    private var recyclerView: RecyclerView? = null
    private var adapter: ReviewAdapter? = null
    private var showDividers: Boolean = false


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val dialog = DialogNewReview()
            dialog.show(supportFragmentManager, "")
        }

        mSerializer = JSONSerializer("MovieReviews.json",
            applicationContext)
        try {
            reviewList = mSerializer!!.load()
        } catch (e: Exception) {
            reviewList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = reviewList?.let { ReviewAdapter(this, it) }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Movie Reviews", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)

        if (showDividers)
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        else {
            // check there are some dividers or the app will crash
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    fun createNewReview(rev: Review) {
        reviewList?.add(rev)
        adapter!!.notifyDataSetChanged()
    }

    fun showReview(reviewToShow: Int) {
        val dialog = DialogShowReview()
        reviewList?.get(reviewToShow)?.let { dialog.sendReviewSelected(it) }
        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveReviews() {
        try {
            mSerializer!!.save(this.reviewList!!)
        } catch (e: Exception) {
            Log.e("Error Saving Reviews", "", e)
        }
    }

    override fun onPause() {
        super.onPause()

        saveReviews()
    }

}