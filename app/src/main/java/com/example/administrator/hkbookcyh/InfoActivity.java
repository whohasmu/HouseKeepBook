package com.example.administrator.hkbookcyh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    TextView won_TV;
    TextView kind_TV;
    ImageView kind_IV;
    Button info_delete_BT;
    ImageView back_to_list_IV;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);



        won_TV = findViewById(R.id.won_TV);
        kind_TV = findViewById(R.id.kind_TV);
        kind_IV = findViewById(R.id.kind_IV);
        back_to_list_IV = findViewById(R.id.back_to_list);
        info_delete_BT = findViewById(R.id.info_delete_BT);


        dbManager = new DBManager(InfoActivity.this, "items.db", null, 1);



        Intent intent = getIntent();
        final int key = intent.getIntExtra("key",0);
        Integer won = intent.getIntExtra("won",0);
        int kind = intent.getIntExtra("kind",0);



        won_TV.setText(won.toString());

        if(kind==0) {
            kind_TV.setText(R.string.income);
            kind_IV.setImageResource(R.drawable.shape_oval);
        }else if(kind==1) {
            kind_TV.setText(R.string.outgoing);
            kind_IV.setImageResource(R.drawable.shape_oval2);
        }

        info_delete_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("DEL",1);
                dbManager.deleteItem(key);
                setResult(RESULT_OK,intent);
                finish();



            }
        });

        back_to_list_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


}
