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
    //cái này chưa cần làm!
    public static ImageView getAvatarById(Query db, Context context, String userId, String size){
        ImageView image = new ImageView(context);
        int image_size;
        if (size=="small") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_small);
        else if (size=="tiny") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_tiny);
        else image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_medium);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(image_size, image_size);
        params.setMargins(0,0,25,0);
        image.setLayoutParams(params);

        String link = getLinkAvatarById(db, userId);
        new MyInternet.DownloadImageTask(image).execute(link);

        image.setBackgroundResource(R.drawable.avatar);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return image;
    }

    //trả về image link của ng dùng
    public static String getLinkAvatarById(Query db, String userId){
        String link = "Empty";

        if (link=="Empty") return "https://i.pinimg.com/564x/01/fc/6f/01fc6f6f0a921bf1529b4989b8973d9f.jpg";
        return link;
    }

    //trả về số id user hiện respon cho activity
    public static ArrayList<String>  getResponsibilityUserId(Query db, String actId){
        ArrayList<String> user_of_respon_id = new ArrayList<String>();

        String strGetResponsibilityUserId = "SELECT username FROM UserResponActivity WHERE activityID = '"+actId+"'";
        Cursor getResponsibilityUserId = db.getData(strGetResponsibilityUserId);
        while(getResponsibilityUserId.moveToNext()){
            String username = getResponsibilityUserId.getString(0);
            user_of_respon_id.add(username);
        }
        return user_of_respon_id;
    }


    //trả về id user khác connection với user
    public static ArrayList<String> getConnectedUserId(Query db, String myId){
        ArrayList<String> user_of_connection_id = new ArrayList<String>();
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

    //trả về toàn bộ id của project mà người dùng hiện tham gia
    public static ArrayList<String> getCurrentResponProject(String myId){
        ArrayList<String> all_project_ids = new ArrayList<>();
        return all_project_ids;
    }

    //trả về danh sách activityId của 1 project
    public static ArrayList<String> getActivityIdListByProjectId(Query db, String proId){
        ArrayList<String> listId= new ArrayList<String>();
        String strGetOwnProject = "SELECT activityID FROM ActivityInProject WHERE projectID = '"+proId+"'";
        Cursor ActivityIdListByProjectId = db.getData(strGetOwnProject);
        while(ActivityIdListByProjectId.moveToNext()){
            String activityItem = ActivityIdListByProjectId.getString(0);
            listId.add(activityItem);
        }

        return listId;
    }

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

    //trả về số task mà người dùng còn trong deadline (CurrentTasks)
    public static int getCurrentTasks(Query db, String myId){
        final int[] current_task = {0};
        MyDatabase.getAllProject(myId, new getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                for (Project_Database cur_project: all_projects) {
                    if (cur_project.getProjectOwner().equals(myId))
                        current_task[0] += 1;
                }
            }
        });
        return current_task[0];
    }

    //trả về số task mà người dùng còn trong deadline nhưng hoàn thành rồi
    public static int getCurrentFinishedTasks(Query db, String myId){
        int finished_task;
        try {
            String strCountCurrentFinishedTasks = "SELECT COUNT(currentFinished) FROM UserInfo WHERE username = '"+myId+"'";

            Cursor CurrentFinishedTasks = db.getData(strCountCurrentFinishedTasks);
            String countCurrentFinishedTask = CurrentFinishedTasks.getString(0);
            finished_task = Integer.parseInt(countCurrentFinishedTask);
        } catch (Exception ex) {finished_task=0;}
        return finished_task;
    }

    //trả về tổng số task mà người dùng hoàn thành từ trc tới giờ
    public static int getTotalTasks(Query db, String myId){
        int total_task = 16;
        try{
            String strTotalTasks = "SELECT COUNT(totalTasks) FROM UserInfo WHERE username = '"+myId+"'";

            Cursor TotalTasks = db.getData(strTotalTasks);
            String countTotalTask = TotalTasks.getString(0);
            total_task = Integer.parseInt(countTotalTask);
        } catch (Exception ex){total_task=0;}
        return total_task;
    }

    //trả về số thời gian người dùng tham gia từ trc tới giờ
    //trong activity sẽ có thời gian sẽ có thời gian deadline, cứ cộng thời gian lại là dc
    //? -> Chẳng lẽ cứ tham gia 1 activity là thời gian tham gia của người đó phải cộng thêm khoảng thời
    //     gian từ lúc bắt đầu cho đến khi deadline à, thời gian tgia này nên là tgian nhận cho đến khi làm xong
    //     nên để update sau này, chứ ko cần thiết làm vậy
    //! => Thời gian chỉ là thời gian project user trong deadline thôi, tức là tổng tg deadline user có dc
    //      vd: userA có 1 deadline 2 ngày, 1 deadline 3 ngày. Vậy tổng tg là 5 ngày = 120h
    public static int getTotalHour(Query db, String myId){
        int total_hour;
        try {
            String strTotalHours = "SELECT COUNT(totalHour) FROM UserInfo WHERE username = '"+myId+"'";

            Cursor TotalHours = db.getData(strTotalHours);
            String countTotalHour = TotalHours.getString(0);
            total_hour = Integer.parseInt(countTotalHour);
        } catch (Exception ex){total_hour = 0;}
        return total_hour;
    }

    //trả về những thông tin mà người dùng mún giới thiệu bản thân
    public static String getUserOverview(Query db, String myId){
        String overview = "Here is my overview, Here is my overview, Here is my overview, Here is my overview";
        return overview;
    }

    //trả về danh sách request activity của user hiện tại (cái này t nghĩ là phải tạo 1 bảng mới là Request trong db)
    public static ArrayList<String> getActivityRequestListId(Query db, String myId){
        ArrayList<String> activity_request_id = new ArrayList<String>();
        activity_request_id.add("act20127333");
        activity_request_id.add("act20127306");
        return activity_request_id;
    }

    //trả về các notification của user
    public static ArrayList<String> getUserNotifications(Query db, String userId){
        ArrayList<String> notifications = new ArrayList<String>();
        notifications.add("Architecture Definition hạn là 14 tháng 12 năm 2022 lúc 20:00");
        notifications.add("Architecture Definition hạn là 14 tháng 12 năm 2022 lúc 20:00");
        notifications.add("Architecture Definition hạn là 14 tháng 12 năm 2022 lúc 20:00");
        return notifications;
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

//        String pathObject = String.valueOf(act.getActivityID());
//        ArrayList<String> values = new ArrayList<>();
//        values.add(act.getActivityID());
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
