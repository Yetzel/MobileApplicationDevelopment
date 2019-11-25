package com.mhcibasics.eiuhr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context ctx = null;
    private ListView lvTimers;
    private List<Countdown> lstTimers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = getApplication();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvTimers = (ListView) findViewById(R.id.lvItems);
        lstTimers = new ArrayList<>();
        lstTimers.add(new Countdown("A", System.currentTimeMillis() + 10000));
        lstTimers.add(new Countdown("B", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("C", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("D", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("E", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("F", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("G", System.currentTimeMillis() + 30000));
        lstTimers.add(new Countdown("H", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("I", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("J", System.currentTimeMillis() + 40000));
        lstTimers.add(new Countdown("K", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("L", System.currentTimeMillis() + 50000));
        lstTimers.add(new Countdown("M", System.currentTimeMillis() + 60000));
        lstTimers.add(new Countdown("N", System.currentTimeMillis() + 20000));
        lstTimers.add(new Countdown("O", System.currentTimeMillis() + 10000));

        lvTimers.setAdapter(new CountdownAdapter(MainActivity.this, lstTimers));

        FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.ic_add_timer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, AddTimerActivity.class);
                startActivity(intent);
            }
        });
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
}
