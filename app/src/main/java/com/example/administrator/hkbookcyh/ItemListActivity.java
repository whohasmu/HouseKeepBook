package com.example.administrator.hkbookcyh;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;


public class ItemListActivity extends AppCompatActivity {
    static int ADD = 0;
    static int DEL = 1;

    Bus bus = BusProvider.getInstance().getBus();
    ArrayList <Item> items = new ArrayList<>();
    ListView LV_item;
    ItemListAdapter itemListAdapter;
    Button add_BT;
    ImageView before_date_IV;
    ImageView next_date_IV;
    TextView Text_date;


    Integer year;
    Integer month;
    Integer day;

    DBManager dbManager;

    NotificationManager notifcationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*Intent intent = new Intent(ItemListActivity.this, InputActivity.class);
        startActivityForResult(intent, ADD);*/
        bus.register(ItemListActivity.this);

        before_date_IV = findViewById(R.id.before_date);
        next_date_IV = findViewById(R.id.next_date);
        LV_item = findViewById(R.id.LV_item);
        add_BT = findViewById(R.id.add_BT);
        Text_date = findViewById(R.id.Text_date);

        dbManager = new DBManager(ItemListActivity.this, "items.db", null, 1);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        setDate(year, month + 1 , day);

        items = dbManager.getItemList(year,month+1,day);
        itemListAdapter = new ItemListAdapter(items);
        LV_item.setAdapter(itemListAdapter);




        add_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemListActivity.this, InputActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("day",day);

                startActivityForResult(intent, ADD);


            }
        });

        Text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int inputyear, int inputmonth, int inputday) {
                        Log.d("cyh",year + " " + month + " " + day);
                        setDate(inputyear,inputmonth+1,inputday);
                        year = inputyear;
                        month = inputmonth;
                        day = inputday;
                        refreshList();

                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(ItemListActivity.this,dateSetListener,
                        cyear,cmonth,cday);
                dialog.show();
            }
        });


        before_date_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,day);
                calendar1.add(Calendar.DAY_OF_MONTH,-1);
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);
                setDate(year,month+1,day);
                refreshList();
            }
        });

        next_date_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,day);
                calendar1.add(Calendar.DAY_OF_MONTH,+1);
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);
                setDate(year,month+1,day);
                refreshList();
            }
        });


        LV_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ItemListActivity.this, InfoActivity.class);

                intent.putExtra("key",items.get(position).getId());
                intent.putExtra("won",items.get(position).getWon());
                intent.putExtra("kind",items.get(position).getKind());
                startActivityForResult(intent, DEL);


            }

        });


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(ItemListActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                .check();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==ADD){

                refreshList();

            }else if(requestCode==DEL){
                refreshList();



            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void setDate(Integer year, Integer month, Integer day){
        Text_date.setText(year+"/"+month+"/"+day);
    }
    public void refreshList() {
        items = dbManager.getItemList(year,month+1,day);
        itemListAdapter = new ItemListAdapter(items);
        LV_item.setAdapter(itemListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(ItemListActivity.this);
    }

    @Subscribe
    public void ItemAddEvent(ItemAddEvent event) {
        dbManager.insertItem(event.getItem().getYear(),event.getItem().getMonth(),
                event.getItem().getDay(),event.getItem().getWon(),1);
        refreshList();
        generatNotification();






    }
    public void generatNotification() {

        notifcationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        Context context = ItemListActivity.this;
        NotificationChannel notify = new NotificationChannel("channel_id", "channel_name", android.app.NotificationManager.IMPORTANCE_DEFAULT);


        notify.setDescription("channel description");
        notify.enableLights(true);
        notify.setLightColor(Color.GREEN);
        notify.enableVibration(true);
        notify.setVibrationPattern(new long[]{100, 200, 100, 200});
        notify.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notifcationManager.createNotificationChannel(notify);



    }



}
