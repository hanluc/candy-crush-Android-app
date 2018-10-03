package com.example.candy_crush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.frame_board);
        //board = (BoardView)findViewById(R.id.boardView);
        //setContentView(new BoardView(this));
        BoardView board = new BoardView(this);
        frameLayout.addView(board);

    }



}
