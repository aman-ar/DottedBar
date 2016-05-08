package com.example.amanarora.dottedbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public DottedBar dotBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotBar = (DottedBar) findViewById(R.id.dottedBar);
    }

    public void viewTouched(View view)
    {
        dotBar.resetAnimation();

    }

    public void hideButtonClicked(View view)
    {
        if(dotBar.getVisibility() == View.VISIBLE)
        {

            dotBar.setVisibility(View.INVISIBLE);
            dotBar.stopAnimation();

        }
        else
        {
            Toast.makeText(this, "Already Invisible", Toast.LENGTH_LONG).show();
        }
    }

    public void showButtonClicked(View view)
    {
        if(dotBar.getVisibility() == View.INVISIBLE)
        {
            dotBar.setVisibility(View.VISIBLE);
            dotBar.resetAnimation();
        }
        else
        {
            Toast.makeText(this, "Already Visible", Toast.LENGTH_LONG).show();
        }
    }
}
