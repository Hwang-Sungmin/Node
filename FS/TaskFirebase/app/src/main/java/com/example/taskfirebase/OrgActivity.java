package com.example.taskfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskfirebase.adapter.UserAdapter;
import com.example.taskfirebase.databinding.ActivityOrgBinding;
import com.example.taskfirebase.model.User;

import java.util.ArrayList;

public class OrgActivity extends AppCompatActivity {

    ActivityOrgBinding vw;
    OrgViewModel vm = new OrgViewModel();
    String orgKey = "";
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = DataBindingUtil.setContentView(this, R.layout.activity_org);
        vw.setVw(this);
        vw.setVm(vm);

        Intent intent = getIntent();
        orgKey = intent.getStringExtra("orgKey");

        vm.OrgViewModelSetting(orgKey);
    }


    public void UserSearchClick(){
        vm.OrgUserSearchClick(new OrgViewModel.IOrgUserSearchClickCallback() {
            @Override
            public void callBack(ArrayList<User> users) {
                userAdapter = new UserAdapter(getApplicationContext(),users);
                vw.rvUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                vw.rvUser.setAdapter(userAdapter);
            }
        });


    }
}