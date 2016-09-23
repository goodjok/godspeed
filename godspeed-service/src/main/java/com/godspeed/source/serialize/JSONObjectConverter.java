package com.godspeed.source.serialize;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectConverter extends TypeConverter<String, JSONObject> {
    @Override
    public String getDBValue(JSONObject model) {
        if (model == null)
            return null;
        return model.toString();
    }

    @Override
    public JSONObject getModelValue(String data) {
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
