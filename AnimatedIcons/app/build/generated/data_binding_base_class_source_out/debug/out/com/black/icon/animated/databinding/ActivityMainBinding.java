// Generated by view binder compiler. Do not edit!
package com.black.icon.animated.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.black.icon.animated.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView fromAnimatedDrawable;

  @NonNull
  public final ImageView fromAnimatedDrawableWithControls;

  @NonNull
  public final ImageView fromAnimatedGif;

  @NonNull
  public final ImageView fromGif;

  @NonNull
  public final Button left;

  @NonNull
  public final Button right;

  @NonNull
  public final MaterialButtonToggleGroup toggleSignals;

  private ActivityMainBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView fromAnimatedDrawable, @NonNull ImageView fromAnimatedDrawableWithControls,
      @NonNull ImageView fromAnimatedGif, @NonNull ImageView fromGif, @NonNull Button left,
      @NonNull Button right, @NonNull MaterialButtonToggleGroup toggleSignals) {
    this.rootView = rootView;
    this.fromAnimatedDrawable = fromAnimatedDrawable;
    this.fromAnimatedDrawableWithControls = fromAnimatedDrawableWithControls;
    this.fromAnimatedGif = fromAnimatedGif;
    this.fromGif = fromGif;
    this.left = left;
    this.right = right;
    this.toggleSignals = toggleSignals;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fromAnimatedDrawable;
      ImageView fromAnimatedDrawable = rootView.findViewById(id);
      if (fromAnimatedDrawable == null) {
        break missingId;
      }

      id = R.id.fromAnimatedDrawableWithControls;
      ImageView fromAnimatedDrawableWithControls = rootView.findViewById(id);
      if (fromAnimatedDrawableWithControls == null) {
        break missingId;
      }

      id = R.id.fromAnimatedGif;
      ImageView fromAnimatedGif = rootView.findViewById(id);
      if (fromAnimatedGif == null) {
        break missingId;
      }

      id = R.id.fromGif;
      ImageView fromGif = rootView.findViewById(id);
      if (fromGif == null) {
        break missingId;
      }

      id = R.id.left;
      Button left = rootView.findViewById(id);
      if (left == null) {
        break missingId;
      }

      id = R.id.right;
      Button right = rootView.findViewById(id);
      if (right == null) {
        break missingId;
      }

      id = R.id.toggleSignals;
      MaterialButtonToggleGroup toggleSignals = rootView.findViewById(id);
      if (toggleSignals == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayout) rootView, fromAnimatedDrawable,
          fromAnimatedDrawableWithControls, fromAnimatedGif, fromGif, left, right, toggleSignals);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}