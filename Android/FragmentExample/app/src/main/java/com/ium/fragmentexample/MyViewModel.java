package com.ium.fragmentexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> role = new MutableLiveData<>("Guest");
    public MutableLiveData<ArrayList<String>> upcomingEvents = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<ArrayList<String>> pastEvents = new MutableLiveData<>(new ArrayList<>());
    AsyncHttpClient myHttpClient = new AsyncHttpClient();

    public ArrayList<String> getUpcomingEventsOptions()
    {
        ArrayList<String> ret = new ArrayList<>();
        if(upcomingEvents.getValue() == null) return ret;
        for(String s : upcomingEvents.getValue())
        {
            ret.add(s.split("\n")[0]);
        }
        return ret;
    }
    public ArrayList<String> getPastEventsOptions()
    {
        ArrayList<String> ret = new ArrayList<>();
        if(pastEvents.getValue() == null) return ret;
        for(String s : pastEvents.getValue())
        {
            ret.add(s);
        }
        return ret;
    }
}
