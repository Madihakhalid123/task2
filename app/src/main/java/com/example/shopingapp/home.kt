package com.example.shopingapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.ListFormatter.Width
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.databinding.ActivityHomeBinding
import com.example.shopingapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class home : AppCompatActivity(), Adopter_list.OnItemClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        binding.lgout.setOnClickListener() {
            editor.putBoolean("IsLoggedIn", false)
            editor.apply()
            startActivity(Intent(this@home, login::class.java))
            Toast.makeText(this, "logout successfull", Toast.LENGTH_SHORT).show()


        }






        binding.add.setOnClickListener(
        )
        {
            var title = binding.task.text.toString()
            var description = binding.des.text.toString()
            var modelTask = ModelTask()
            if (description.isEmpty() && title.isEmpty()) {

                binding.task.error = "Title cannot be empty"
                binding.des.error = "Description cannot be empty"

            } else {

                modelTask.title = binding.task.text.toString()
                modelTask.description = binding.des.text.toString()

                db.collection("Task").add(modelTask)
                    .addOnSuccessListener { documentreference ->


                        modelTask.taskId = documentreference.id


                        modelTask.userId = sharedPreferences.getString("userId", "").toString()



                        db.collection("Task").document(documentreference.id).set(modelTask)


                        Toast.makeText(this@home, "TaskADDED", Toast.LENGTH_SHORT).show()


                    }
                    .addOnFailureListener()
                    {
                        Toast.makeText(this@home, "Failed to task addition", Toast.LENGTH_SHORT)
                            .show()
                    }

            }

        }
        binding.show.setOnClickListener()
        {

            setAdapter()
        }


    }

    override fun ondeleteClick(modelChat: ModelTask) {
        modelChat.title = "mfjkef"
        Toast.makeText(this@home, modelChat.title, Toast.LENGTH_SHORT).show()

        db.collection("Task").document(modelChat.taskId).delete()
            .addOnSuccessListener()
            {
                Toast.makeText(this@home, "deleted", Toast.LENGTH_SHORT).show()
                setAdapter()
            }
            .addOnFailureListener {
                Toast.makeText(this@home, "something wrong", Toast.LENGTH_SHORT).show()

            }
    }

    override fun oneditclick(modelChat: ModelTask) {
        val dialog = Dialog(this@home)
        dialog.setContentView(R.layout.activity_update_task)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        var title = dialog.findViewById<EditText>(R.id.editTextTitle)
        var description = dialog.findViewById<EditText>(R.id.editTextDescription)
        var update = dialog.findViewById<Button>(R.id.update_task)
        var cancel = dialog.findViewById<Button>(R.id.cancel_task)

        title.setText(modelChat.title.toString())
        description.setText(modelChat.description.toString())

        cancel.setOnClickListener()
        {
            dialog.dismiss()
        }

        update.setOnClickListener(
        )
        {


            modelChat.title=title.text.toString()
            modelChat.description=description.text.toString()


            db.collection("Task").document(modelChat.taskId).set(modelChat)
                .addOnSuccessListener() {
                    Toast.makeText(this, "update successfull", Toast.LENGTH_SHORT).show()
                    setAdapter()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show()
                }
        }

        dialog.show()
    }

    override fun onItemClick(modelChat: ModelTask) {
        startActivity(
            Intent(this@home, MainActivity::class.java).putExtra(
                "modelchat",
                modelChat.toString()
            )
        )
    }


    fun setAdapter() {
        db.collection("Task")
            .whereEqualTo("userId", sharedPreferences.getString("userId", "").toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    var taskList = ArrayList<ModelTask>()
                    for (task in task.result) {
                        val modelTask = task.toObject(ModelTask::class.java)
                        taskList.add(modelTask)

                    }


                    binding.recycle.layoutManager = LinearLayoutManager(this)
                    binding.recycle.adapter = Adopter_list(this, taskList, this@home)


                }
            }
    }
}




