package com.example.taskfirebase;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.taskfirebase.model.Orgs;
import com.example.taskfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class OrgViewModel {
    String orgKey = "";
    FirebaseFirestore db;

    public ObservableField<String> ObOrgName = new ObservableField<>("");

    public void OrgViewModelSetting(String key){
        orgKey = key;
        db = FirebaseFirestore.getInstance();
        OrgSetting();
    }

    public void OrgSetting(){
        db.collection("orgs").whereEqualTo("key",orgKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        ObOrgName.set(document.getString("orgName"));
                    }
                }
            }
        });
    }

    public interface IOrgUserSearchClickCallback{
        void callBack(ArrayList<User> users);
    }

    public void OrgUserSearchClick(IOrgUserSearchClickCallback cb){
        db.collection("orgs").whereEqualTo("key",orgKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Object obUser = doc.get("usr");
                        if ( obUser !=null){
                            Map<String,Object> uMap = (Map<String, Object>) obUser;
                            ArrayList<User> userArrayList = new ArrayList<>();

                            for (Object o : uMap.values()){
                                Map<String,Object> userMap = (Map<String, Object>) o;
                                User user = MapUser(userMap);
                                userArrayList.add(user);
                            }

                            cb.callBack(userArrayList);
                        }
                    }
                }
            }
        });

    }

    public static User MapUser(Map<String, Object> map) {
        Object okey = map.get("key");
        Object oUserId = map.get("userId");
        Object oUserName= map.get("userName");

        User user = new User();
        user.setKey((String) okey);
        user.setUserName((String) oUserName);
        user.setUserId((String) oUserId);

        return user;
    }


}
