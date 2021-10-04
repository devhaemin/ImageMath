package com.haemin.imagemathstudent.Retrofit;


import com.haemin.imagemathstudent.Data.*;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface RetrofitInterface {

    @GET("/video")
    Call<ArrayList<Video>> getVideoList(@Header("x-access-token") String accessToken);

    @GET("/video/lecture/{lectureSeq}")
    Call<ArrayList<Video>> getVideoListByLecture(@Header("x-access-token") String accessToken, @Path("lectureSeq")String lectureSeq);

    @GET("/file/video/attachedVideo")
    Call<ArrayList<ServerFile>> getVideoFileList(@Header("x-access-token") String accessToken, @Query("videoSeq") String videoSeq);

    @GET("/alarm")
//userSeq로 LectureSeqs찾고 해당 알람리스트 출력 수정필요
    Call<ArrayList<Alarm>> getAlarmList(@Header("x-access-token") String accessToken);

    @FormUrlEncoded
    @POST("/alarm/pushToken")
    Call<Void> setPushToken(@Header("x-access-token")String accessToken, @Field("fcmToken")String fcmToken);

    @GET("/qna/question")
    Call<ArrayList<Question>> getQuestionList(@Header("x-access-token")String accessToken);

    @GET("/qna/question/file/{questionSeq}")
    Call<ArrayList<ServerFile>> getQuestionFileList(@Header("x-access-token")String accessToken, @Path("questionSeq") String questionSeq);

    @GET("/qna/answer/{questionSeq}")
    Call<ArrayList<Answer>> getAnswerList(@Header("x-access-token")String accessToken,@Path("questionSeq") String questionSeq);

    @GET("qna/answer/file/{answerSeq}")
    Call<ArrayList<ServerFile>> getAnswerFileList(@Header("x-access-token")String accessToken, @Path("answerSeq") String answerSeq);

    @GET("/notice/file")
    Call<ArrayList<ServerFile>> getNoticeFileList(@Header("x-access-token")String accessToken, @Query("noticeSeq") String noticeSeq);

    @FormUrlEncoded
    @POST("/qna/question")
    Call<Question> writeQuestion(@Header("x-access-token")String accessToken, @Field("title")String title,@Field("contents")String contents);

    @Multipart
    @POST("/qna/editQuestion/{questionSeq}")
    Call<Void> addQuestionFile(@Header("x-access-token") String accessToken, @Path("questionSeq") String questionSeq, @Part MultipartBody.Part questionFile);




    @GET("/notice")
//수업에 해당하는 공지 출력 ok page구현
    Call<ArrayList<Notice>> getNoticeList(@Header("x-access-token") String accessToken, @Query("lectureSeq") String lectureSeq, @Query("page") int page);


    @GET("/assignment/{assignmentSeq}")
//assignment/{assignmentSeq}:1  이런식으로 경로작성 ok    내일 만들거 studentAssignment 필요함
    Call<Assignment> getAssignmentInfo(@Header("x-access-token") String accessToken, @Path("assignmentSeq") String assignmentSeq);

    //경로바꿈 ok
    @GET("/account/school")
    Call<ArrayList<School>> getSchoolList();

    @GET("/assignment/student")
    Call<ArrayList<StudentAssignment>> getStudentAssignmentList(@Header("x-access-token") String accessToken, @Query("page") int page);

    @GET("/assignment/student/{assignmentSeq}")
    Call<StudentAssignment> getStudentAssignmentInfo(@Header("x-access-token") String accessToken, @Path("assignmentSeq") String assignmentSeq);

    @Multipart
    @POST("/assignment/submit/{assignmentSeq}")
//이건 과제 이미지 올리는거였나? 일단 보류
    Call<Void> postPicture(@Header("x-access-token") String accessToken, @Path("assignmentSeq") String assignmentSeq, @Part MultipartBody.Part assignmentPart);
    // imageFile Parameter name : ?

    @GET("/lecture")
//수업 리스트 출력 ok  tutor : 모든수업, student : 듣는수업
    Call<ArrayList<Lecture>> getLectureList(@Query("exceptExpired")Boolean exceptExpired);

    @GET("/lecture/student")
    Call<ArrayList<Lecture>> getMyLectureList(@Header("x-access-token") String accessToken, @Query("exceptExpired") boolean exceptExpired, @Query("page") int page);


    /*
    academySeq, name, weekDay, time, totalDate, week, studentNum, userType
     */
    @FormUrlEncoded
    @POST("/lecture/add/student")
    Call<Void> requestAddLecture(@Header("x-access-token") String accessToken, @Field("lectureSeq") String lectureSeq, @Field("lectureName") String lectureName);


    //-----------------------------------------------------------
    @GET("/test/student")
//ok testAdm accessToken 확인 ->userSeq testSeq 둘다 일치하는거score포함
    Call<StudentTest> getStudentTestInfo(@Header("x-access-token") String accessToken, @Query("testAdmSeq") String testAdmSeq);

    @GET("/test/tutor")
    Call<Test> getTestInfo(@Header("x-access-token") String accessToken, @Query("testSeq") String testSeq);

    @GET("/test/student/{lectureSeq}")
//보류 testAdm userSeq, lectureSeq 해당 데이터리스트
    Call<ArrayList<StudentTest>> getUserTest(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq, @Query("page") int page);

    @GET("/test/result")
//수정필요  testAdm score포함 accessToken -> student: 상위 10개 , tutor : 전부 다
    Call<ArrayList<StudentTest>> getTestResults(@Header("x-access-token") String accessToken, @Query("lectureSeq") String lectureSeq, @Query("page") int page);


    @FormUrlEncoded
    @POST("/auth/register")
    Call<User> registerEmail(@FieldMap HashMap<String, String> registerField);
    /*
    name , school~~~~~~~
     */

    @GET("/auth/autologin")
    Call<User> loginWithToken(@Header("x-access-token") String accessToken);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<User> loginWithEmail(@Field("email") String email, @Field("password") String pw);

    /* 페이징 처리는 요청을 보낼때 0페이지면 1~20까지 데이터. 1페이지면 21~40 까지 데이터 */
    /* 테스트 부분 API 작업할때는 UI도 참고하고 질문하기! */
}