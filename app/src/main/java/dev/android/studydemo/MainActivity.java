package dev.android.studydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dev.android.studydemo.recyclerview.one.LoadMoreAndEmptyActivity;
import dev.android.studydemo.recyclerview.two.DifferenceRecycleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_demo_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoadMoreAndEmptyActivity.class));
            }
        });
        findViewById(R.id.tv_demo_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DifferenceRecycleActivity.class));
            }
        });
    }
}
