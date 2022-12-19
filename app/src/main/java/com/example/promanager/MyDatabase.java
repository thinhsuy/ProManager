package com.example.promanager;


// NHỮNG GIÁ TRỊ TRONG TRANG NÀY HIỆN CHỈ LÀ GIẢ KHỞI TẠO! database sẽ có thể trả về những giá trị khác


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MyDatabase {
    public static Query db;

    //cái này chưa cần làm!
    public static ImageView getAvatarById(Context context, String userId, String size){
        ImageView image = new ImageView(context);
        int image_size;
        if (size=="small") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_small);
        else if (size=="tiny") image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_tiny);
        else image_size = (int)context.getResources().getDimension(R.dimen.avatar_size_medium);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(image_size, image_size);
        params.setMargins(0,0,25,0);
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        String link = getLinkAvatarById(userId);
        new MyInternet.DownloadImageTask(image).execute(link);
        image.setBackgroundResource(R.drawable.avatar);
        return image;
    }

    //cái này chưa cần làm!
    public static String getLinkAvatarById(String userId){
        String link = "https://banner2.cleanpng.com/20180625/req/kisspng-computer-icons-avatar-business-computer-software-user-avatar-5b3097fcae25c3.3909949015299112927133.jpg";
        return link;
    }

    //trả về số id user hiện respon cho activity
    public static ArrayList<String> getResponsibilityUserId(String actId){
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
    public static ArrayList<String> getConnectedUserId(String myId){
        ArrayList<String> user_of_connection_id = new ArrayList<String>();

        String strGetConnectedUserId = "SELECT usernameA FROM UserConnection WHERE usernameB = '"+myId+"'";

        Cursor connectedUserId = db.getData(strGetConnectedUserId);
        while(connectedUserId.moveToNext()){
            String username = connectedUserId.getString(0);
            user_of_connection_id.add(username);
        }
        return user_of_connection_id;
    }

    //trả về true khi set thông tin ng dùng sign up tới database thành công
    public static boolean setDatabaseRegister(String fullname, String username, String password, String confirm, String about){
        return true;
        //DB không thể tự xử lí
        //!! cái này qtrong nên cần giải quyết nhanh
        //! => cái này giống login, chỉ cần add thêm các thông tin vào như lệnh insert table thôi
    }

    //trả về true false khi kiểm tra dữ liệu login của người dùng
    public static boolean checkLogin(String username, String password){
        //DB không thể tự xử lí
        //!! cái này qtrong nên cần giải quyết nhanh
        // ! => sao ko thể xử lí nhỉ? m cứ so sánh password và username trùng với nhau thì trả về true thôi
        //      giống ktra password bth
        return true;
    }

    //trả về id (username) của người dùng hiện tại
    public static String getCurrentUserId(){
        String userId = "20127333";
        return userId;
        //DB không thể tự xử lí
        // !! cái này nó ảnh hưởng hết cả app nên rất qtrong!! cần giải quyết nhanh
        // ! => cái này lỗi t, sorry t quên nói là sau khi ktra login ở trên thì trả về id của ng login vào
        //      parameters sẽ có thể thay dổi thêm 2 tham chiếu là username và password
    }

    //trả về toàn bộ id của project mà người dùng hiện phải chủ trì (manager, own)
    public static ArrayList<String> getOwnProject(String myId){
        ArrayList<String> all_own_projects= new ArrayList<String>();

        String strGetOwnProject = "SELECT projectID FROM Project WHERE projectOwner = '"+myId+"'";

        Cursor OwnProject = db.getData(strGetOwnProject);
        while(OwnProject.moveToNext()){
            String projectID = OwnProject.getString(0);
            all_own_projects.add(projectID);
        }

        return all_own_projects;
    }

    //trả về toàn bộ id của project mà người dùng hiện KHÔNG tham gia và KHÔNG phải chủ trì
    public static String[] getAllProject(String myId){
        String[] all_project_id = {"20127306", "20127333"};
        return all_project_id;
        // -> KHÔNG tham gia với KHÔNG chủ trì thì vô số, query cái này để làm gì?
        // ! => caí này trả về toàn bộ project trong newfeeds cho ng dùng tìm kiếm,
        //      giống như việc trang facebook của m sẽ trả về các bài post ko phải của m phải ko?
        //      cái này tạo ra 1 môi trường project mà ng dùng chưa tham gia de họ có cơ hội tham gia
        //      Nhìu là dúng rồi vì càng nhìu càng tốt!!
    }

    //trả về toàn bộ id của project mà người dùng hiện tham gia
    public static ArrayList<String> getCurrentResponProject(String myId){

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
    public static ArrayList<String> getActivityIdListByProjectId(String proId){

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
    public static ActivityClass getActivityById(String actId){
        ActivityClass activity = new ActivityClass();
        activity.activity_header = "Architecture definition";
        activity.deadline = "Deadline in 2 more days";
        String hoster = "ThinhSuy";
        activity.hoster = "Host by " + hoster;
        return activity;
    }

    //trả về 1 số thông tin quan trọng của project
    //proId truyền vào tam là "20127306"
    public static ProjectClass getProjectById(String proId){
        ProjectClass project = new ProjectClass();
        project.project_header = "Projects Manager";

        //dười này là cách dùng 1 array dynamic, ở java phân biệt rõ dynamic và static array lắm nên chấp nhận ik
        project.activityIdList = getActivityIdListByProjectId(proId);

        return project;
    }

    //trả về số task mà người dùng còn trong deadline (CurrentTasks)
    public static int getCurrentTasks(String myId){
        int current_task;

        String strCountCurrentTask = "SELECT COUNT(currentTask) FROM UserInfo WHERE username = '"+myId+"'";

        Cursor CurrentTasks = db.getData(strCountCurrentTask);
        String countCurrentTask = CurrentTasks.getString(0);
        current_task = Integer.parseInt(countCurrentTask);

        return current_task;
    }

    //trả về số task mà người dùng còn trong deadline nhưng hoàn thành rồi
    public static int getCurrentFinishedTasks(String myId){
        int finished_task;

        String strCountCurrentFinishedTasks = "SELECT COUNT(currentFinished) FROM UserInfo WHERE username = '"+myId+"'";

        Cursor CurrentFinishedTasks = db.getData(strCountCurrentFinishedTasks);
        String countCurrentFinishedTask = CurrentFinishedTasks.getString(0);
        finished_task = Integer.parseInt(countCurrentFinishedTask);

        return finished_task;
    }

    //trả về tổng số task mà người dùng hoàn thành từ trc tới giờ
    public static int getTotalTasks(String myId){
        int total_task = 16;

        String strTotalTasks = "SELECT COUNT(totalTasks) FROM UserInfo WHERE username = '"+myId+"'";

        Cursor TotalTasks = db.getData(strTotalTasks);
        String countTotalTask = TotalTasks.getString(0);
        total_task = Integer.parseInt(countTotalTask);

        return total_task;
    }

    //trả về số thời gian người dùng tham gia từ trc tới giờ
    //trong activity sẽ có thời gian sẽ có thời gian deadline, cứ cộng thời gian lại là dc
    //? -> Chẳng lẽ cứ tham gia 1 activity là thời gian tham gia của người đó phải cộng thêm khoảng thời
    //     gian từ lúc bắt đầu cho đến khi deadline à, thời gian tgia này nên là tgian nhận cho đến khi làm xong
    //     nên để update sau này, chứ ko cần thiết làm vậy
    //! => Thời gian chỉ là thời gian project user trong deadline thôi, tức là tổng tg deadline user có dc
    //      vd: userA có 1 deadline 2 ngày, 1 deadline 3 ngày. Vậy tổng tg là 5 ngày = 120h
    public static int getTotalHour(String myId){
        int total_hour = 300;

        String strTotalHours = "SELECT COUNT(totalHour) FROM UserInfo WHERE username = '"+myId+"'";

        Cursor TotalHours = db.getData(strTotalHours);
        String countTotalHour = TotalHours.getString(0);
        total_hour = Integer.parseInt(countTotalHour);

        return total_hour;
    }

    //trả về những thông tin mà người dùng mún giới thiệu bản thân
    public static String getUserOverview(String myId){
        String overview = "Here is my overview, Here is my overview, Here is my overview, Here is my overview";
        return overview;
    }
}
