package com.example.asynctaskfileioexample;

import org.json.JSONArray;

public interface AsyncResponse {
    void processFinish(JSONArray output);
    void processPending(Boolean status);
}