package com.carrey.recycleviewset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.carrey.recycleviewset.view.recycleview.adapter.ClassOffset;
import com.carrey.recycleviewset.view.recycleview.adapter.ItemViewDelegate;
import com.carrey.recycleviewset.view.recycleview.adapter.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        initData();
        initData2();
    }

    private void initData2() {
        RecyclerView view = findViewById(R.id.recycle);
        view.setLayoutManager(new LinearLayoutManager(this));
        MultiTypeAdapter adapter = new MultiTypeAdapter();
       adapter.register(Data.Data1.class,new  Delegate.Delegate1());
       adapter.register(Data.Data2.class,new  Delegate.Delegate3());
        adapter.setItems(getData());
        view.setAdapter(adapter);
    }

    private void initData() {

        RecyclerView view = findViewById(R.id.recycle);
        view.setLayoutManager(new LinearLayoutManager(this));
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(Data.Data1.class).into(
                new Delegate.Delegate1(),
                new Delegate.Delegate2())
                .withClassMapping(new ClassOffset<Data.Data1>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewDelegate<Data.Data1, ?>> index(int position, @NonNull Data.Data1 data1) {
                        if (position % 3 == 0 || position % 7 == 0) {
                            return Delegate.Delegate1.class;
                        }
                        return Delegate.Delegate2.class;
                    }
                });
        adapter.setItems(getData());
        view.setAdapter(adapter);

    }

    private List<?> getData() {
        List list = new ArrayList<>();
        for (int i = 0; i < 50 ;i++){
            if (i%3==0||i%4==0){
                list.add(new Data.Data2());
            }
            list.add(new Data.Data1());

        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items into the action bar if it is present.
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
}
