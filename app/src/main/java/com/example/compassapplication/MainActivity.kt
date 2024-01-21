package com.example.compassapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

//while implementing the sensor here we to add the SensorEventListener
class MainActivity : ComponentActivity() , SensorEventListener {

    var sensor:Sensor?=null
    var sensorManager:SensorManager?=null
    lateinit var compassImage:ImageView
    lateinit var rotationTV: TextView

    var currentDeg = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        compassImage = findViewById(R.id.imageView)
        rotationTV = findViewById(R.id.textView)

        //to keep track of the rotation of the compass

    }

    override fun onSensorChanged(event: SensorEvent?) {

        var degree = Math.round(event!!.values[0])
        Log.d(TAG,"OnSensorChanged:" + degree)
        rotationTV.text = degree.toString() + "degrees"
        var rotationAnimation = RotateAnimation(currentDeg,(-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)

        rotationAnimation.duration=210
        rotationAnimation.fillAfter= true
        compassImage.startAnimation(rotationAnimation)
        currentDeg = (-degree).toFloat()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    //Registering a listener for the sensor
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)
    }

    //unregistering the listener
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}