package com.example.minhtam.chatsoketio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListUserFragment extends Fragment {


    public ListUserFragment() {
        // Required empty public constructor
    }

    TextView txtUser;
    EditText edtUser;
    Button  btnUser;
    ListView lvUser;
    ArrayAdapter adapter;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        txtUser = (TextView) view.findViewById(R.id.txtUser);
        edtUser = (EditText) view.findViewById(R.id.edtUser);
        btnUser = (Button) view.findViewById(R.id.btnUser);
        lvUser = (ListView) view.findViewById(R.id.lvUser);
        mainActivity =(MainActivity) getActivity();

        adapter = new ArrayAdapter(mainActivity,android.R.layout.simple_list_item_1,mainActivity.userList);
        lvUser.setAdapter(adapter);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mSocket.emit("client_gui_username",edtUser.getText().toString());
            }
        });
        return view;
    }
}
