package com.example.harsh.sports_counter;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int a_points=0,b_points=0;
    public void threePointer_a(View view)
    {
        a_points+=3;
        display_a(a_points);
    }
    public void threePointer_b(View view)
    {
        b_points+=3;
        display_b(b_points);
    }
    public void twoPointer_a(View view)
    {
        a_points+=2;
        display_a(a_points);
    }
    public void twoPointer_b(View view)
    {
        b_points+=2;
        display_b(b_points);
    }
    public void freeThrow_a(View view)
    {
        a_points++;
        display_a(a_points);
    }
    public void freeThrow_b(View view)
    {
        b_points++;
        display_b(b_points);
    }
    public void reset(View view)
    {
        a_points=0;
        b_points=0;
        display_a(a_points);
        display_b(b_points);
    }
    public void display_a(int points)
    {

        TextView t = (TextView) findViewById(R.id.team_a);
        t.setText(""+points);
    }
    public void display_b(int points)
    {

        TextView t = (TextView) findViewById(R.id.team_b);
        t.setText(""+points);
    }
}
