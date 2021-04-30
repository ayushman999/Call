package com.android.internal.call;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.Model.model;
import com.android.internal.adapter.ContactAdapter;
import com.android.internal.adapter.contactItemClicked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements contactItemClicked {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    RecyclerView recyclerView;
    Map<String, String> namePhoneMap = new HashMap<String, String>();
    ContactAdapter adapter;
    ArrayList<model> listItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        getPhoneNumbers();
        setlist();
    }


    //for permissions
    public static boolean hasPermissions(MainActivity context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    //for contactinfo
    private void getPhoneNumbers() {

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Loop Through All The Numbers
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            // Cleanup the phone number
            phoneNumber = phoneNumber.replaceAll("[()\\s-]+", "");

            Log.d("hhhhhhhhhhhhhhhhhhh", "fjsnl  " + name);
            namePhoneMap.put(name, phoneNumber);
            //listItem.add(name);
        }
        for (Map.Entry<String,String> entry : namePhoneMap.entrySet())
        {
            listItem.add(new model(entry.getKey(),namePhoneMap.get(entry.getKey()),0));
        }
        Collections.sort(listItem, new Comparator<model>() {
            @Override
            public int compare(model o1, model o2) {
                return o1.getContactName().compareTo(o2.getContactName());
            }
        });
        phones.close();
    }
    ListView listView;
    TextView textView;

    //setting listview for contacts
    void setlist(){
    recyclerView=(RecyclerView) findViewById(R.id.contactlist);
    RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
    recyclerView.setLayoutManager(manager);
    textView=(TextView) findViewById(R.id.textView);
    adapter=new ContactAdapter(listItem,  MainActivity.this);
    recyclerView.setAdapter(adapter);

}

    @Override
    public void onItemClicked(String item) {
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void plus(model m, int position) {
        int c=listItem.get(position).getPos()+1;
        listItem.get(position).setPos(c);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void minus(model m, int position) {
        int c=listItem.get(position).getPos()-1;
        listItem.get(position).setPos(c);
        adapter.notifyDataSetChanged();
    }
}
