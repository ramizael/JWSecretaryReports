package com.andresalcantar.jwsecretaryreports.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.andresalcantar.jwsecretaryreports.R;

public class ViewPublishers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publishers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewPublisherActivity(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openNewPublisherActivity(View view) {
        Intent intent = new Intent(this, NewPublisher.class);
        startActivity(intent);
    }

}
