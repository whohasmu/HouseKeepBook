package com.example.administrator.hkbookcyh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    EditText money_ET;
    Button deposit_BT;
    Button withdraw_BT;
    TextView Text_date;

    Button close_BT;

    DBManager dbManager;

    Integer year;
    Integer month;
    Integer day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        money_ET = findViewById(R.id.money_ET);
        deposit_BT = findViewById(R.id.deposit_BT);
        withdraw_BT =findViewById(R.id.withdraw_BT);
        close_BT = findViewById(R.id.close_BT);
        Text_date = findViewById(R.id.Text_date);

        final Animation ButtonAnim = AnimationUtils.loadAnimation(InputActivity.this, R.anim.button);


        dbManager = new DBManager(InputActivity.this, "items.db", null, 1);
        Intent intent = getIntent();
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day = intent.getIntExtra("day",0);
        setDate(year,month+1,day);



        deposit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int won = Integer.parseInt(money_ET.getText().toString());

                dbManager.insertItem(year, month+1, day, won, 0 );

                deposit_BT.startAnimation(ButtonAnim);
                money_ET.setText("");





                /*Intent intent = getIntent();
                intent.putExtra("inputWon",money);
                intent.putExtra("inputOper",0);
                setResult(RESULT_OK,intent);*/



            }
        });

        withdraw_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int won = Integer.parseInt(money_ET.getText().toString());

                dbManager.insertItem(year, month+1, day, won, 1 );

                withdraw_BT.startAnimation(ButtonAnim);
                money_ET.setText("");


                /*Intent intent = getIntent();
                intent.putExtra("inputWon",money);
                intent.putExtra("inputOper",1);
                setResult(RESULT_OK,intent);*/

            }
        });

        close_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void setDate(Integer year, Integer month, Integer day){
        Text_date.setText(year+"/"+month+"/"+day);
    }

}
