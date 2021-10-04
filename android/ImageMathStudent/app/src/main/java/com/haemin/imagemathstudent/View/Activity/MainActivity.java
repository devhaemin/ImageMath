package com.haemin.imagemathstudent.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.haemin.imagemathstudent.Adapter.MainPagerAdapter;
import com.haemin.imagemathstudent.FirebaseUsage.MyFirebaseMessagingService;
import com.haemin.imagemathstudent.R;
import com.haemin.imagemathstudent.View.UI.TutorToolbar;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private static final String TAG = "PUSH_TOKEN_REFRESH";
    @BindView(R.id.toolbar)
    TutorToolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tab_main)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        refreshPushToken();

        ButterKnife.bind(this); //BindView by ID
        setSupportActionBar(toolbar); //set Toolbar for appbar

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), MainPagerAdapter.FRAGMENT_COUNT);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager,true);

        toolbar.setNotificationOnClickListener(v -> {
            startActivity(new Intent(this, AlarmActivity.class));
        });
    }

    private void refreshPushToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    // Log and toast
                    Log.d(TAG, "token :"+ token);
                    MyFirebaseMessagingService service = new MyFirebaseMessagingService();
                    service.onNewToken(token);
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
