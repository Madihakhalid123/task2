package com.example.shopingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopingapp.databinding.ActivityLoginBinding
import com.example.shopingapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.protobuf.Empty

class MainActivity : AppCompatActivity() ,Adopter_list.OnItemClickListener{
    var db = Firebase.firestore
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()




        binding.retreve.setOnClickListener()
        {
            db.collection("Task").whereEqualTo("userId",sharedPreferences.getString("userId","").toString())
                .get()
                .addOnCompleteListener {
                    task->
if(task.isSuccessful)
{

    var taskList=ArrayList<ModelTask>()
    for(task in task.result)
    {
        val modelTask = task.toObject(ModelTask::class.java)

taskList.add(modelTask)
        Toast.makeText(this@MainActivity, modelTask.title.toString(), Toast.LENGTH_SHORT).show()


    }

    Toast.makeText(this@MainActivity, taskList.size.toString(), Toast.LENGTH_SHORT).show()

   binding.recycle.layoutManager= LinearLayoutManager(this)
    binding.recycle.adapter=Adopter_list(this,taskList,this@MainActivity)


}
                }

        }





        binding.task.setOnClickListener(
        )
        {

            var modelTask=ModelTask()

            db.collection("Task").add(modelTask)
                .addOnSuccessListener {
                        documentreference->


                    modelTask.taskId=documentreference.id




                    modelTask.userId = sharedPreferences.getString("userId", "").toString()



                    db.collection("Task").document(documentreference.id).set(modelTask)


                    Toast.makeText(this@MainActivity, "TaskADDED", Toast.LENGTH_SHORT).show()


                }
                .addOnFailureListener()
                {
                    Toast.makeText(this@MainActivity, "Failed to task addition", Toast.LENGTH_SHORT).show()
                }

        }



    }

    override fun ondeleteClick(modelChat: ModelTask) {

    }

    override fun oneditclick(modelChat: ModelTask) {

    }

    override fun onItemClick(modelChat: ModelTask) {

    }
}






