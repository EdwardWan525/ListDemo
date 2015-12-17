package com.smartisan.edward.listdemo.Activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.smartisan.edward.listdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.smartisan.edward.listdemo.provider";
    private ListView mListView;
    private SimpleAdapter mAdapter;
    private ContentResolver mContenResolver;
    public List<Map<String,String>> queryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setActionBar();

        initView();

    }

    private void initView(){
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new SimpleAdapter(this,queryListItem(),R.layout.list_item,
                new String[]{"title","subject"},
                new int[]{R.id.list_item_title,R.id.list_item_subject});
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
//                Bundle bundle = new Bundle();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
    private void setActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private List<Map<String,String>> queryListItem(){
        List<Map<String,String>> listitems = new ArrayList<Map<String,String>>();
        Uri uri = Uri.parse("content://"+AUTHORITY+"/list");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);
        cursor.moveToLast();
        cursor.moveToNext();
        Map<String,String> listitem = null;
        while (cursor.moveToPrevious()){
            listitem = new HashMap<String, String>();
//            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
//            String author = cursor.getString(cursor.getColumnIndex("author"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            listitem.put("subject",subject);
//            listitem.put("author",author);
            listitem.put("title",title);
//            listitem.put("_id",id);
            listitems.add(listitem);
        }
        cursor.close();
        return listitems;
    }

}
