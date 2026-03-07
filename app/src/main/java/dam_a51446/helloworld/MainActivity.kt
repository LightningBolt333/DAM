package dam_a51446.helloworld

import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println(this@MainActivity.localClassName + getString(R.string.activity_oncreate_msg))
        println(getString(R.string.activity_oncreate_msg, this@MainActivity.localClassName))

        val drawingView = findViewById<DrawingView>(R.id.drawingView)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val bitmap = drawingView.getBitmap()

            MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Drawing", "My sketch")
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

            drawingView.clearCanvas()
        }
    }
}