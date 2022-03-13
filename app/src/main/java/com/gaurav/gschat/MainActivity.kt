package com.gaurav.gschat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var userAdapter : UserAdapter
    private lateinit var userAuth : FirebaseAuth
    private lateinit var databaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()
        userAdapter = UserAdapter(this, userList)
        userAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        userRecyclerView = findViewById(R.id.user_recycler_view)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter

        databaseReference.child("user").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (s in snapshot.children) {
                    val user = s.getValue(User::class.java)
                    if (userAuth.currentUser?.uid != user?.uid)
                        userList.add(user!!)
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    class UserViewHolder(itemView : View, val context: Context): RecyclerView.ViewHolder(itemView) {
        private lateinit var user: User
        val textName = itemView.findViewById<TextView>(R.id.user_title)
    }

    class UserAdapter(val context : Context, val users : ArrayList<User>) :
        RecyclerView.Adapter<UserViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view : View = LayoutInflater.from(context)
                .inflate(R.layout.list_item_user, parent, false)
            return UserViewHolder(view, context)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users[position]
            holder.textName.text = user.name
            holder.itemView.setOnClickListener{
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("name", user.name)
                intent.putExtra("uid", user.uid)
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return users.size
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.log_out) {
            userAuth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}