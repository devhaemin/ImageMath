package com.haemin.imagemathtutor.Retrofit;


import com.haemin.imagemathtutor.Data.*;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("/push/postPush")
    Call<Void> postPushAlarm(@Header("x-access-token")String accessToken, @Field("userSeq")String userSeq, @Field("message")String message);

    @GET("/qna/question")
    Call<ArrayList<Question>> getQuestionList(@Header("x-access-token")String accessToken);

    @GET("/qna/question/file/{questionSeq}")
    Call<ArrayList<ServerFile>> getQuestionFileList(@Header("x-access-token")String accessToken, @Path("questionSeq") String questionSeq);

    @GET("/qna/answer/{questionSeq}")
    Call<ArrayList<Answer>> getAnswerList(@Header("x-access-token")String accessToken,@Path("questionSeq") String questionSeq);

    @GET("qna/answer/file/{answerSeq}")
    Call<ArrayList<ServerFile>> getAnswerFileList(@Header("x-access-token")String accessToken, @Path("answerSeq") String answerSeq);

    @FormUrlEncoded
    @POST("/qna/answer")
    Call<Answer> writeAnswer(@Header("x-access-token")String accessToken, @Field("title")String title, @Field("contents")String contents, @Field("questionSeq")String questionSeq);

    @Multipart
    @POST("/qna/editAnswer/{answerSeq}")
    Call<Void> addAnswerFile(@Header("x-access-token") String accessToken, @Path("answerSeq") String answerSeq, @Part MultipartBody.Part answerFile);


    @GET("/notice")
//수업에 해당하는 공지 출력 ok page구현
    Call<ArrayList<Notice>> getNoticeList(@Header("x-access-token") String accessToken, @Query("lectureSeq") String lectureSeq);

    @GET("/notice/file")
    Call<ArrayList<ServerFile>> getNoticeFileList(@Header("x-access-token")String accessToken, @Query("noticeSeq") String noticeSeq);

    @FormUrlEncoded
    @POST("/notice")
    Call<Notice> addNotice(@Header("x-access-token") String accessToken, @Field("title") String title, @Field("contents") String contents, @Field("lectureSeq") String lectureSeq);
    // imageFiles Parameter name : ?, normalFiles Parameter name : ?

    @DELETE("/notice/delete/{noticeSeq}")
    Call<Void> deleteNotice(@Header("x-access-token")String accessToken, @Path("noticeSeq")String noticeSeq);
    @Multipart
    @POST("/notice/{noticeSeq}")
    Call<Void> addNoticeFile(@Header("x-access-token") String accessToken, @Path("noticeSeq") String noticeSeq, @Part MultipartBody.Part noticeFile);

//    @GET("/account/school")
////경로바꿈 ok
//    Call<ArrayList<School>> getSchoolList();

    @GET("/account/academy")
    Call<ArrayList<Academy>> getAcademyList();

    @GET("/account/{userSeq}")
    Call<User> getUserInfo(@Header("x-access-token")String accessToken, @Path("userSeq")String userSeq);

    @GET("/assignment/tutor/submit/{assignmentSeq}")
    Call<ArrayList<ServerFile>> getAssignmentSubmitFiles(@Header("x-access-token")String accessToken,@Path("assignmentSeq")String assignmentSeq,@Query("userSeq") String userSeq);

    @GET("/assignment/tutor/{assignmentSeq}")
    Call<ArrayList<StudentAssignment>> getStudentAssignmentList(@Header("x-access-token") String accessToken,@Path("assignmentSeq")String assignmentSeq, @Query("page") int page);

    @GET("/assignment/tutor/sub/byStudent")
    Call<ArrayList<StudentAssignment>> getStudentSubmitAssignmentList(@Header("x-access-token")String accessToken,@Query("userSeq") String userSeq);

    @DELETE("/assignment/delete/answerFile/{fileSeq}")
    Call<Void> deleteAnswerFile(@Header("x-access-token")String accessToken, @Path("fileSeq")String fileSeq);
    @GET("/assignment")
//ok  assignmentInfo
    Call<ArrayList<Assignment>> getAssignmentList(@Header("x-access-token") String accessToken, @Query("page") int page);

    @FormUrlEncoded
    @POST("/assignment/add")
    Call<Assignment> postAssignmentInfo(@Header("x-access-token") String accessToken, @FieldMap Map<String, String> assignmentField);

    @FormUrlEncoded
    @POST("/assignment/edit")
    Call<Assignment> editAssignmentInfo(@Header("x-access-token") String accessToken, @FieldMap Map<String, String> assignmentField);

    @Multipart
    @POST("/assignment/add/answerFile/{assignmentSeq}")
    Call<Void> uploadAssignmentAnswer(@Header("x-access-token")String accessToken,@Path("assignmentSeq")String assignmentSeq, @Part MultipartBody.Part part);

    @GET("/assignment/{assignmentSeq}")
//assignment/{assignmentSeq}:1  이런식으로 경로작성 ok    내일 만들거 studentAssignment 필요함
    Call<Assignment> getAssignmentInfo(@Header("x-access-token")String accessToken, @Path("assignmentSeq") String assignmentSeq);

    @FormUrlEncoded
    @PATCH("/assignment/assignmentAdm/{assignmentAdmSeq}")
    Call<Void> setAssignmentAdmStatus(@Header("x-access-token")String accessToken, @Path("assignmentAdmSeq") String assignmentAdmSeq, @Field("submitState")int submitState);

    @DELETE("/assignment/{assignmentSeq}")
    Call<Void> deleteAssignmentInfo(@Header("x-access-token")String accessToken, @Path("assignmentSeq")String assignmentSeq);

    @GET("/lecture")
//수업 리스트 출력 ok  tutor : 모든수업, student : 듣는수업
    Call<ArrayList<Lecture>> getLectureList(@Query("exceptExpired") Boolean exceptExpired);

    @FormUrlEncoded
    @PATCH("/lecture/{lectureSeq}")
    Call<Void> setExpiredLecture(@Header("x-access-token")String accessToken, @Path("lectureSeq")String lectureSeq, @Field("isExpired") int isExpired);

    @DELETE("/lecture/{lectureSeq}")
    Call<Void> deleteLecture(@Header("x-access-token")String accessToken, @Path("lectureSeq")String lectureSeq);

    @FormUrlEncoded
    @POST("/lecture/add")
//경로바꿈 ok
    Call<Void> addLecture(@Header("x-access-token") String accessToken, @FieldMap Map<String, String> lectureField);

    @GET("/lecture/tutor")
    Call<ArrayList<Lecture>> getStudentLectureList(@Header("x-access-token")String accessToken,@Query("userSeq")String userSeq);
    /*
    academySeq, name, weekDay, time, totalDate, week, studentNum, userType
     */
    @FormUrlEncoded
    @POST("/lecture/add/student")
    Call<Void> requestAddLecture(@Header("x-access-token") String accessToken, @Field("lectureSeq") String lectureSeq, @Field("lectureName") String lectureName);

    //------------------------------------------------------------------------
    @GET("/lecture/recognition/{lectureSeq}")
// ok mysql contain 경로바꿈 이거랑 /student/{lectureSeq}랑 구분이 안가네;;
    Call<ArrayList<User>> getRequestUserList(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq, @Query("page") int page);

    @FormUrlEncoded
    @PATCH("/lecture/recognition/{lectureSeq}")
//보류
    Call<Void> recognizeStudent(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq, @Field("studentSeq") String studentSeq, @Field("isAccept") boolean isAccept);

    @GET("/lecture/student/{lectureSeq}")
//보류  mysql contain 내일 확인바람
    Call<ArrayList<User>> getStudentList(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq);

    @DELETE("/lecture/student/{lectureSeq}/{userSeq}")
    Call<Void> deleteStudent(@Header("x-access-token")String accessToken, @Path("lectureSeq")String lectureSeq, @Path("userSeq") String userSeq);

    @GET("/test/student/{lectureSeq}")
//보류 testAdm userSeq, lectureSeq 해당 데이터리스트
    Call<ArrayList<StudentTest>> getUserTest(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq, @Query("page") int page);

    //-----------------------------------------------------------
    @GET("/test/student")
//ok testAdm accessToken 확인 ->userSeq testSeq 둘다 일치하는거score포함
    Call<StudentTest> getStudentTestInfo(@Header("x-access-token") String accessToken, @Query("testSeq") String testSeq);

    @GET("/test/tutor/{lectureSeq}")
//ok testInfo
    Call<ArrayList<Test>> getTutorTestList(@Header("x-access-token") String accessToken, @Path("lectureSeq") String lectureSeq, @Query("page") int page);

    @GET("/test/tutor")
//수정필요 testInfo  score제외
    Call<Test> getTutorTestInfo(@Header("x-access-token") String accessToken, @Query("testSeq") String testSeq);

    @GET("/test/result")
//수정필요  testAdm score포함 accessToken -> student: 상위 10개 , tutor : 전부 다
    Call<ArrayList<StudentTest>> getTestResults(@Header("x-access-token") String accessToken, @Query("testSeq") String testSeq, @Query("page") int page);

    @FormUrlEncoded
    @POST("/test/add")
    Call<Test> addTestInfo(@Header("x-access-token")String accessToken,@FieldMap HashMap<String,String> testField);

    @Multipart
    @POST("/test/answer/{testSeq}")
    Call<Void> addTestAnswerFile(@Header("x-access-token")String accessToken,@Path("testSeq") String testSeq, @Part MultipartBody.Part answerFile);

    @Multipart
    @POST("/test/tutor/uploadXls/{testSeq}")
    Call<Void> postTestWithExcel(@Header("x-access-token") String accessToken, @Path("testSeq") String testSeq,@Part("xlsLength")int xlsLength, @Part("lectureSeq")String lectureSeq,@Part MultipartBody.Part excelFile);


    @DELETE("/test/{testSeq}")
    Call<Void> deleteTestInfo(@Header("x-access-token")String accessToken, @Path("testSeq")String testSeq);

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