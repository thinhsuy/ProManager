package com.example.promanager;


// NHỮNG GIÁ TRỊ TRONG TRANG NÀY HIỆN CHỈ LÀ GIẢ KHỞI TẠO! database sẽ có thể trả về những giá trị khác


import java.util.ArrayList;
import java.util.Arrays;

public class MyDatabase {
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

    //trả về toàn bộ tên của project mà người dùng hiện tham gia
    public static String[] getCurrentResponProject(String myId){
        String[] all_project_id = {};
        return all_project_id;
    }

    public static ActivityClass getActivityById(String actId){
        ActivityClass activity = new ActivityClass();
        activity.activity_header = "Architecture definition";
        activity.deadline = "Deadline in 2 more days";
        return activity;
    }

    //trả về 1 số thông tin quan trọng của project
    //proId truyền vào tam là "20127306"
    public static ProjectClass getProjectById(String proId){
        ProjectClass project = new ProjectClass();
        project.project_header = "Projects Manager";
        //dười này là cách dùng 1 array dynamic, ở java phân biệt rõ dynamic và static array lắm nên chấp nhận ik
        project.activityIdList = new ArrayList<String>(Arrays.asList("20127333", "20127306"));
        for (int i=0; i< project.activityIdList.size(); i++){
            project.addActivityList(getActivityById(project.activityIdList.get(i)));
        }
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
