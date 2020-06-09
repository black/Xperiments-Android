package com.example.asynctaskbaseexample;

import org.json.JSONArray;

public interface AsyncResponse {
    void processFinish(JSONArray output);
    void processPending(Boolean status);
}