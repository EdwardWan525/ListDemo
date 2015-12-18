package com.smartisan.edward.listdemo.Activity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.smartisan.edward.listdemo.R;

public class DetailActivity extends AppCompatActivity {
    private static final String AUTHORITY = "com.smartisan.edward.listdemo.provider";
    private static final Uri URI = Uri.parse("content://"+AUTHORITY+"/list");
    private EditText detailTitle;
    private EditText detailSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        setActionBar();

        initView();
    }

    private void setActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView(){
        detailTitle = (EditText) findViewById(R.id.detail_title);
        detailSubject = (EditText) findViewById(R.id.detail_subject);
        final Bundle bundle = this.getIntent().getExtras();
        detailTitle.setText(bundle.getString("title"));
        detailSubject.setText(bundle.getString("subject"));
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("_id",bundle.getString("_id"));
                values.put("title",detailTitle.getText().toString());
                values.put("author",bundle.getString("author"));
                values.put("subject",detailSubject.getText().toString());
                getContentResolver().update(URI,values,"_id ="+bundle.getString("_id"),null);
                finish();
            }
        });
    }

}
