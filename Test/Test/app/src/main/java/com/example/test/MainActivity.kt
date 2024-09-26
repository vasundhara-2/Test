package  com.example.test

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loading = findViewById<ProgressBar>(R.id.progress_bar)
        lifecycleScope.launchWhenCreated {
            loading.visibility = View.VISIBLE
            val response = try{
                RetrofitInstance.api.getUserData()
            }catch (e: Exception){
                Log.e(TAG, "Exception: $e")
                loading.visibility = View.GONE
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                Toast.makeText(this@MainActivity, ""+response.body()?.size+" users loaded!", Toast.LENGTH_SHORT).show()
                val usersRecyclerView = findViewById<RecyclerView>(R.id.users_list_view).apply {
                    adapter = UserAdapter(){it}
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                }
                (usersRecyclerView.adapter as UserAdapter).submitList(response.body())
                loading.visibility = View.GONE
            } else {
                Toast.makeText(this@MainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }
        }
    }
}