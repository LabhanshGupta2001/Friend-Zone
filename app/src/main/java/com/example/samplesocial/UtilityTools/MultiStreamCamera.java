/*
 * Copyright (C) 2021 pedroSG94.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.samplesocial.UtilityTools;

import android.media.MediaCodec;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import com.pedro.rtmp.flv.video.ProfileIop;
import com.pedro.rtmp.rtmp.RtmpClient;
import com.pedro.rtmp.utils.ConnectCheckerRtmp;

import java.nio.ByteBuffer;

/**
 * More documentation see:
 * {@link com.pedro.rtplibrary.base.Camera1Base}
 *
 * Created by pedro on 25/01/17.
 */

public class MultiStreamCamera extends Camera1Base {

  private final RtmpClient rtmpClient;
  private final RtmpClient rtmpClient1;

  public MultiStreamCamera(SurfaceView surfaceView, ConnectCheckerRtmp connectChecker) {
    super(surfaceView);
    rtmpClient = new RtmpClient(connectChecker);
    rtmpClient1 = new RtmpClient(connectChecker);
  }





  /**
   * H264 profile.
   *
   * @param profileIop Could be ProfileIop.BASELINE or ProfileIop.CONSTRAINED
   */
  public void setProfileIop(ProfileIop profileIop) {

    rtmpClient.setProfileIop(profileIop);
    rtmpClient1.setProfileIop(profileIop);
  }

  @Override
  public void resizeCache(int newSize) throws RuntimeException {
    rtmpClient.resizeCache(newSize);
    rtmpClient1.resizeCache(newSize);
  }

  @Override
  public int getCacheSize() {
    return rtmpClient.getCacheSize();
  }

  @Override
  public long getSentAudioFrames() {
    return rtmpClient.getSentAudioFrames();
  }

  @Override
  public long getSentVideoFrames() {
    return rtmpClient.getSentVideoFrames();
  }

  @Override
  public long getDroppedAudioFrames() {
    return rtmpClient.getDroppedAudioFrames();
  }

  @Override
  public long getDroppedVideoFrames() {
    return rtmpClient.getDroppedVideoFrames();
  }

  @Override
  public void resetSentAudioFrames() {
    rtmpClient.resetSentAudioFrames();
    rtmpClient1.resetSentAudioFrames();
  }

  @Override
  public void resetSentVideoFrames() {
    rtmpClient.resetSentVideoFrames();
    rtmpClient1.resetSentVideoFrames();
  }

  @Override
  public void resetDroppedAudioFrames() {
    rtmpClient.resetDroppedAudioFrames();
    rtmpClient1.resetDroppedAudioFrames();
  }

  @Override
  public void resetDroppedVideoFrames() {
    rtmpClient.resetDroppedVideoFrames();
    rtmpClient1.resetDroppedVideoFrames();
  }

  @Override
  public void setAuthorization(String user, String password,String user1, String password1) {
    rtmpClient.setAuthorization(user, password);
    rtmpClient1.setAuthorization(user1, password1);
  }

  /**
   * Some Livestream hosts use Akamai auth that requires RTMP packets to be sent with increasing
   * timestamp order regardless of packet type.
   * Necessary with Servers like Dacast.
   * More info here:
   * https://learn.akamai.com/en-us/webhelp/media-services-live/media-services-live-encoder-compatibility-testing-and-qualification-guide-v4.0/GUID-F941C88B-9128-4BF4-A81B-C2E5CFD35BBF.html
   */
  public void forceAkamaiTs(boolean enabled) {
    rtmpClient.forceAkamaiTs(enabled);
    rtmpClient1.forceAkamaiTs(enabled);
  }

  /**
   * Must be called before start stream.
   *
   * Default value 128
   * Range value: 1 to 16777215.
   *
   * The most common values example: 128, 4096, 65535
   *
   * @param chunkSize packet's chunk size send to server
   */
  public void setWriteChunkSize(int chunkSize) {
    if (!isStreaming()) {
      rtmpClient.setWriteChunkSize(chunkSize);
      rtmpClient1.setWriteChunkSize(chunkSize);
    }
  }

  @Override
  protected void prepareAudioRtp(boolean isStereo, int sampleRate) {
    rtmpClient.setAudioInfo(sampleRate, isStereo);
    rtmpClient1.setAudioInfo(sampleRate, isStereo);
  }

  @Override
  protected void startStreamRtp(String url,String url2) {
    if (videoEncoder.getRotation() == 90 || videoEncoder.getRotation() == 270) {
      rtmpClient.setVideoResolution(videoEncoder.getHeight(), videoEncoder.getWidth());
    } else {
      rtmpClient.setVideoResolution(videoEncoder.getWidth(), videoEncoder.getHeight());
    }
    rtmpClient.setFps(videoEncoder.getFps());
    rtmpClient.setOnlyVideo(!audioInitialized);
    rtmpClient.connect(url);


    if (videoEncoder.getRotation() == 90 || videoEncoder.getRotation() == 270) {
      rtmpClient1.setVideoResolution(videoEncoder.getHeight(), videoEncoder.getWidth());
    } else {
      rtmpClient1.setVideoResolution(videoEncoder.getWidth(), videoEncoder.getHeight());
    }
    rtmpClient1.setFps(videoEncoder.getFps());
    rtmpClient1.setOnlyVideo(!audioInitialized);
    rtmpClient1.connect(url);
  }

  @Override
  protected void stopStreamRtp() {
   /* rtmpClient.disconnect();
    rtmpClient1.disconnect();*/
  }

  @Override
  public void setReTries(int reTries) {
    rtmpClient.setReTries(reTries);
    rtmpClient1.setReTries(reTries);
  }

  @Override
  protected boolean shouldRetry(String reason) {
    return rtmpClient.shouldRetry(reason);
  }

  @Override
  public void reConnect(long delay, @Nullable String backupUrl) {
    rtmpClient.reConnect(delay, backupUrl);
    rtmpClient1.reConnect(delay, backupUrl);
  }

  @Override
  public boolean hasCongestion() {
    return rtmpClient.hasCongestion();
  }



  @Override
  protected void getAacDataRtp(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {
    rtmpClient.sendAudio(aacBuffer, info);

  }
  @Override
  protected void getAacDataRtp2(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {

    rtmpClient1.sendAudio(aacBuffer, info);
  }

  @Override
  protected void onSpsPpsVpsRtp(ByteBuffer sps, ByteBuffer pps, ByteBuffer vps) {
    rtmpClient.setVideoInfo(sps, pps, vps);
    rtmpClient1.setVideoInfo(sps, pps, vps);
  }

  @Override
  protected void getH264DataRtp(ByteBuffer h264Buffer, MediaCodec.BufferInfo info) {
    rtmpClient.sendVideo(h264Buffer, info);
    rtmpClient1.sendVideo(h264Buffer, info);
  }

  @Override
  public void setLogs(boolean enable) {
    rtmpClient.setLogs(enable);
    rtmpClient1.setLogs(enable);
  }

  @Override
  public void setCheckServerAlive(boolean enable) {
    rtmpClient.setCheckServerAlive(enable);
    rtmpClient1.setCheckServerAlive(enable);
  }
}

