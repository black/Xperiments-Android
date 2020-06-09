package com.black.watchads;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class AdViewAdapter extends BaseAdapter {

    private Context context;
    private List<AdsEntity> itemList;

    public AdViewAdapter(Context context, List<AdsEntity> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            v = inflater.inflate(R.layout.ads_card_type_one,null);
        }
        /* Get Views from inflated view */
        TextView title = v.findViewById(R.id.title);
        TextView description = v.findViewById(R.id.description);
        TextView url = v.findViewById(R.id.hyperlink);
        ImageView imageView = v.findViewById(R.id.adimage);

        /* Set values to Views */
        title.setText(itemList.get(position).getTitle());
        description.setText(itemList.get(position).getDescription());
        url.setText(itemList.get(position).getHyperLink());

        final ProgressBar progressBar = v.findViewById(R.id.imageLoader);
        Glide.with(context).clear(imageView);
        Glide.with(context)
                .load(itemList.get(position).getImgURL())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        return v;
    }
}
