package ca.nuro.notificationhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.os.Binder;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import ca.nuro.notificationhistory.AdapterPackage.ItemDataModel;
import ca.nuro.notificationhistory.AdapterPackage.NotificationAdapter;
import ca.nuro.notificationhistory.DataBaseHelperPackage.DataProviderInterface;

public class MainActivity extends AppCompatActivity implements DataProviderInterface {

    private NotificationAdapter notificationAdapter;
    private List<ItemDataModel> itemList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.notificationList);
        itemList = new ArrayList<>();

        notificationAdapter = new NotificationAdapter(this,itemList);
        listView.setAdapter(notificationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Product",view.getTag()+" ");
            }
        });
    }

    @Override
    public void processFinish(JSONArray output) {

    }

    @Override
    public void processPending(Boolean status) {

    }
}
