package com.example.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(SMS_RECEIVED)) {

            Bundle pudsBundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String str = "";
            String msg_from = "";

            if (pudsBundle != null) {

                Object[] pdus = (Object[]) pudsBundle.get("pdus");

                msgs = new SmsMessage[pdus.length];

                for (int i=0; i<msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    str += msgs[i].getMessageBody();
                    msg_from = msgs[i].getOriginatingAddress();
                }

                Intent smsIntent = new Intent(context, MainActivity.class);

                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                smsIntent.putExtra("MessageNumber", msg_from);

                smsIntent.putExtra("Message", str);

                context.startActivity(smsIntent);

                Toast.makeText(context, "SMS Received From :" + msg_from + "\n" +
                        msgs.toString(), Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(context, "Forgot", Toast.LENGTH_SHORT).show();
        }

    }
}
