package com.example.robottime

import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.example.robottime.data.AppDatabase
import com.example.robottime.data.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity:RobotActivity(), RobotLifecycleCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QiSDK.register(this,this)
        GlobalScope.launch {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

            //db.userDao().insertAll(User(0,"Kelvin", "Snepvangers"))
            Log.i("Test", db.userDao().findByName("Kelvin", "Snepvangers").firstName)
            textView.text = db.userDao().getAll()[0].firstName
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this,this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        // Create a new say action.
        val say = SayBuilder.with(qiContext) // Create the builder with the context.
            .withText("Hello human!") // Set the text to say.
            .build() // Build the say action.

        // Execute the action.
        say.run()
    }

    override fun onRobotFocusLost() {
        // The robot focus is lost.
    }

    override fun onRobotFocusRefused(reason: String) {
        // The robot focus is refused.
    }

}
