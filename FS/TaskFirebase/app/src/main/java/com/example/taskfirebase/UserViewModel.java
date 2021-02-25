package com.example.taskfirebase;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.taskfirebase.model.Orgs;
import com.example.taskfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserViewModel {

    String userKey = "";
    FirebaseFirestore db;

    public ObservableField<String> ObUserName = new ObservableField<>("");

    public interface IOrgsCallback{
        void callBack(ArrayList<Orgs> orgs);
    }

    public void UserViewModelSetting(String key){
        userKey = key;
        db = FirebaseFirestore.getInstance();
        UserSetting();
    }

    public void UserSetting(){
        db.collection("usr").whereEqualTo("key",userKey)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){

                        ObUserName.set(document.getString("userName"));
                    }
                }
            }
        });
    }

    public void OrgSearch(UserViewModel.IOrgsCallback cb){
        db.collection("orgs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    if ( querySnapshot != null){
                        List<DocumentSnapshot> docs = querySnapshot.getDocuments();
                        if ( docs != null){
                            ArrayList<Orgs> orgs = new ArrayList<>();
                            for ( DocumentSnapshot doc : docs){
                                Orgs org = OrgsSearchDetail(doc);

                                orgs.add(org);
                            }
                            cb.callBack(orgs);
                        }
                    }
                }
            }
        });
    }
    public static Orgs OrgsSearchDetail(DocumentSnapshot doc){
        Orgs orgs = new Orgs();
        orgs.setOwner(doc.getString("owner"));
        orgs.setOrgName(doc.getString("orgName"));
        orgs.setKey(doc.getString("key"));
        return orgs;
    }


    public void UserOrgSave(Orgs orgs){
        // user의 org update
        User user = new User();
        HashMap<String, Orgs> map = new HashMap<>();
        map.put(orgs.getKey(),orgs);
        user.setOrgs(map);

        DocumentReference UserRef = db.collection("usr").document(userKey);

        HashMap<String, HashMap<String, Orgs> > hashMap = new HashMap<>();
        hashMap.put("orgs",map);

        UserRef.set(hashMap,SetOptions.merge());

        OrgUserSave(orgs);
    }

    public void OrgUserSave(Orgs orgs){
        //orgs의 user 업데이트

        DocumentReference OrgRef = db.collection("orgs").document(orgs.getKey());
        HashMap<String, User> map = new HashMap<>();

        db.collection("usr").whereEqualTo("key",userKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    User usr = new User();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        usr.setKey(document.getString("key"));
                        usr.setUserId(document.getString("userId"));
                        usr.setUserName(document.getString("userName"));
                    }
                    map.put(usr.getKey(),usr);

                    HashMap<String, HashMap<String, User> > hashMapHashMap = new HashMap<>();
                    hashMapHashMap.put("usr",map);

                    OrgRef.set(hashMapHashMap,SetOptions.merge());

                }
            }
        });
    }

    public interface IMyOrgSearchClickCallback{
        void callBack(ArrayList<Orgs> orgs);
    }

    // 내가 등록한 조직 리스트
    public void MyOrgSearchClick(IMyOrgSearchClickCallback cb){
        db.collection("usr").whereEqualTo("key",userKey)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){

                    for ( QueryDocumentSnapshot doc : task.getResult()){
                        Object obOrgs = doc.get("orgs");

                        if (obOrgs != null) {
                            Map<String, Object> omap = (Map<String, Object>) obOrgs;
                            ArrayList<Orgs> orgsArrayList = new ArrayList<>();

                            for (Object o : omap.values()) {
                                Map<String, Object> orgmap = (Map<String, Object>)o;
                                Orgs org = MapOrgs(orgmap);

                                orgsArrayList.add(org);
                            }

                            cb.callBack(orgsArrayList);
                        }
                    }
                }
            }
        });
    }

    public static Orgs MapOrgs(Map<String, Object> map) {
        Object okey = map.get("key");
        Object oOwner = map.get("orgName");
        Object oOrgName= map.get("owner");

        Orgs org = new Orgs();
        org.setKey((String) okey);
        org.setOrgName((String) oOrgName);
        org.setOwner((String) oOwner);

        return org;
    }



}
