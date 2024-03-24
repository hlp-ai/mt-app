package com.yimt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class language extends AppCompatActivity {
    final static int CONN_TIMEOUT = 15000;
    final static int READ_TIMEOUT = 15000;
    private final static int LANGUAGES = 1;
    private final static String DEFAULT_SERVER = "http://192.168.1.104:5555";
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Toolbar toolbar = findViewById(R.id.toolbar_language);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("语言");
        }

        // Get the TextViews
        TextView language1 = findViewById(R.id.language1);
        TextView language2 = findViewById(R.id.language2);
        TextView language3 = findViewById(R.id.language3);

        // Set the click listeners
        language1.setOnClickListener(this::toggleCheckmark);
        language2.setOnClickListener(this::toggleCheckmark);
        language3.setOnClickListener(this::toggleCheckmark);

        // 获取RecyclerView
        RecyclerView recyclerView = findViewById(R.id.all_languages_recycler_view);

        // 创建一个新的LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // 将LinearLayoutManager设置为RecyclerView的布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建一个新的LanguageAdapter
        LanguageAdapter adapter = new LanguageAdapter(this);

        // 将LanguageAdapter设置为RecyclerView的适配器
        recyclerView.setAdapter(adapter);
    }

//    public void selectLanguage(View view) {
//        // 从视图的标签中获取位置
//        Integer position = (Integer) view.getTag();
//
//        // 检查位置是否为 null
//        if (position == null) {
//            // 如果位置为 null，那么就返回，不做任何事情
//            return;
//        }
//
//        // 获取 RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.all_languages_recycler_view);
//
//        // 获取 RecyclerView 的适配器
//        LanguageAdapter adapter = (LanguageAdapter) recyclerView.getAdapter();
//
//        // 设置被选中的语言的位置
//        if (adapter != null) {
//            adapter.setSelectedPosition(position);
//        }
//    }

    // 添加一个方法，用于获取被选中的语言的位置
    public int getSelectedPosition() {
        return selectedPosition;
    }

    // 添加一个方法，用于设置被选中的语言的位置
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public void toggleCheckmark(View view) {
        // 获取最近使用的语言
        TextView language1 = findViewById(R.id.language1);
        TextView language2 = findViewById(R.id.language2);
        TextView language3 = findViewById(R.id.language3);

        List<TextView> languages = Arrays.asList(language1, language2, language3);
        for (TextView language : languages) {
            // 如果视图的 ID 是最近使用的语言的 ID，那么就设置它的 drawable 为打勾的图标
            if (language.getId() == view.getId()) {
                language.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checkmark, 0, 0, 0);
            } else { // 否则设置为透明的图标
                language.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_transparent, 0, 0, 0);
            }
        }

        // 从视图的标签中获取位置
        Integer position = (Integer) view.getTag();

        // 检查位置是否为 null
        if (position == null) {
            // 如果位置为 null，那么就返回，不做任何事情
            return;
        }

        // 获取 RecyclerView
        RecyclerView recyclerView = findViewById(R.id.all_languages_recycler_view);

        // 获取 RecyclerView 的适配器
        LanguageAdapter adapter = (LanguageAdapter) recyclerView.getAdapter();

        // 设置被选中的语言的位置
        if (adapter != null) {
            adapter.setSelectedPosition(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, String> getLanguageMap(String languages) {
        HashMap<String, String> langMap = new HashMap<String, String>();
        String[] str = languages.split(",");
        for (String s : str) {
            if (!s.equals("")) {
                String[] pair = s.split(":");
                langMap.put(pair[0], pair[1]);
            }
        }

        return langMap;
    }

//    private String requestLanguages(String server) throws Exception {
//        StringBuilder languages = new StringBuilder();
//        URL url = new URL(server + "/languages");
//
//        Log.d("yimt", "Request languages from " + url);
//
//        HttpURLConnection conn = null;
//        try {
//            conn = server.startsWith("https") ?
//                    (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(CONN_TIMEOUT);
//            conn.setReadTimeout(READ_TIMEOUT);
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("accept", "application/json");
//
//            InputStream inputStream = conn.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            JSONArray jsonArray = new JSONArray(reader.readLine());
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String langCode = jsonArray.getJSONObject(i).getString("code");
//                String langName = jsonArray.getJSONObject(i).getString("name");
//                languages.append(langCode).append(":").append(langName).append(",");
//            }
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//
//        if (languages.length() > 0)
//            return languages.substring(0, languages.length() - 1);
//        return languages.toString();
//    }
//
//    private void retrieveLanguages() throws Exception {
//        // 从另一个Activity_settings中获取服务器地址
//        String server = getSharedPreferences("settings", MODE_PRIVATE).getString("server", DEFAULT_SERVER);
//
//        Thread thread = new Thread(() -> {
//            String languages = "";
//            String error = "";
//            try {
//                if (server != null) {
//                    languages = requestLanguages(server);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                error = e.toString();
//            }
//
//            Bundle bundle = new Bundle();
//            bundle.putString("languages", languages);
//            bundle.putString("serverError", error);
//            Message msg = new Message();
//            msg.setData(bundle);
//            msg.what = LANGUAGES;
//            mhandler.sendMessage(msg);
//        });
//
//        thread.start();
//    }
}
