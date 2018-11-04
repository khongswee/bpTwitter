package com.bp.twitter.presentation.tweetlist.view.viewholder;

import android.net.Uri;
import android.support.annotation.NonNull;

import im.ene.toro.ToroPlayer;
import im.ene.toro.exoplayer.Config;
import im.ene.toro.exoplayer.ExoPlayable;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.MediaSourceBuilder;

@SuppressWarnings("unused") class LoopingPlayerHelper extends ExoPlayerViewHelper {

  private static Config loopingConfig =
      new Config.Builder().setMediaSourceBuilder(MediaSourceBuilder.LOOPING).build();

  @SuppressWarnings("unused") //
  LoopingPlayerHelper(@NonNull ToroPlayer player, @NonNull Uri mediaUri) {
    // customized using Looping media source builder
    this(player, mediaUri, null);
  }

  @SuppressWarnings("WeakerAccess") //
  LoopingPlayerHelper(@NonNull ToroPlayer player, @NonNull Uri mediaUri, String extension) {
    super(player, mediaUri, extension, loopingConfig);
  }

  LoopingPlayerHelper(@NonNull ToroPlayer player, @NonNull ExoPlayable playable) {
    super(player, playable);
  }
}