package com.example.candy_crush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.webkit.DownloadListener;

import java.util.ArrayList;
import java.util.Random;

// This is the ”game engine ".
public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    Canvas canvas; //can i set it as global reference?
    ArrayList<Bitmap> bits = new ArrayList<>();
    Bitmap candyOrange = BitmapFactory. decodeResource ( getResources () , R. drawable.c_orange);
    Bitmap candyYellow = BitmapFactory. decodeResource ( getResources () , R. drawable.c_yellow);
    Bitmap candyRed = BitmapFactory. decodeResource ( getResources () , R. drawable.c_red);
    Bitmap candyBlue = BitmapFactory. decodeResource ( getResources () , R. drawable.c_blue);
    Bitmap candyGreen = BitmapFactory. decodeResource ( getResources () , R. drawable.c_green);
    Bitmap candyPurple = BitmapFactory. decodeResource ( getResources () , R. drawable.c_purple);

    float x1,x2,y1,y2;
    int row1,row2,col1,col2;
    int direction;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;


    int[][] candies = new int[10][10];

    public BoardView(Context context) {
        super(context);
        //Notify the surface holder that you'd like to recieve SurfaceHolder callbacks
        getHolder().addCallback(this);
        setFocusable(true);  //Very important
        //TODO: initialize game static variables and the game board variable
        bits.add(candyOrange);
        bits.add(candyYellow);
        bits.add(candyRed);
        bits.add(candyBlue);
        bits.add(candyGreen);
        bits.add(candyPurple);

        //rand candymap
        Random rand = new Random();
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                candies[i][j] = rand.nextInt(5);
            }
        }
        //DON"T RENDER THE GAME YET

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {    //? Surface was already locked
        // Construct game initial state

        //Rect r = new Rect(300,300,1100,1100);
        canvas = holder.lockCanvas(); //create a new canvas and lock its existance
        canvas.drawColor(Color.TRANSPARENT);
        //TODO: where is canvas located?

        onDraw(canvas);
        //Create the Candy board
        //Initialize the board with random candies
        holder.unlockCanvasAndPost(canvas);

        return;
    }


    //the difference?
    public void onDraw(Canvas c){     //canvas: (10*100) * (12*100)
        //draw()has done everything back needed
        for(int i=0;i<10;i++) {     //i:row
            for(int j=0;j<10;j++) { //j:col
                int type = candies[i][j];
                Rect dst = new Rect(j*100, i*100, 100+j*100, 100+i*100);
                c.drawBitmap(bits.get(type), null, dst, null);
                System.out.print(type+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Called in response to changes in the surface.

        // Write code here to do the necessary tasks such as adding new random candies from the top
        // of the board when the dimensions of the board change on eliminating the matched candies
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        //This is called immediately before a surface is being destroyed.
        //Write code here that needs to be executed just before the surface is destroyed.

    }

    public boolean onTouchEvent(MotionEvent e) {
        // Update game state in response to events:
        // touchdown, touchup, and touchmove.
        // Perform operations to check if the touch event is a valid move
        // If the move is valid , take the necessary actions such as removing the matched candies
        // and moving the candies above the eliminated row/ column to their appropriate positions

        //Canvas canvas = getHolder().lockCanvas();   //Surface was already locked???s
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            x1 = e.getX();
            y1 = e.getY();
            row1 = (int)y1/100;
            col1 = (int)x1/100;
            System.out.println("Click down coordinate ("+x1+","+y1+")\n"+"row:"+row1+" col:"+col1);
            return true;
        }
        else if(e.getAction() == MotionEvent.ACTION_UP){
            x2 = e.getX();
            y2 = e.getY();
            row2 = (int)y2/100;
            col2 = (int)x2/100;
            System.out.println("Click up coordinate ("+x2+","+y2+")\n"+"row:"+row2+" col:"+col2);
            if(row2-row1>0&&row2-row1<2){
                if(col1==col2) {
                    direction = DOWN;
                    swap(candies,row1,col1,row2,col2);
                }
            }
            else if(row2-row1<0&&row2-row1>-2){
                if(col1==col2) {
                    direction = UP;
                    swap(candies,row1,col1,row2,col2);
                }
            }
            else if(col2-col1>0&&row2-row1<2){
                if(row1==row2) {
                    direction = RIGHT;
                    swap(candies,row1,col1,row2,col2);
                }
            }
            else if(col2-col1<0&&row2-row1>-2) {
                if (row1 == row2) {
                    direction = LEFT;
                    swap(candies, row1, col1, row2, col2);
                }
            }
            canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE);
            onDraw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
            return true;
        }
        //TODO: complete the touch event
        return false;
    }

    public void swap(int[][] a,int x1,int y1,int x2,int y2){
        int temp = a[x1][y1];
        a[x1][y1] = a[x2][y2];
        a[x2][y2] = temp;
        System.out.print("In swap: \n");
        for(int i=0;i<10;i++) {     //i:row
            for(int j=0;j<10;j++) { //j:col
                int type = candies[i][j];

                System.out.print(type+" ");
            }
            System.out.println();
        }
        System.out.println("__________________");
    }


}
