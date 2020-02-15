package com.example.edgemedicaloficial.Adapter;

import android.util.Log;

import com.example.edgemedicaloficial.Model.mLogin.Login;
import com.example.edgemedicaloficial.Model.mLogin.LoginResponse;
import com.example.edgemedicaloficial.Model.mRegistro.Registro;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ContentAdapter implements JsonDeserializer<LoginResponse> {
    @Override
    public LoginResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LoginResponse loginResponse = new Gson().fromJson(json, LoginResponse.class);
        JsonObject jsonObject = json.getAsJsonObject();
        Log.e("Error","Entro"+jsonObject);
        if (jsonObject.has("msg"))
        {
            JsonElement elem = jsonObject.get("msg");
            Log.e("Error","Entro");

            if (elem != null && !elem.isJsonNull())
            {
                if (elem.isJsonPrimitive())
                {
                    Log.e("Error", "Entro");
                }
                else
                {
                    Log.e("Error","Entro"+elem);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<User>>(){}.getType();
                    List<User> r = gson.fromJson(elem, listType);
                    loginResponse.setMsg(r);
                }
            }
        }
        return loginResponse;
    }
}
