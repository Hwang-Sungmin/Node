package com.example.taskfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskfirebase.adapter.OrgAdapter;
import com.example.taskfirebase.databinding.ActivityUserBinding;
import com.example.taskfirebase.model.Orgs;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding vw;
    UserViewModel vm = new UserViewModel();
    public String userKey = "";
    OrgAdapter orgAdapter;
    OrgAdapter myOrgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vw = DataBindingUtil.setContentView(this, R.layout.activity_user);
        vw.setVw(this);
        vw.setVm(vm);

        Intent intent = getIntent();
        userKey = intent.getStringExtra("userKey");
        vm.UserViewModelSetting(userKey);
    }

    public void OrgSearchClick(){
        vm.OrgSearch(new UserViewModel.IOrgsCallback() {
            @Override
            public void callBack(ArrayList<Orgs> orgs) {
//                orgAdapter = new OrgAdapter(getApplicationContext(),orgs);
                orgAdapter = new OrgAdapter(getApplicationContext(), orgs, new OrgAdapter.IOrgsSelectListener() {
                    @Override
                    public void onSelected(Orgs org) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(UserActivity.this);
                        //dlg.setMessage(org.getName() + "을(를) 주 병원으로 지정하시겠습니까?")
                        dlg.setMessage(org.getOrgName())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        vm.UserOrgSave(org);
                                    }
                                })
                                .show();
                    }
                });
                vw.rvOrg.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                vw.rvOrg.setAdapter(orgAdapter);
            }
        });
    }


    public void MyOrgSearchClick(){
        vm.MyOrgSearchClick(new UserViewModel.IMyOrgSearchClickCallback() {
            @Override
            public void callBack(ArrayList<Orgs> orgs) {
                myOrgAdapter = new OrgAdapter(getApplicationContext(),orgs);
                vw.rvMyOrg.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                vw.rvMyOrg.setAdapter(myOrgAdapter);
            }
        });
    }


}