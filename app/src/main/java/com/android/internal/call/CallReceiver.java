package com.android.internal.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Method;
import java.util.List;


import static android.content.Context.TELEPHONY_SERVICE;



public class CallReceiver  extends BroadcastReceiver {
    private static final String TAG = "BroadcastReceiver";
    Context mContext;
    String incoming_nr;
    private int prev_state;
    RecyclerView recyclerView;
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE); //TelephonyManager object
        CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager
        Bundle bundle = intent.getExtras();
        String phoneNr= bundle.getString("incoming_number");
        Log.v(TAG, "phoneNr: "+phoneNr);
        mContext=context;
    }
    /* Custom PhoneStateListener */
    public class CustomPhoneStateListener extends PhoneStateListener {

        private static final String TAG = "CustomPhoneState";

        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            System.out.println(incomingNumber.length());
            if(incomingNumber!=null&&incomingNumber.length()>0) {
                incoming_nr=incomingNumber;
                send(incoming_nr);
                Log.d("phoneOOOOOOOOOOOOOOOOOO",incoming_nr);
            }
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(TAG, "CALL_STATE_RINGING");
                    prev_state=state;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d(TAG, "CALL_STATE_OFFHOOK");
                    prev_state=state;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d(TAG, "CALL_STATE_IDLE==>"+incoming_nr);
                    if((prev_state==TelephonyManager.CALL_STATE_OFFHOOK)){
                        prev_state=state;
                        //Answered Call which is ended
                        Toast.makeText(mContext, "answered call end", Toast.LENGTH_LONG).show();
                    }
                    if((prev_state==TelephonyManager.CALL_STATE_RINGING)){
                        prev_state=state;
                        //Rejected or Missed call
                        Log.v(TAG,incoming_nr);
//                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
//                        smsIntent.setType("vnd.android-dir/mms-sms");
//                        smsIntent.putExtra("address",incoming_nr);
//                        smsIntent.putExtra("sms_body","The person you are trying to call is busy, Please try later");
//                        smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(smsIntent);
                    }
                    break;
            }
        }
        void send(String number)
        {
            try {
                SmsManager.getDefault().sendTextMessage(number, null,
                        "Hello SMS!", null, null);
                Toast.makeText(mContext,"Message Sent",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
