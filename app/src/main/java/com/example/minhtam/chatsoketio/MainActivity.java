package com.example.minhtam.chatsoketio;

import android.icu.text.LocaleDisplayNames;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;
    ArrayList<String> tabTitles;
    ChatFragment chatFragment;
    ListUserFragment listUserFragment;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<String> userList;
    ArrayList<String> msgList;
    public String a="Day la mainactivity";
    public Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.192:3000/");
        } catch (URISyntaxException e) {

            Log.e("mSocket","Bị lỗi");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.on("server-send-dangki-thatbai",serverSendDangKyThatBai);
        mSocket.on("server-send-co-nguoi-dangki-thanhcong",serverSendCoNguoiDangKyThanhCong);
        mSocket.on("server-send-dangki-thanhcong",serverSendDangKyThanhCong);
        mSocket.on("server_gui_message",serverGuiMessage);
        mSocket.on("server-send-du-lieu-cho-nguoi-moi",serverSendDuLieuChoNguoiMoi);
        mSocket.on("server-send-co-nguoi-thoat",serverSendCoNguoiThoat);
        mSocket.connect();

        setContentView(R.layout.activity_main);

        userList = new ArrayList<>();
        msgList = new ArrayList<>();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        fragments = new ArrayList<>();
        tabTitles = new ArrayList<>();
        chatFragment = new ChatFragment();
        listUserFragment = new ListUserFragment();
        fragments.add(chatFragment);
        fragments.add(listUserFragment);
        tabTitles.add("Chat");
        tabTitles.add("ListUser");
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments,tabTitles);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private Emitter.Listener serverSendDangKyThatBai = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject data = (JSONObject) args[0];

                    Log.e("DangKyThatBai",args[0].toString());

                    // add the message to view
                    Toast.makeText(MainActivity.this,"Đăng ký thấy bại",Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    private Emitter.Listener serverSendDangKyThanhCong = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject data = (JSONObject) args[0];

                    Log.e("DangKyThanhCong",args[0].toString());
                    listUserFragment.btnUser.setVisibility(View.INVISIBLE);
                    listUserFragment.edtUser.setVisibility(View.INVISIBLE);
                    listUserFragment.txtUser.setVisibility(View.VISIBLE);
                    listUserFragment.txtUser.setText(listUserFragment.edtUser.getText().toString());

                    // add the message to view
                }
            });
        }
    };
    private Emitter.Listener serverSendCoNguoiDangKyThanhCong = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject data = (JSONObject) args[0];

                    Log.e("CoNguoiDangKyThanhCong",args[0].toString());
                    userList.add(args[0].toString());
                    listUserFragment.adapter.notifyDataSetChanged();

                    // add the message to view
                }
            });
        }
    };
    private Emitter.Listener serverGuiMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];

                        Log.e("GuiMessage", args[0].toString());
                        String username = data.getString("Username");
                        String msg = data.getString("msg");
                        msgList.add(username+": "+msg);
                        chatFragment.adapter.notifyDataSetChanged();
                        // add the message to view
                    }
                    catch (Exception e){
                        Log.e("GuiMessage", "That cmnr");

                    }
                }
            });
        }
    };
    private Emitter.Listener serverSendDuLieuChoNguoiMoi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = (JSONArray) args[0];

                        Log.e("DuLieuChoNguoiMoi",args[0].toString());
                        for (int i=0;i<data.length();i++){
                            userList.add(data.get(i).toString());
                        }
                        listUserFragment.adapter.notifyDataSetChanged();
                        // add the message to view
                    }
                    catch (Exception e){
                        Log.e("DuLieuChoNguoiMoi","That bai cmmr");
                    }
                }
            });
        }
    };
    private Emitter.Listener serverSendCoNguoiThoat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = (JSONArray) args[0];

                        Log.e("CoNguoiThoat", args[0].toString());
                        userList.clear();
                        for (int i = 0; i < data.length(); i++) {
                            userList.add(data.get(i).toString());
                        }
                        listUserFragment.adapter.notifyDataSetChanged();

                        // add the message to view
                    }
                    catch (Exception e){
                        Log.e("serverSendCoNguoiThoat","That bai cmmr");
                    }

                }
            });
        }
    };
}
