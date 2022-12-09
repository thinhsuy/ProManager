package com.example.promanager;


// NHỮNG GIÁ TRỊ TRONG TRANG NÀY HIỆN CHỈ LÀ GIẢ KHỞI TẠO! database sẽ có thể trả về những giá trị khác


import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MyDatabase {
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
    public static String[] getResponsibilityUserId(String actId){
        String[] user_of_respon_id = {"20127306", "20127333", "20127306", "20127333", "20127306"};
        return user_of_respon_id;
    }

    //trả về id user khác connection với user
    public static String[] getConnectedUserId(String myId){
        String[] user_of_connection_id = {"20127333", "20127306", "20127306", "20127333", "20127306", "20127333", "20127306"};
        return user_of_connection_id;
    }

    //trả về true khi set thông tin ng dùng sign up tới database thành công
    public static boolean setDatabaseRegister(String fullname, String username, String password, String confirm, String about){
        return true;
    }

    //trả về true false khi kiểm tra dữ liệu login của người dùng
    public static boolean checkLogin(String username, String password){
        return true;
    }

    //trả về id (username) của người dùng hiện tại
    public static String getCurrentUserId(){
        String userId = "20127333";
        return userId;
    }

    //trả về toàn bộ id của project mà người dùng hiện phải chủ trì (manager, own)
    public static String[] getOwnProject(String myId){
        String[] all_project_id = {"20127306", "20127333"};
        return all_project_id;
    }

    //trả về toàn bộ id của project mà người dùng hiện KHÔNG tham gia và KHÔNG phải chủ trì
    public static String[] getAllProject(String myId){
        String[] all_project_id = {"20127306", "20127333"};
        return all_project_id;
    }

    //trả về toàn bộ id của project mà người dùng hiện tham gia
    public static String[] getCurrentResponProject(String myId){
        String[] all_project_id = {"20127306", "20127333"};
        return all_project_id;
    }

    //trả về danh sách activityId của 1 project
    public static ArrayList<String> getActivityIdListByProjectId(String proId){
        String[] listId = {"20127306", "20127333"};

        //dười này là cách dùng 1 array dynamic, ở java phân biệt rõ dynamic và static array lắm nên chấp nhận ik
        return new ArrayList<String>(Arrays.asList(listId));
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

    //trả về số task mà người dùng còn trong deadline
    public static int getCurrentTasks(String myId){
        int current_task = 6;
        return current_task;
    }

    //trả về số task mà người dùng còn trong deadline nhưng hoàn thành rồi
    public static int getCurrentFinishedTasks(String myId){
        int finished_task = 5;
        return finished_task;
    }

    //trả về tổng số task mà người dùng hoàn thành từ trc tới giờ
    public static int getTotalTasks(String myId){
        int total_task = 16;
        return total_task;
    }

    //trả về số thời gian người dùng tham gia từ trc tới giờ
    //trong activity sẽ có thời gian sẽ có thời gian deadline, cứ cộng thời gian lại là dc
    public static int getTotalHour(String myId){
        int total_hour = 300;
        return total_hour;
    }

    //trả về những thông tin mà người dùng mún giới thiệu bản thân
    public static String getUserOverview(String myId){
        String overview = "Here is my overview, Here is my overview, Here is my overview, Here is my overview";
        return overview;
    }
}
