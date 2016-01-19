package com.plugins;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sglickfield on 11/4/2015.
 */

public class PhoneStateListenerPlugin extends CordovaPlugin {

    TelephonyManager mTelephonyManager;

    boolean mOutgoingCallReceiverRegistered = false;
    CallbackContext mCallbackContext;
//    private TimerTask mTimerTask;
//    private Timer mTimer;
//    private final Handler mHandler = new Handler();

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        Log.i("PhoneStateListenerPlugin", "Plugin Called");

        Context context = this.cordova.getActivity().getApplicationContext();

        if (!mOutgoingCallReceiverRegistered){
            context.registerReceiver(new OutgoingCallBroadcastReceiver(), new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
            mOutgoingCallReceiverRegistered = true;
        }
        if (mTelephonyManager == null) {
            // TELEPHONY MANAGER class object to register one listner
            mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //Create Listener
            // MyPhoneStateListener PhoneListener = new MyPhoneStateListener(this.getApplicationContext());
            MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener(context);

            // Register listener for LISTEN_CALL_STATE
            mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        // Sarah G: Limitation - the mCallbackContext will be overwritten with each call to execute.
        // If we're doing multiple things here with different callbacks, we need to have one callback per "thing" we're doing

        mCallbackContext = callbackContext;
      //  PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
      //  result.setKeepCallback(true);
      //  mCallback.sendPluginResult(result);
        return true;
    }

    /*    private void sendNote(PhoneStateTracker.PhoneState stateTracker) {
            String phoneNumber = PhoneStateTracker.getPhoneStateTracker().getPhoneNumber();
            Log.i("PhoneStateListenerPlugin", "in SendNote() with phone number " + phoneNumber);
            if (phoneNumber == null) {
                return;
            }

            String personID = BullhornAPI.searchPersonIDByPhoneNumber(phoneNumber);
            String action = PhoneStateTracker.getPhoneStateTracker().getStateString();

            try {

                JSONObject addNotePostData = new JSONObject();
                addNotePostData.put("personID", personID);
                addNotePostData.put("action", action);
                addNotePostData.put("comments", ""); // TODO: Pop up a screen for them to add in a note

                BullhornAPI.addNote(addNotePostData.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    */

    public class OutgoingCallBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            Log.i("PhoneStateListenerPlugin", "ACTION:" + intent.getAction());

            if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
                final String originalNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.i("PhoneStateListenerPlugin", "outgoing,ringing:" + originalNumber);
            }
        }
    }

    public class MyPhoneStateListener extends PhoneStateListener {

        Context mContext;

        public MyPhoneStateListener(Context c) {
            mContext = c;
        }

        // LOG PATTERNS AFTER RUNNING A COUPLE OF TESTS
        // NOTES:
        // 1. At what point do we lose the focus, and our phone capture no longer works?
        // 1a. Is there some way to solve for this? (possibilities - use a Service, or launch our app on startup
        // 2. We need to call execute from the HTML as soon as the page loads, so that these objects get created.  Otherwise we won't have a TelephonyManager
        // 3. When we receive an outgoing call action - we don't know if that call has been answered or not

 /*       01-18 22:25:07.445  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ New Phone Call Event. Incomming Number : 13055272920
                01-18 22:25:19.885  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State IDLE
        01-18 22:25:33.289  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ New Phone Call Event. Incomming Number : 13055272920
                01-18 22:25:35.844  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State OFFHOOK
        01-18 22:25:40.240  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State IDLE
        01-18 22:25:48.518  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State OFFHOOK
        01-18 22:26:04.608  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State IDLE
        01-18 22:26:08.291  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State OFFHOOK
        01-18 22:26:33.024  27297-27297/com.ionicframework.pulsemobile496191 I/PhoneStateListenerPlugin﹕ Phone State IDLE
  */

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("MyPhoneListener", state + "   incoming no:" + incomingNumber);
            //       String intentExtraNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            if (state == TelephonyManager.CALL_STATE_RINGING) {

                String msg = " New Phone Call Event. Incomming Number : " + incomingNumber;
                Log.i("PhoneStateListenerPlugin", msg);
//                int duration = Toast.LENGTH_LONG;
//                Toast toast = Toast.makeText(mContext, msg, duration);
//                toast.show();
                //PhoneStateTracker.getPhoneStateTracker().setState(PhoneStateTracker.PhoneState.INCOMING, incomingNumber);
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                String msg = "Phone State IDLE";
                Log.i("PhoneStateListenerPlugin", msg);

                // Phone was just hung up
                //sendNote(PhoneStateTracker.getPhoneStateTracker().getState());

                //PhoneStateTracker.getPhoneStateTracker().setState(PhoneStateTracker.PhoneState.IDLE, "");
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                String msg = "Phone State OFFHOOK";
                Log.i("PhoneStateListenerPlugin", msg);

                // If we go into the CALL_STATE_OFFHOOK state, and the previous state wasn't CALL_STATE_RINGING - then this is an outbound call
                //PhoneStateTracker.PhoneState tmpState = PhoneStateTracker.getPhoneStateTracker().getState();
                //Log.d("PhoneStateListenerPlugin", "Old State = " + PhoneStateTracker.getPhoneStateTracker().getStateString());
            }
        }
    }

}

/*
Request should take this format:

PUT http://dschulte-backend.bh-bos2.bullhorn.com:8181/rest-services/5zio9/entity/Note

{
"personID": 1234,
"action": "Inbound Call",
"comment":"This is a comment"
}
*/


/*
Request should take this format:

PUT http://dschulte-backend.bh-bos2.bullhorn.com:8181/rest-services/5zio9/entity/Note

{
"personID": 1234,
"action": "Inbound Call",
"comment":"This is a comment"
}
*/
