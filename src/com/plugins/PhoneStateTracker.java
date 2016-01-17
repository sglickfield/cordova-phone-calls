package com.bullhorn.src.bullhorndatacollector;

import java.util.HashMap;

/**
 * Created by sglickfield on 11/5/2015.
 * Singleton to store current state of phone, so that we know which action to associate to a note
 */
public class PhoneStateTracker {

    private static PhoneStateTracker mPhoneStateTracker = new PhoneStateTracker();

    public static enum PhoneState {INCOMING, IDLE, CALL_STATE_OFFHOOK, OUTGOING};
    private HashMap<PhoneState, String> phoneStateStringHashMap;
    private PhoneState mState;
    private String mPhoneNumber;

    private PhoneStateTracker(){
        phoneStateStringHashMap = new HashMap<PhoneState, String>();
        phoneStateStringHashMap.put(PhoneState.INCOMING,            "Inbound Call");
        phoneStateStringHashMap.put(PhoneState.IDLE,                "Idle");
        phoneStateStringHashMap.put(PhoneState.CALL_STATE_OFFHOOK,  "Phone In Use");
        phoneStateStringHashMap.put(PhoneState.OUTGOING,            "Outbound Call");
    }

    public static PhoneStateTracker getPhoneStateTracker(){
        return mPhoneStateTracker;
    }

    public PhoneState getState(){
        return mState;
    }

    public void setState(PhoneState state, String phoneNumber){
        mState = state;
        mPhoneNumber = phoneNumber;
    }

    public String getStateString(){
        return phoneStateStringHashMap.get(mState);
    }

    public String getPhoneNumber(){
        return mPhoneNumber;
    }

}
