// Generated by view binder compiler. Do not edit!
package com.black.xperiments.headtracking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.black.xperiments.headtracking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TileSimpleBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView respDescription;

  @NonNull
  public final ImageView respImg;

  @NonNull
  public final ImageView respMore;

  @NonNull
  public final TextView respTitle;

  private TileSimpleBinding(@NonNull RelativeLayout rootView, @NonNull TextView respDescription,
      @NonNull ImageView respImg, @NonNull ImageView respMore, @NonNull TextView respTitle) {
    this.rootView = rootView;
    this.respDescription = respDescription;
    this.respImg = respImg;
    this.respMore = respMore;
    this.respTitle = respTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TileSimpleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TileSimpleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tile_simple, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TileSimpleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.resp_description;
      TextView respDescription = ViewBindings.findChildViewById(rootView, id);
      if (respDescription == null) {
        break missingId;
      }

      id = R.id.resp_img;
      ImageView respImg = ViewBindings.findChildViewById(rootView, id);
      if (respImg == null) {
        break missingId;
      }

      id = R.id.resp_more;
      ImageView respMore = ViewBindings.findChildViewById(rootView, id);
      if (respMore == null) {
        break missingId;
      }

      id = R.id.resp_title;
      TextView respTitle = ViewBindings.findChildViewById(rootView, id);
      if (respTitle == null) {
        break missingId;
      }

      return new TileSimpleBinding((RelativeLayout) rootView, respDescription, respImg, respMore,
          respTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}