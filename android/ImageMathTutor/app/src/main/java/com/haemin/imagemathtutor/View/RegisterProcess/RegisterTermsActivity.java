package com.haemin.imagemathtutor.View.RegisterProcess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.R;

public class RegisterTermsActivity extends AppCompatActivity {


    @BindView(R.id.btn_accept)
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_terms);

        ButterKnife.bind(this);

        btnAccept.setOnClickListener(v -> {
            GoToNext();
        });
    }

    private void GoToNext() {
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
}
