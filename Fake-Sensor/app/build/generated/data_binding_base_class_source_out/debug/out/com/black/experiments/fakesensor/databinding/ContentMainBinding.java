// Generated by view binder compiler. Do not edit!
package com.black.experiments.fakesensor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.black.experiments.fakesensor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ContentMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView powerValueOne;

  @NonNull
  public final TextView powerValueTwo;

  @NonNull
  public final TextView sensorValueOne;

  @NonNull
  public final TextView sensorValueTwo;

  private ContentMainBinding(@NonNull LinearLayout rootView, @NonNull TextView powerValueOne,
      @NonNull TextView powerValueTwo, @NonNull TextView sensorValueOne,
      @NonNull TextView sensorValueTwo) {
    this.rootView = rootView;
    this.powerValueOne = powerValueOne;
    this.powerValueTwo = powerValueTwo;
    this.sensorValueOne = sensorValueOne;
    this.sensorValueTwo = sensorValueTwo;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.powerValueOne;
      TextView powerValueOne = ViewBindings.findChildViewById(rootView, id);
      if (powerValueOne == null) {
        break missingId;
      }

      id = R.id.powerValueTwo;
      TextView powerValueTwo = ViewBindings.findChildViewById(rootView, id);
      if (powerValueTwo == null) {
        break missingId;
      }

      id = R.id.sensorValueOne;
      TextView sensorValueOne = ViewBindings.findChildViewById(rootView, id);
      if (sensorValueOne == null) {
        break missingId;
      }

      id = R.id.sensorValueTwo;
      TextView sensorValueTwo = ViewBindings.findChildViewById(rootView, id);
      if (sensorValueTwo == null) {
        break missingId;
      }

      return new ContentMainBinding((LinearLayout) rootView, powerValueOne, powerValueTwo,
          sensorValueOne, sensorValueTwo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}