package com.haemin.imagemathstudent.View.RegisterProcess;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathstudent.R;

public class RegisterTermsActivity extends AppCompatActivity {

    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.webview)
    WebView termView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_terms);

        ButterKnife.bind(this);

        btnAccept.setOnClickListener(v -> {
            GoToNext();
        });
        termView.loadUrl("http://ec2-54-180-115-237.ap-northeast-2.compute.amazonaws.com:3000/auth/terms");
        termView.setWebChromeClient(new WebChromeClient());

    }

    private void GoToNext() {
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
}
