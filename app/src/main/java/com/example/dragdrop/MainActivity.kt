package com.example.dragdrop

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llFirst=findViewById<LinearLayout>(R.id.llTop)
        val llSecond=findViewById<LinearLayout>(R.id.llBottom)

        llFirst.setOnDragListener(dragListener)
        llSecond.setOnDragListener(dragListener)

        val vFirst=findViewById<View>(R.id.dragView)

        vFirst.setOnLongClickListener {
            val clipText= "This is our clipdata text"
            val item=ClipData.Item(clipText)
            val mimeType= arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data= ClipData(clipText,mimeType,item)

            val dragShadowBuilder=View.DragShadowBuilder(it)
            it.startDragAndDrop(data,dragShadowBuilder,it,0)

            it.visibility= View.INVISIBLE
            true

        }
    }
    val dragListener = View.OnDragListener{view, event ->
         when(event.action){
             DragEvent.ACTION_DRAG_STARTED -> {
                 event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
             }
             DragEvent.ACTION_DRAG_ENTERED -> {
                 view.invalidate()
                 true
             }
             DragEvent.ACTION_DRAG_LOCATION -> true
             DragEvent.ACTION_DRAG_EXITED -> {
                 view.invalidate()
                 true
             }
             DragEvent.ACTION_DROP -> {
                 val item= event.clipData.getItemAt(0)
                 val dragData=item.text
                 Toast.makeText(this,dragData,Toast.LENGTH_SHORT).show()

                 view.invalidate()


                 val v = event.localState as View
                 val owner=v.parent as ViewGroup
                 owner.removeView(v)
                 val destination =view as LinearLayout
                 destination.addView(v)
                 v.visibility= View.VISIBLE
                 true

             }
              DragEvent.ACTION_DRAG_ENDED -> {
                  view.invalidate()
                  true
              }
             else -> false
         }
    }
}