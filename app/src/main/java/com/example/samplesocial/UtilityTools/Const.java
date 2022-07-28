package com.example.samplesocial.UtilityTools;


import com.example.samplesocial.Retrofit.Constant;

/**
 * Created by Anil on 12/3/2021.
 */
public class Const {
    //EndPoint
    public static final String login = "login";
    //live
    //Environment  **NOTE**  Change "Live" With "Debug"  When Going Live

    public static String Development = Constant.Debug;

    public interface URL {
        //http://sawit.dollopinfotech.com/ http://192.168.1.231/BitBucketProjects/sawitweb/update_user_profile
        //  String HOST_URL = "http://3.19.123.194//sawit.dollopinfotech.com/";
        //  String IMAGE_URL = "http://3.19.123.194//sawit.dollopinfotech.com/";
        String HOST_URL = "https://enwdtech.com/v1/";
        String term_condition_web = "https://enwdtech.com/v1/term_condition_web";
        String faq_web = "https://enwdtech.com/v1/faq_web";
        //instagram
        String instaFeedBaseURL = "https://instafeed.me/rtmp/api?";
        String instaRtmpBaseURL = "rtmps://live-upload.instagram.com:443/rtmp/";
        String privacy_policy_web = "https://enwdtech.com/v1/privacy_policy_web";
        String IMAGE_URL = "https://enwdtech.com/";

        //Twitch urls
        String CLIENT_ID = "e0hrzzdor1mjhgc057pnzod7ilko3v";
        String CLIENT_SECRET = "0ctzb2tawcfmell1tluzmdeohv24n5";
        String REDIRECT_URI = "https://enwdtech.com/twitch_callback";
        String SCOPE = "channel:manage:broadcast+channel:read:stream_key";
        String AUTHURL = "https://id.twitch.tv/oauth2/authorize";
        String validateTwitch = "https://id.twitch.tv/oauth2/validate";
        String updateChannel = "https://api.twitch.tv/helix/channels?broadcaster_id=";
        String streamURlTwitch = "https://api.twitch.tv/helix/streams/key?broadcaster_id=";

        // String RTMPUrl = "rtmp://sfo.contribute.live-video.net/app/";
        String RTMPUrl = "rtmp://iad05.contribute.live-video.net/app/";
        //  let state = "Twitch\(Int(NSDate().timeIntervalSince1970))"

        //let state = "Twitch\(Int(NSDate().timeIntervalSince1970))"
        //        let authURLFull = TwitchConstants.AUTHURL + "?response_type=token&client_id=" + TwitchConstants.CLIENT_ID + "&scope=" + TwitchConstants.SCOPE + "&state=" + state + "&redirect_uri=" + TwitchConstants.REDIRECT_URI

        String SIGN_UP_URL = HOST_URL + "signup";
        //Twitter client

        String BASE_URL = "https://api.twitter.com/2/";
        String TWEETS_ENDPOINT = "tweets";
        String USERS_ENDPOINT = "users";
        String OAUTH2_ENDPOINT = "oauth2";
        String OAUTH2_TOKEN_ENDPOINT = "token";
        String OAUTH2_REVOKE_ENDPOINT = "revoke";
        String OAuthUrlMe = "https://api.twitter.com/2/users/me";
        String AUTH_BASE_URL = "https://api.twitter.com/oauth/";
        String REQUEST_TOKEN_ENDPOINT = "request_token";
        String ACCESS_TOKEN_ENDPOINT = "access_token";
        String ClientId = "eGlycE1McnFkSW9LQVJFWGJlcHA6MTpjaQ";
        String ClientSecrete = "t6p6Fr-YmmbMsAw_u4uCb3Sh_2woOae8xR93o39NPN5RW9g9ob";


    }
}