package dev.android.studydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dev.android.studydemo.combinationView.CombinationActivity;
import dev.android.studydemo.recyclerview.one.LoadMoreAndEmptyActivity;
import dev.android.studydemo.recyclerview.two.DifferenceRecycleActivity;
import dev.android.studydemo.view.calendar.CalendarViewActivity;
import dev.android.studydemo.view.line.BrokenLineDiagramActivity;
import dev.android.studydemo.view.wave.WaveActivity;

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
        findViewById(R.id.tv_demo_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));
            }
        });
        findViewById(R.id.tv_demo_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BrokenLineDiagramActivity.class));
            }
        });
        findViewById(R.id.tv_demo_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WaveActivity.class));
            }
        });
        findViewById(R.id.tv_demo_six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CombinationActivity.class));
            }
        });
    }
}
