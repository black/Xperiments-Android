package ca.nuro.notificationhistory.AdapterPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.nuro.notificationhistory.R;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private List<ItemDataModel> itemList;

    public NotificationAdapter(Context context, List<ItemDataModel> itemList) {
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
            v = inflater.inflate(R.layout.notification_layout,null);
        }
        /* Get Views from inflated view */
        TextView title = v.findViewById(R.id.title);
        TextView message = v.findViewById(R.id.message);
        ImageView img = v.findViewById(R.id.appicon);

        /* Set values to Views */
        title.setText(itemList.get(position).getTitle());
        message.setText(itemList.get(position).getMessage());
        img.setImageResource(itemList.get(position).getImg());

        return v;
    }
}
