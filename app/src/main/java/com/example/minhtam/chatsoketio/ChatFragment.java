package com.example.minhtam.chatsoketio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }
    EditText edtChat;
    Button btnChat;
    ListView lvChat;
    MainActivity mainActivity;
    ArrayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        edtChat = (EditText) view.findViewById(R.id.edtChat);
        btnChat = (Button) view.findViewById(R.id.btnChat);
        lvChat = (ListView) view.findViewById(R.id.lvChat);
        mainActivity =(MainActivity) getActivity();
        Log.e("ChatFragment",mainActivity.a);
        adapter = new ArrayAdapter(mainActivity,android.R.layout.simple_list_item_1,mainActivity.msgList);

        lvChat.setAdapter(adapter);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mSocket.emit("client_gui_message",edtChat.getText().toString());
            }
        });
        return view;
    }

}
