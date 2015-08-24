package com.uitox.asap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uitox.asapapp.GCMEntity;
import com.uitox.asapapp.GCMEntityResult;
import com.uitox.gcm.GCM;
import com.uitox.http.GsonRequest;
import com.uitox.http.NetParams;
import com.uitox.http.NetWorkTool;
import com.uitox.lib.ShowYourMessage;
import com.uitox.view.BaseFragmentView;

import java.util.ArrayList;
import java.util.HashMap;

public class GCMFragmentView extends BaseFragmentView {

    private View view;
    private AutoCompleteTextView auto_tv;
    private ArrayList<String> names;
    private ArrayAdapter<String> adapter_c;
    private String regId;
    private TextView send_msg;
    private Button send_gcm;

    //要顯示的VIEW
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gcm_layout, container, false);
        GCM GCM = new GCM(getActivity());
        GCM.openGCM();
        regId = GCM.getRegistrationId();
        Log.i("regId", GCM.getRegistrationId());
        send_gcm = (Button) view.findViewById(R.id.send_gcm);
        send_msg = (TextView) view.findViewById(R.id.send_msg);
        send_gcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        /*Gson gson = new Gson();
        String jsonData = "{\"multicast_id\":6660102880917479615,\"success\":1,\"failure\":0,\"canonical_ids\":0,\"results\":[{\"message_id\":\"0:1440393733821321%124a2af3f9fd7ecd\"}]}";
        GCMEntity person = gson.fromJson(jsonData, GCMEntity.class);

        for (GCMEntityResult res : person.getResults()) {
            Log.i("test", res.getMessage_id().toString());
        }*/

        return view;
    }

    //該VIEW要娶的資料
    @Override
    public void initData() {

    }

    //接收傳入參數
    @Override
    public void initBundle(Bundle bundle) {

    }

    @Override
    public void updateUI() {

    }

    public void send() {
        NetWorkTool.getInstance(getActivity()).clearCache();
        String msg = send_msg.getText().toString();
        Log.i("send", msg);
        Log.i("send", regId);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("message", msg);
        hashMap.put("registatoin_ids", regId);

        GsonRequest<GCMEntity> notifyRequest = new GsonRequest<GCMEntity>(
                Request.Method.POST,
                NetParams.getSHIHJIEUrl("/phpinfo.php"),
                GCMEntity.class,
                null,
                new Response.Listener<GCMEntity>() {
                    @Override
                    public void onResponse(GCMEntity gcm) {
                        for (GCMEntityResult res : gcm.getResults()) {
                            ShowYourMessage.msgToShowShort(getActivity(), res.getMessage_id().toString());
                        }
                        ShowYourMessage.msgToShowShort(getActivity(), "send ok");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ShowYourMessage.msgToShowShort(getActivity(), "ERROR2");
                    }
                }, hashMap);
        NetWorkTool.getInstance(getActivity()).addToRequestQueue(notifyRequest);
    }
}
