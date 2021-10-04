package com.haemin.imagemathstudent.View.RegisterProcess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.Data.Lecture;
import com.haemin.imagemathstudent.Data.School;
import com.haemin.imagemathstudent.Data.User;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.SingleTon.AppString;
import com.haemin.imagemathstudent.SingleTon.GlobalApplication;
import com.haemin.imagemathstudent.View.UI.ListPickerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_email_confirm)
    Button btnEmailConfirm;
    @BindView(R.id.btn_phone_confirm)
    Button btnPhoneConfirm;
    @BindView(R.id.btn_code_confirm)
    Button btnCodeConfirm;
    @BindView(R.id.btn_search_lecture)
    Button btnSearchLecture;
    @BindView(R.id.btn_add_lecture)
    Button btnAddLecture;
    @BindView(R.id.btn_search_school)
    Button btnSearchSchool;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_birthYear)
    EditText editBirthYear;
    @BindView(R.id.edit_birthMonth)
    EditText editBirthMonth;
    @BindView(R.id.edit_birthDay)
    EditText editBirthDay;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_pw)
    EditText editPw;
    @BindView(R.id.edit_pw_re)
    EditText editPwRe;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.edit_lecture)
    EditText editLecture;
    @BindView(R.id.edit_school)
    EditText editSchool;
    @BindView(R.id.edit_student_code)
    EditText editStudentCode;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.viewholder_lecture)
    LinearLayout viewHolderLecture;
    @BindView(R.id.text_pw_re_confirm)
    TextView textPwReConfirm;

    @BindView(R.id.toggle_gender_female)
    CheckBox toggleGenderFemale;
    @BindView(R.id.toggle_gender_male)
    CheckBox toggleGenderMale;

    boolean isRecognized = false;
    String schoolSeq = "";
    String lectureSeq = "";
    String lectureSeqs = "";
    HashMap<String, String> registerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lectureSeqs = "0/";

        registerField = new HashMap<>();
        ButterKnife.bind(this);
        bindListener();

    }

    private void bindListener() {

        btnPhoneConfirm.setOnClickListener(v -> {
            showToast("아직 준비중인 기능입니다. 1234를 입력해주세요.");
            editCode.setEnabled(true);
        });

        btnCodeConfirm.setOnClickListener(v -> {
            if (editCode.getText().toString().equals("")) {
                showToast(AppString.ERROR_CODE_EMPTY);
            } else {
                if (editCode.getText().toString().equals("1234")) {
                    showToast(AppString.SUCCESS_CODE_RECOGNITION);
                    isRecognized = true;
                } else {
                    showToast(AppString.ERROR_CODE_ERROR);
                }
            }
        });

        btnSearchLecture.setOnClickListener(v -> {
            GlobalApplication.getAPIService().getLectureList(true).enqueue(new Callback<ArrayList<Lecture>>() {
                @Override
                public void onResponse(Call<ArrayList<Lecture>> call, Response<ArrayList<Lecture>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        showLectureDialog(response.body());
                    } else {
                        showToast(AppString.ERROR_LOAD_LECTURE_LIST);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Lecture>> call, Throwable t) {
                    showToast(AppString.ERROR_NETWORK_MESSAGE);
                }
            });
        });

        btnSearchSchool.setOnClickListener(v -> {
            GlobalApplication.getAPIService().getSchoolList().enqueue(new Callback<ArrayList<School>>() {
                @Override
                public void onResponse(Call<ArrayList<School>> call, Response<ArrayList<School>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        showSchoolDialog(response.body());
                    } else {
                        showToast(AppString.ERROR_LOAD_SCHOOL_LIST);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<School>> call, Throwable t) {
                    showToast(AppString.ERROR_NETWORK_MESSAGE);
                }
            });
        });

        /*
        btnAddLecture.setOnClickListener(v -> {
            if (!editLecture.getText().toString().equals("")) {
                //addLecture(lectureSeq, editLecture.getText().toString());
                lectureSeq = null;
                editLecture.setText("");
            } else {
                Toast.makeText(this, "수업을 검색하여 추가해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
*/
        btnRegister.setOnClickListener(v -> {
            if (isAbleToRegister()) {
                GlobalApplication.getAPIService().registerEmail(registerField).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200 && response.body() != null) {
                            User user = response.body();
                            GlobalApplication.setAccessToken(user.getAccessToken());
                            showToast(AppString.SUCCESS_REGISTER);
                            RegisterSuccessActivity.start(RegisterActivity.this,user.getUserSeq()+"");
                            finish();
                        } else {
                            showToast("이미 존재하는 이메일입니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        showToast(AppString.ERROR_NETWORK_MESSAGE);
                        Log.e("RegisterActivity",AppString.ERROR_NETWORK_MESSAGE , t);
                    }
                });
            }
        });

        toggleGenderMale.setOnClickListener(v -> {
            if (toggleGenderMale.isChecked())
                toggleGenderFemale.setChecked(false);
        });
        toggleGenderFemale.setOnClickListener(v -> {
            if (toggleGenderFemale.isChecked())
                toggleGenderMale.setChecked(false);
        });

    }

    private boolean isAbleToRegister() {
        if (editName.getText().toString().equals("")) {
            showToast("이름을 입력해주세요.");
            editName.requestFocus();
            return false;
        } else {
            registerField.put("name", editName.getText().toString());
        }
        if (editBirthYear.getText().toString().equals("") || editBirthMonth.getText().toString().equals("") || editBirthDay.getText().toString().equals("")) {
            showToast("생년월일을 입력해주세요.");
            editBirthYear.requestFocus();
            return false;
        } else {
            registerField.put("birthday", editBirthYear.getText().toString() + editBirthMonth.getText().toString() + editBirthDay.getText().toString());
        }
        if (editEmail.getText().toString().equals("")) {
            showToast("이메일을 입력해주세요.");
            editEmail.requestFocus();
            return false;
        } else {
            registerField.put("email", editEmail.getText().toString());
        }
        if (editPw.getText().toString().equals("")) {
            showToast("비밀번호를 입력해주세요.");
            editPw.requestFocus();
            return false;
        } else if (!editPw.getText().toString().equals(editPwRe.getText().toString())) {
            showToast("비밀번호와 비밀번호 확인이 다릅니다.");
            editPwRe.requestFocus();
            textPwReConfirm.setVisibility(View.VISIBLE);
            return false;
        } else if (editPw.getText().toString().equals(editPwRe.getText().toString())) {
            textPwReConfirm.setVisibility(View.INVISIBLE);
            registerField.put("password", editPw.getText().toString());
        } else {
            registerField.put("password", editPw.getText().toString());
        }
        if (editPhone.getText().toString().equals("")) {
            showToast("휴대폰 번호를 입력해주세요.");
            editPhone.requestFocus();
            return false;
        }
        if (!isRecognized) {
            showToast("휴대폰 인증을 완료해주세요.");
            editPhone.requestFocus();
            return false;
        } else {
            registerField.put("phone", editPhone.getText().toString());
        }
        if (editSchool.getText().toString().equals("")) {
            showToast("학교를 입력해주세요.");
            editSchool.requestFocus();
            return false;
        } else {
            registerField.put("schoolName", editSchool.getText().toString());
        }

        if (toggleGenderMale.isChecked()) {
            registerField.put("gender", "0");
        } else {
            registerField.put("gender", "1");
        }
        registerField.put("userType", "student");
        if(editStudentCode.getText().toString().equals("")){
            showToast("학생코드를 입력해주세요.");
            editStudentCode.requestFocus();
            return false;
        }else{
            registerField.put("studentCode",editStudentCode.getText().toString());
        }
        return true;
    }
/*
    private void addLecture(String lectureSeq, String lectureName) {
        if (lectureSeqs.equals("")) {
            lectureSeqs = lectureSeq;
        } else {
            lectureSeqs += ("," + lectureSeq);
        }
        TextView textView = new TextView(this);
        textView.setText(lectureName);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,10);
        textView.setTextSize(18);
        textView.setLayoutParams(params);
        textView.setTextColor(Color.BLACK);
        viewHolderLecture.addView(textView);
    }
    */

    private void showLectureDialog(ArrayList<Lecture> lectures) {
        ListPickerDialog<Lecture> lectureDialog = new ListPickerDialog<>(lectures, "수업을 선택해주세요.");
        lectureDialog.setOnItemClickListener(data -> {
            lectureSeq = "" + data.getLectureSeq();
            editLecture.setText(data.getName());
        });
        lectureDialog.show(getSupportFragmentManager(), "searchLectureDialog");
    }

    private void showSchoolDialog(ArrayList<School> body) {
        ListPickerDialog<School> schoolDialog = new ListPickerDialog<>(body, "학교를 선택해주세요.");
        schoolDialog.setOnItemClickListener(data -> {
            schoolSeq = "" + data.getSchoolSeq();
            editSchool.setText(data.getSchoolName());
        });
        schoolDialog.show(getSupportFragmentManager(), "searchSchoolDialog");
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
