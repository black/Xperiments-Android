package ca.nuro.notificationhistory.DataBaseHelperPackage;

import org.json.JSONArray;

public interface DataProviderInterface {
    void processFinish(JSONArray output);
    void processPending(Boolean status);
}