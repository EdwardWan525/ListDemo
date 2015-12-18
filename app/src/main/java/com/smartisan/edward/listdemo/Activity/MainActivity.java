package com.smartisan.edward.listdemo.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.smartisan.edward.listdemo.Adapter.ListDemoAdapter;
import com.smartisan.edward.listdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.smartisan.edward.listdemo.provider";
    private static final Uri URI = Uri.parse("content://"+AUTHORITY+"/list");
    private ListView mListView;
    private ListDemoAdapter mAdapter;
    public List<Map<String,String>> queryResult;
    private String title = null;
    private String author = null;
    private String subject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setActionBar();

        initView();

    }
    @Override
    protected void onResume(){
        super.onResume();
        queryResult.clear();
        queryResult.addAll(queryListItem());
        mAdapter.notifyDataSetChanged();

    }
    private void initView(){
        mListView = (ListView)findViewById(R.id.listview);
        queryResult = queryListItem();
        mAdapter = new ListDemoAdapter(this,queryResult,R.layout.list_item,new String[]{"title","subject"},
                new int[]{R.id.list_item_title,R.id.list_item_subject});
        mListView.setAdapter(mAdapter);


        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialog = inflater.inflate(R.layout.add_item_layout,null);
        final MaterialDialog mMaterialDialog = new MaterialDialog(MainActivity.this).setView(dialog);
        mMaterialDialog.setTitle("添加一个Item");
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText setTitle = (EditText)dialog.findViewById(R.id.set_title);
                EditText setAuthor = (EditText)dialog.findViewById(R.id.set_author);
                EditText setSubject = (EditText)dialog.findViewById(R.id.set_subject);
                if(setTitle.getText() != null && setAuthor.getText() != null
                        && setSubject.getText().toString() != null){
                    addItem(setTitle.getText().toString(),setAuthor.getText().toString(),
                            setSubject.getText().toString());
                    setTitle.setText("");
                    setAuthor.setText("");
                    setSubject.setText("");
                    mMaterialDialog.dismiss();
                }else {
                    mMaterialDialog.setTitle("输入有空");
                }

            }
        });
        mMaterialDialog.setNegativeButton("cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.show();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("_id",queryResult.get(position).get("_id"));
                bundle.putString("title",queryResult.get(position).get("title"));
                bundle.putString("author",queryResult.get(position).get("author"));
                bundle.putString("subject",queryResult.get(position).get("subject"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getContentResolver().delete(URI,"_id="+queryResult.get(position).get("_id"),null);
                queryResult.clear();
                queryResult.addAll(queryListItem());
                mAdapter.notifyDataSetChanged();
                return true;
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
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String subject = cursor.getString(cursor.getColumnIndex("subject"));
            listitem.put("subject",subject);
            listitem.put("author",author);
            listitem.put("title",title);
            listitem.put("_id",id);
            listitems.add(listitem);
        }
        cursor.close();
        return listitems;
    }

    private void addItem(String title,String author,String subject){
        ContentValues values = new ContentValues();
        values.put("_id", (byte[]) null);
        values.put("title",title);
        values.put("author",author);
        values.put("subject",subject);
        getContentResolver().insert(URI,values);
        //in order to invalidate listview,queryResult must point to the same memory
        queryResult.clear();
        queryResult.addAll(queryListItem());
        mAdapter.notifyDataSetChanged();

    }

}
