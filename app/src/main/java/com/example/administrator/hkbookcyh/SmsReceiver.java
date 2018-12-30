package com.example.administrator.hkbookcyh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Calendar;



public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for (int i = 0 ; i < messages.length ; i++) {
                String format = bundle.getString("format");
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i],format);
            }

            String message = smsMessage[0].getMessageBody().toString();

            if (message.contains("현대카드 M2")) {
                String[] tmpStr = message.split("\n");
                String[] tmpPrice = tmpStr[2].split("원");
                String price = tmpPrice[0];
                price = price.replace(",","");
                Integer i_price = Integer.parseInt(price);

                Calendar calendar = Calendar.getInstance();
                Integer year = calendar.get(Calendar.YEAR);
                Integer month = calendar.get(Calendar.MONTH)+1;
                Integer day = calendar.get(Calendar.DAY_OF_MONTH);

                Item item = new Item(-1,year,month,day,i_price,1);

                ItemAddEvent itemAddEvent = new ItemAddEvent(item);
                BusProvider.getInstance().getBus().post(itemAddEvent);

                /*DBManager dbManager = new DBManager(context,"items.db",null,1);
                dbManager.insertItem(year,month,day,i_price,1);*/

            }
        }
    }
}
