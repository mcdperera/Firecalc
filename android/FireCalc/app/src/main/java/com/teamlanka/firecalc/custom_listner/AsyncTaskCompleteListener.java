package com.teamlanka.firecalc.custom_listner;

import org.json.JSONException;

public interface AsyncTaskCompleteListener {

	void onTaskCompleted(String response, int serviceCode) throws JSONException;

}
