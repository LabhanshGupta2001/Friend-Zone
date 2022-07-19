package com.example.samplesocial.UtilityTools;

public interface ResultOfStreamBroadcast {
    void onStreamId(String streamid);

    void onBroadCastId(String broadCastid);

    void onFailed(String message);
    //  void onInngestionUrl(String ingestionUrl);
}
