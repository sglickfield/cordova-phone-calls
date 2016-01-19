package com.plugins;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

//    final private CallbackContext mCallbackContext;
//    private TimerTask mTimerTask;
//    private Timer mTimer;
//    private final Handler mHandler = new Handler();

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        Log.i("PhoneStateListenerPlugin", "Plugin Called");

        Context context = this.cordova.getActivity().getApplicationContext();
        
      if (mTelephonyManager == null) {
          // TELEPHONY MANAGER class object to register one listner
          mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
          //Create Listener
          // MyPhoneStateListener PhoneListener = new MyPhoneStateListener(this.getApplicationContext());
          MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener(context);

          // Register listener for LISTEN_CALL_STATE
          mTelephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
      }

        return true;

        // Sarah G: Limitation - the mCallbackContext will be overwritten with each call to execute.
        // If we're doing multiple things here with different callbacks, we need to have one callback per "thing" we're doing

//         mCallbackContext = callbackContext;
        //PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        //result.setKeepCallback(true);
        //mCallback.sendPluginResult(result);
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
    public class MyPhoneStateListener extends PhoneStateListener {

        Context mContext;

        public MyPhoneStateListener(Context c) {
            mContext = c;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("MyPhoneListener", state + "   incoming no:" + incomingNumber);

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
