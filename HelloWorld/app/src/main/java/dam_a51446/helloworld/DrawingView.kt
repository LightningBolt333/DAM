package dam_a51446.helloworld

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var path = Path() //path que guarda as linhas que o user desenha
    private var paint = Paint() //define como as linhas são

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.isAntiAlias = true
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) { //onDraw é chamado quando a view precisa de ser desenhada
        super.onDraw(canvas)
        canvas.drawPath(path, paint) //desenha de acordo com o definido, no canvas
    }

    override fun onTouchEvent(event: MotionEvent): Boolean { //deteta quando o user toca no ecrã

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y) //quando o dedo toca no ecra
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y) //quando o dedo se mexe
        }

        invalidate() // pede ao android para redesenhar a view
        return true
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.WHITE)

        draw(canvas)
        return bitmap
    }

    fun clearCanvas(){
        path.reset()
        invalidate()
    }
}