package com.yimt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yimt.databinding.ActivitySettingsBinding;

public class settings extends AppCompatActivity {
    private final static String DEFAULT_SERVER = "http://192.168.1.104:5555";
    private SharedPreferences settings;
    private ActivitySettingsBinding binding;
    private EditText editTextServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("设置");
        }

        // 初始化SharedPreferences
        settings = getSharedPreferences("settings", MODE_PRIVATE);

        // 通过ID找到EditText
        editTextServer = findViewById(R.id.editTextServer);

        // 从SharedPreferences中获取保存的值，或者使用默认值
        String serverValue = settings.getString("server", DEFAULT_SERVER);
        editTextServer.setText(serverValue); // 设置EditText的初始值

        // 设置文本观察者来监听EditText的变化
        editTextServer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前的操作
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本变化时的操作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本变化后的操作，保存新的值到SharedPreferences
                String newServerValue = s.toString();
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("server", newServerValue);
                editor.apply(); // 保存更改
            }
        });

        // Get the EditTexts
        // Get the TextViews
        TextView sourceLangText = findViewById(R.id.sourceLangText);
        TextView targetLangText = findViewById(R.id.targetLangText);

        // Set the click listeners
        sourceLangText.setOnClickListener(this::openLanguageActivity);
        targetLangText.setOnClickListener(this::openLanguageActivity);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openLanguageActivity(View view) {
        Intent intent = new Intent(this, language.class);
        startActivity(intent);
    }
}