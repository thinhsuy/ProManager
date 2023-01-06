package com.example.promanager;


// NHỮNG GIÁ TRỊ TRONG TRANG NÀY HIỆN CHỈ LÀ GIẢ KHỞI TẠO! database sẽ có thể trả về những giá trị khác


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

        String strGetConnectedUserId = "SELECT usernameA FROM UserConnection WHERE usernameB = '"+myId+"'";

        Cursor connectedUserId = db.getData(strGetConnectedUserId);
        while(connectedUserId.moveToNext()){
            String username = connectedUserId.getString(0);
            user_of_connection_id.add(username);
        }
        return user_of_connection_id;
    }

    //trả về id (username) của người dùng hiện tại
    public static String getCurrentUserId(String username, String password){
        String userId = username;
        return userId;
        //DB không thể tự xử lí
        // !! cái này nó ảnh hưởng hết cả app nên rất qtrong!! cần giải quyết nhanh
        // ! => cái này lỗi t, sorry t quên nói là sau khi ktra login ở trên thì trả về id của ng login vào
        //      parameters sẽ có thể thay dổi thêm 2 tham chiếu là username và password
    }

    //-----------------------------------------------------
    public interface getAllOwnProjectsCallback {
        void onAllOwnProjectsReceived(ArrayList<Project_Database> all_projects);
    }

    //trả về toàn bộ id của project mà người dùng hiện phải chủ trì (manager, own)
    public static void getOwnProject(String myId, getAllOwnProjectsCallback callback){
        ArrayList<Project_Database> all_own_projects = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Project_Database project = dataSnapshot.getValue(Project_Database.class);
                    if(project.getProjectOwner().equals(myId)){
                        all_own_projects.add(project);
                    }
                }
                callback.onAllOwnProjectsReceived(all_own_projects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //-------------------------------------------------------


    //-------------------------------------------------------

    public interface getAllProjectsCallback {
        void onAllProjectsReceived(ArrayList<Project_Database> all_projects);
    }

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
    public static ArrayList<String> getCurrentResponProject(Query db, String myId){
        ArrayList<String> all_project_ids= new ArrayList<String>();
        String strGetOwnProject = "SELECT projectID FROM UserResponProject WHERE username = '"+myId+"'";

        Cursor CurrentResponProject = db.getData(strGetOwnProject);
        while(CurrentResponProject.moveToNext()){
            String projectID = CurrentResponProject.getString(0);
            all_project_ids.add(projectID);
        }
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

    //trả về 1 số thông tin quan trọng của activity
    public static Activity_Database getActivityById(Query db, String actId){
        Activity_Database activity = new Activity_Database();
        activity.setActivityID(actId);
        activity.setActivityName("Architecture definition");
        activity.setActivityDeadline("Deadline in 2 more days");
        String hoster = "ThinhSuy";
        activity.setActivityHost("Host by " + hoster);
        activity.setActivityDescribe("This activity needs you to continue on moving on your life, try by heart in yourself and improve it, maybe quite hard for you but never give it up. Wanna cry? Just cry! After that please stand up and do everything with 200% power. Until the day that we will meet again, it could be not a good moment for both, but at least, you already tried whole your heart ... if destiny give us a chance or if not, we still be good memories of each other and that moment in future, you would be proud of other. Be strong my girl, my boy!");
        activity.setActivityStatus("Not Finished");
        activity.setActivityFile("Empty");
        activity.setActivityAgreement("Rate: 0%");
        return activity;
    }

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
        int current_task;
        try{
            String strCountCurrentTask = "SELECT COUNT(currentTask) FROM UserInfo WHERE username = '"+myId+"'";

            Cursor CurrentTasks = db.getData(strCountCurrentTask);
            String countCurrentTask = CurrentTasks.getString(0);
            current_task = Integer.parseInt(countCurrentTask);
        } catch (Exception ex) {current_task =0;}
        return current_task;
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


    public static void Creation(Query db){
        String createUserInfo = "CREATE TABLE IF NOT EXISTS UserInfo(username VARCHAR(100) PRIMARY KEY," +
                "pass VARCHAR(100)," +
                "email VARCHAR(100)," +
                "phonenumber VARCHAR(10)," +
                "overview NVARCHAR(1000)," +
                "totalTasks smallint," +
                "totalHours smallint," +
                "currentTasks smallint," +
                "currentFinished smallint)";
        db.queryData(createUserInfo);

        String createProject = "CREATE TABLE IF NOT EXISTS Project(projectID VARCHAR(10) PRIMARY KEY," +
                "projectName NVARCHAR(100)," +
                "projectOwner VARCHAR(100)," +
                "projectDeadline date," +
                "projectDescribe NVARCHAR(1000)," +
                "projectPrivacy SMALLINT)";
        db.queryData(createProject);

        String createActivity = "CREATE TABLE IF NOT EXISTS Activity(activityID VARCHAR(10) PRIMARY KEY," +
                "activityName NVARCHAR(100)," +
                "activityDescribe NVARCHAR(1000)," +
                "activityDeadline date," +
                "activityHost VARCHAR(10)," +
                "activityFile VARCHAR(1000)," +
                "activityStatus VARCHAR(1000)," +
                "activityAgreement NVARCHAR(100))";
        db.queryData(createActivity);

        String createUserConnection = "CREATE TABLE IF NOT EXISTS UserConnection(" +
                "usernameA varchar(100)," +
                "usernameB varchar(100)," +
                "PRIMARY KEY (usernameA, usernameB))";
        db.queryData(createUserConnection);

        String createActivityInProject = "CREATE TABLE IF NOT EXISTS ActivityInProject(" +
                "projectID varchar(10)," +
                "activityID varchar(10)," +
                "PRIMARY KEY (projectID, activityID))";
        db.queryData(createActivityInProject);

        String createUserResponActivity = "CREATE TABLE IF NOT EXISTS UserResponActivity(" +
                "activityID varchar(10)," +
                "username varchar(100)," +
                "PRIMARY KEY (activityID, username))";
        db.queryData(createUserResponActivity);

        String createUserResponProject = "CREATE TABLE IF NOT EXISTS UserResponProject(" +
                "username varchar(100)," +
                "projectID varchar(10)," +
                "PRIMARY KEY (username, projectID))";
        db.queryData(createUserResponProject);

        String createAchieveActivity = "CREATE TABLE IF NOT EXISTS AchieveActivity(" +
                "username varchar(100)," +
                "activityID varchar(10)," +
                "PRIMARY KEY (username, activityID))";
        db.queryData(createAchieveActivity);

        // ADD DATA
        // UserInfo TABLE
        db.queryData("INSERT INTO UserInfo " +
                "VALUES ('username1', '123456', 'abc123@gmail.com', '0893483493', 'US1', 'ABC', 10, 30, 3, 7)");
        db.queryData("INSERT INTO UserInfo " +
                "VALUES ('username2', '123456', 'def456@gmail.com', '0399274829', 'US2', 'DEF', 5, 24, 1, 4)");
        db.queryData("INSERT INTO UserInfo " +
                "VALUES ('username3', '123456', 'xyz278@gmail.com', '0773827283', 'US3', 'CCC', 8, 20, 5, 3)");

        //Project TABLE
        db.queryData("INSERT INTO Project VALUES ('project1', 'PA1', 'username1', '2022-11-30', 'This is test describe', 'private')");
        db.queryData("INSERT INTO Project VALUES ('project2', 'PA2', 'username2', '2023-05-05', 'This is test describe', 'public')");

        //Activity TABLE
        db.queryData("INSERT INTO Activity VALUES ('activity1', 'CNPM_Task1', 'dosth', '2023-10-01', 'username1', NULL, NULL, NULL)");
        db.queryData("INSERT INTO Activity VALUES ('activity2', 'CNPM_Task2', 'dosth2', '2022-12-12', 'username3', NULL, NULL, NULL)");
        db.queryData("INSERT INTO Activity VALUES ('activity3', 'WEB_Task1', 'dosth3', '2022-12-15', 'username3', NULL, NULL, NULL)");

        //UserConnection TABLE
        db.queryData("INSERT INTO UserConnection VALUES ('username1', 'username2')");
        db.queryData("INSERT INTO UserConnection VALUES ('username1', 'username3')");
        db.queryData("INSERT INTO UserConnection VALUES ('username2', 'username3')");

        //ActivityInProject TABLE
        db.queryData("INSERT INTO ActivityInProject VALUES ('project1', 'activity2')");
        db.queryData("INSERT INTO ActivityInProject VALUES ('project1', 'activity3')");

        //UserResponActivity TABLE
        db.queryData("INSERT INTO UserResponActivity VALUES ('activity1', 'username1')");
        db.queryData("INSERT INTO UserResponActivity VALUES ('activity2', 'username1')");
        db.queryData("INSERT INTO UserResponActivity VALUES ('activity2', 'username3')");

        //UserResponProject TABLE
        db.queryData("INSERT INTO UserResponProject VALUES ('username3', 'project1')");
        db.queryData("INSERT INTO UserResponProject VALUES ('username3', 'project2')");

        //AchieveActivity TABLE
        db.queryData("INSERT INTO AchieveActivity VALUES ('username1', 'activity3')");
        db.queryData("INSERT INTO AchieveActivity VALUES ('username3', 'activity3')");
    }

    public static void dropSQL(Query db, String dbName){
        db.queryData("drop database " + dbName);
    }
}
