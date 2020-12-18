package com.example.task

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.StrictMode
import android.text.Html
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var adapter: MyRecyclerViewAdapter? = null
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pbLoading = findViewById<ProgressBar>(R.id.pbLoading)
        pbLoading.visibility= View.VISIBLE
        val mSharedPrefManager = SharedPrefManager(applicationContext)
        if (isNetworkAvailable()) {
            var outPut: String? = null
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val url = URL("https://instabug.com")
            val `in` = BufferedReader(
                InputStreamReader(
                    url.openStream()
                )
            )
            var inputLine: String?
            while (`in`.readLine().also { inputLine = it } != null) outPut += inputLine
            `in`.close()
            val htmlAsSpanned = Html.fromHtml(outPut)
            htmlAsSpanned.toString().replace("\"", " ")
            mSharedPrefManager.input=htmlAsSpanned.toString()
            calculateWords(htmlAsSpanned.toString())
        }else{
            calculateWords(mSharedPrefManager.input)
        }
        pbLoading.visibility= View.GONE
    }

    private fun validateString(str: String): Boolean {
        var str = str
        str = str.toLowerCase(Locale.ROOT)
        val charArray = str.toCharArray()
        for (i in charArray.indices) {
            val ch = charArray[i]
            if (ch !in 'a'..'z') {
                return false
            }
        }
        return true
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    private fun calculateWords(string:String){
        val words1: ArrayList<String>?
        words1 = ArrayList()
        val words2: ArrayList<String>?
        words2 = ArrayList()
        words1.addAll(string.split(" ").toTypedArray())
        for (i in string.split(" ").toTypedArray().indices) {
            if (validateString(words1[i])) {
                words2.add(words1[i].toLowerCase())
            }
        }
        val occurrences: MutableMap<String, Int> = HashMap()
        for (word in words2) {
            var oldCount = occurrences[word]
            if (oldCount == null) {
                oldCount = 0
            }
            occurrences[word] = oldCount + 1
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rvData)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter(this, occurrences)
        recyclerView.adapter = adapter
    }
}