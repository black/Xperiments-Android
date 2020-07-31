package com.black.settingtile;

import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomTileService extends TileService {
    private int[] iconArr = {R.drawable.ic_baseline_beach_access_24,R.drawable.ic_baseline_control_camera_24};
    @Override
    public void onClick() {
        Random r = new Random();

        Icon icon = Icon.createWithResource(getApplicationContext(),iconArr[r.nextInt(2)]);
        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }


}
