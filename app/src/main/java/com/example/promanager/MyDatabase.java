package com.example.promanager;


// NHỮNG GIÁ TRỊ TRONG TRANG NÀY HIỆN CHỈ LÀ GIẢ KHỞI TẠO! database sẽ có thể trả về những giá trị khác

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MyDatabase {
    static private List<userInfo_Database> mListUser = new ArrayList<>();
    public static ImageView getAvatarById(Query db, Context context, String userId, String size){
        ImageView image = new ImageView(context);
        int image_size;
        if (size=="small") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_small);
        else if (size=="tiny") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_tiny);
        else image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_medium);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(image_size, image_size);
        params.setMargins(0,0,25,0);
        image.setLayoutParams(params);

        image.setBackgroundResource(R.drawable.user_image);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return image;
    }


    //trả về id user khác connection với user
    public static ArrayList<String> getConnectedUserId(Query db, String myId){
        ArrayList<String> user_of_connection_id = new ArrayList<String>();
        user_of_connection_id.add("20127333");
        user_of_connection_id.add("20127306");
        user_of_connection_id.add("20127473");
        user_of_connection_id.add("20127582");
        user_of_connection_id.add("20127333");
        user_of_connection_id.add("20127306");
        user_of_connection_id.add("20127473");
        user_of_connection_id.add("20127582");
        return user_of_connection_id;
    }

    //trả về id (username) của người dùng hiện tại
    public static String getCurrentUserId(String username, String password){
        String userId = username;
        return userId;
    }

    //-----------------------------------------------------
    public interface getAllProjectsCallback {
        void onAllProjectsReceived(ArrayList<Project_Database> all_projects);
    }

    //trả về toàn bộ id của project mà người dùng hiện phải chủ trì (manager, own)
    public static void getOwnProject(String myId, getAllProjectsCallback callback){
        ArrayList<Project_Database> all_own_projects = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Project_Database project = dataSnapshot.getValue(Project_Database.class);
                    all_own_projects.add(project);
                }
                callback.onAllProjectsReceived(all_own_projects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //-------------------------------------------------------


    //-------------------------------------------------------

//    public interface getAllProjectsCallback {
//        void onAllProjectsReceived(ArrayList<Project_Database> all_projects);
//    }

    //trả về toàn bộ id của project mà người dùng hiện KHÔNG tham gia và KHÔNG phải chủ trì (Tạm thời lấy hết)
    public static void getAllProject(String myId, getAllProjectsCallback callback){
        ArrayList<Project_Database> all_projects = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Project_Database project = dataSnapshot.getValue(Project_Database.class);
                    all_projects.add(project);
                }
                callback.onAllProjectsReceived(all_projects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //-------------------------------------------------------

    //---------------------------------------------------------

    public interface getActivityByIdCallback {
        void onActivityByIdReceived(Activity_Database cur_Activity);
    }

    //trả về 1 số thông tin quan trọng của activity
    public static void getActivityById(String actId, getActivityByIdCallback callback){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Activity");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Activity_Database act = dataSnapshot.getValue(Activity_Database.class);
                    if(act.getActivityID().equals(actId)){
                        callback.onActivityByIdReceived(act);
                        return;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //----------------------------------------------------------

    //trả về 1 số thông tin quan trọng của project
    //proId truyền vào tam là "20127306"
    public interface getCurrentProjectCallback {
        void onCurrentProjectReceived(Project_Database project);
    }

    public static void getProjectById(String proId, getCurrentProjectCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Project_Database project = dataSnapshot.getValue(Project_Database.class);
                    if(project.getProjectID().equals(proId)){
                        Log.d("Check ID", project.getProjectID());
                        Project_Database project_Current = project;
                        callback.onCurrentProjectReceived(project_Current);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    //trả về danh sách request activity của user hiện tại (cái này t nghĩ là phải tạo 1 bảng mới là Request trong db)
    public static ArrayList<String> getActivityRequestListId(Query db, String myId){
        ArrayList<String> activity_request_id = new ArrayList<String>();
        activity_request_id.add("act20127333");
        activity_request_id.add("act20127306");
        return activity_request_id;
    }



    //updata data to database, including status, agreement and filefolder of activity
    public static void setStatusActivity(String actId, String value){}
    public static void setAgreementActivity(String actId, String value){}
    public static void setFileFolderActivity(String actId, String value){}

    //tạo thêm 1 task mới và gán cho project
    public interface ActivityIdCallback {
        void onActivityIdReceived(String activityID);
    }

    public static void addNewTaskToProject(Context A, Class<ProjectInforActivity> B, Activity_Database act, String proId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project").child(proId).child("activityIds");


        String pathObject = String.valueOf(act.getActivityID());

        myRef.child(pathObject).setValue(act, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(A, "Add activity to Project complete!", Toast.LENGTH_SHORT).show();
                Log.e("backToPreviousPage", "DONE");
                Intent intent = new Intent(A, B);
                Bundle bundleBack = new Bundle();
                bundleBack.putString("project_id", proId);
                intent.putExtras(bundleBack);
                A.startActivity(intent);
            }
        });
    }

    public static void createActivity(ActivityIdCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Activity");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String new_activity_id = "activity" + Long.toString(snapshot.getChildrenCount()+1);
//                Toast.makeText(LoginActivity.this, Long.toString(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                callback.onActivityIdReceived(new_activity_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //tạo 1 project mới, hàm này m trả về id project vừa tạo nha!
    public interface ProjectIdCallback {
        void onProjectIdReceived(String projectId);
    }

    public static void createNewProject(Project_Database project, ProjectIdCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String new_project_id = "project" + Long.toString(snapshot.getChildrenCount()+1);
//                Toast.makeText(LoginActivity.this, Long.toString(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                callback.onProjectIdReceived(new_project_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //send email reset password cho ng dung
    public static void sendEmailResetPassword(String email){}


    //remove va accept request boi ng dung
    public static void acceptRequest(Query db, String actId, String userId){}
    public static void removeRequest(Query db, String actId, String userId){}

}
