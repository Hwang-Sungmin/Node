package com.example.taskfirebase;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.taskfirebase.model.Orgs;
import com.example.taskfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel {

    FirebaseFirestore db;
    final private String TAG = "MainViewModel";

    public ObservableField<String> ObUserName = new ObservableField<>("");
    public ObservableField<String> ObUserId = new ObservableField<>("");

    public ObservableField<String> ObOrgName = new ObservableField<>("");
    public ObservableField<String> ObOwner = new ObservableField<>("");


    public interface IUserCallback{
        void callBack(ArrayList<User> user);
    }

    public interface IOrgsCallback{
        void callBack(ArrayList<Orgs> orgs);
    }


    public ArrayList<User> adapterUser;

    public void ViewModelSetting(){
       db = FirebaseFirestore.getInstance();
    }

    public void UserSave(){
        User user = new User();

        String name = ObUserName.get().trim();
        String id = ObUserId.get().trim();

        user.setUserName(name);
        user.setUserId(id);

        db.collection("usr").document(user.getKey()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("사용자 등록 완료 !!!!");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("사용자 등록 실패 !!!!");

            }
        });
    }

    public void OrgSave(){
        Orgs orgs = new Orgs();
        System.out.println("여기는 들어오징??");
        String orgName = ObOrgName.get().trim();
        String owner = ObOwner.get().trim();

        orgs.setOrgName(orgName);
        orgs.setOwner(owner);

        db.collection("orgs").document(orgs.getKey()).set(orgs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("기업 등록 완료 !!!!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("기업 등록 실패 !!!!");
            }
        });
    }

    public void UserSearch(IUserCallback cb){
        db.collection("usr").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();

                    if ( querySnapshot != null){
                        List<DocumentSnapshot> docs = querySnapshot.getDocuments();
                        if ( docs !=null){
                           ArrayList<User> user = new ArrayList<>();
                            for ( DocumentSnapshot doc : docs){
                                User usr = UserSearchDetail(doc);
                                user.add(usr);
                            }
                            cb.callBack(user);
                        }
                    }
                }
            }
        });
    }
    public static User UserSearchDetail(DocumentSnapshot doc){
        User user = new User();
        System.out.println(doc.get("userName"));
        System.out.println(doc.getString("userId"));

        user.setUserName(doc.getString("userName"));
        user.setUserId(doc.getString("userId"));
        user.setKey(doc.getString("key"));
        return user;
    }

    public void OrgSearch(IOrgsCallback cb){
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
}
