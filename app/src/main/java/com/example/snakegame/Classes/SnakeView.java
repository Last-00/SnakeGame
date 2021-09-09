package com.example.snakegame.Classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.snakegame.Activities.MainActivity;
import com.example.snakegame.Enums.TitleType;

public class SnakeView extends View {

    private Paint mPaint = new Paint();
    private TitleType SnakeViewMap [] [];

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void setSnakeViewMap(TitleType[] [] map){this.SnakeViewMap = map;}

    @Override
    // function draws and creates the elements of map as circle and then gives it the color according to the type of element
    public void onDraw(Canvas canvas) {
        if(SnakeViewMap != null){
            float titleSizeX = canvas.getWidth()/SnakeViewMap.length;
            float titleSizeY = canvas.getHeight()/SnakeViewMap[0].length;
            float circleRadius = Math.min(titleSizeX,titleSizeY)/2;
            for(int x = 0; x < SnakeViewMap.length;x++){
                for(int y = 0; y < SnakeViewMap[x].length;y++){
                    switch (SnakeViewMap[x] [y]){
                        case Nothing:
                            mPaint.setColor(Color.WHITE);
                            break;
                        case Crossing:
                            mPaint.setColor(Color.BLACK);
                            break;
                        case Wall:
                            mPaint.setColor(Color.GRAY);
                            break;
                        case SnakeHead:
                            mPaint.setColor(Color.BLUE);
                            break;
                        case SnakeTail: // gives the color according to the choice of player in settings
                            {
                                if(MainActivity.sp1.getString("snakeColor",null) == null) mPaint.setColor(Color.GREEN);
                                if (MainActivity.sp1.getString("snakeColor",null) != null)
                                {
                                   if(MainActivity.sp1.getString("snakeColor",null).length() == 5) mPaint.setColor(Color.GREEN);
                                   if(MainActivity.sp1.getString("snakeColor",null).length() == 6) mPaint.setColor(Color.YELLOW);
                                   if(MainActivity.sp1.getString("snakeColor",null).length() == 3) mPaint.setColor(Color.RED);
                                }
                            }
                            break;
                        case Apple:
                            mPaint.setColor(Color.RED);
                            break;
                    }
                    canvas.drawCircle(x*titleSizeX + circleRadius/2 + titleSizeX/2,y*titleSizeY+circleRadius/2+titleSizeY/2,circleRadius,mPaint);
                }
            }
        }
    }
}
