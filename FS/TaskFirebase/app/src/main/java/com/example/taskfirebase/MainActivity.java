package com.example.taskfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.taskfirebase.adapter.OrgAdapter;
import com.example.taskfirebase.adapter.UserAdapter;
import com.example.taskfirebase.databinding.ActivityMainBinding;
import com.example.taskfirebase.model.Orgs;
import com.example.taskfirebase.model.User;

import java.util.ArrayList;

/**
 * firebase 내 테스트용 프로젝트 만들 것 1) 황성민 : test-smhwang 2) 박경민 : test-gmpark
 * 각 자 아래의 구조로 cloud firestore DB 생성
 * org (조직) : key, name(조직명), 하위 collection / 그룹핑된 사용자(usr) 목록
 * usr (사용자) : key, name(사용자명), orgs(document 내 배열형 필드로) / 사용자가 속한 org 목록
 * org-usr 관계는 多:多 관계임
 * android 프로젝트
 * org 입력 쿼리, 화면
 * usr 입력 쿼리, 화면
 * org, usr 매칭 쿼리, 화면 (usr가 org를 선택)
 * usr 목록 화면(RecyclerView)
 * org 목록 화면(RecyclerView)
 */


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding vw;
    MainViewModel vm = new MainViewModel();
    UserAdapter userAdapter;
    OrgAdapter orgAdapter;


    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vw = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vw.setVw(this);
        vw.setVm(vm);

        vm.ViewModelSetting();


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        Map<String,Object> user = new HashMap<>();
//
//        user.put("first", "Alan");
//        user.put("middle", "Mathison");
//        user.put("last", "Turing");
//        user.put("born", 1912);
//
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
    }

    public void OnUserSearch() {
        vm.UserSearch(new MainViewModel.IUserCallback() {
            @Override
            public void callBack(ArrayList<User> user) {
//                userAdapter = new UserAdapter(getApplicationContext(), user);

                userAdapter = new UserAdapter(getApplicationContext(), user, new UserAdapter.IUserSelectListener() {
                    @Override
                    public void onSelected(User user) {
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.putExtra("userKey" ,user.getKey());
                        startActivity(intent);
                    }
                });

                vw.rvUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                vw.rvUser.setItemAnimator(new DefaultItemAnimator());
                vw.rvUser.setAdapter(userAdapter);
            }
        });
    }

    public void OnOrgsSearch(){
        vm.OrgSearch(new MainViewModel.IOrgsCallback() {
            @Override
            public void callBack(ArrayList<Orgs> orgs) {
                orgAdapter = new OrgAdapter(getApplicationContext(),orgs);
                orgAdapter = new OrgAdapter(getApplicationContext(), orgs, new OrgAdapter.IOrgsSelectListener() {
                    @Override
                    public void onSelected(Orgs org) {
                        Intent intent = new Intent(getApplicationContext(), OrgActivity.class);
                        intent.putExtra("orgKey" ,org.getKey());
                        startActivity(intent);
                    }
                });

                vw.rvOrg.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                vw.rvOrg.setItemAnimator(new DefaultItemAnimator());
                vw.rvOrg.setAdapter(orgAdapter);
            }
        });
    }
}