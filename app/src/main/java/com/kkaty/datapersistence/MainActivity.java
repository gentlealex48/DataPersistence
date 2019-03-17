package com.kkaty.datapersistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.kkaty.datapersistence.Constants.SHARE_PREF_NAME;
import static com.kkaty.datapersistence.Constants.SHARE_PREF_NAME;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    MySQLiteHelper mySqlLiteHelper;
    EditText etUserName;
    TextView tvDisplayUserName;
    TextView tvPassword;
    EditText etPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = findViewById(R.id.etUserName);
        tvDisplayUserName = findViewById(R.id.tvDisplayUserName);
        tvPassword = findViewById(R.id.tvDisplayPassword);
        etPassword = findViewById(R.id.etPassword);
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        mySqlLiteHelper = new MySQLiteHelper(this,null );

        String userNameToDisplay = sharedPreferences.getString(Constants.USER_NAME_KEY, "NO NAME");
        tvDisplayUserName.setText(userNameToDisplay);
    }

    public void onButtonClick(View view) {

        switch (view.getId()) {
            case R.id.btnSaveUserName:
                if(!etUserName.getText().toString().isEmpty()){
                    String userName = etUserName.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.USER_NAME_KEY, userName);
                    editor.commit();
                }
                break;
            case R.id.btnGetUserName:
                String userNameToDisplay = sharedPreferences.getString(Constants.USER_NAME_KEY, "NO NAME");
                tvDisplayUserName.setText(userNameToDisplay);
                break;
            case R.id.btnInsertIntoDB:
                String uName = etUserName.getText().toString();
                String pWord = etPassword.getText().toString();
                mySqlLiteHelper.insertUser(uName, pWord);

                break;
            case R.id.btnGetFromDB:
                Cursor cur = mySqlLiteHelper.getUsersByName(etUserName.getText().toString());
                cur.moveToFirst();
                int uNameIndex = cur.getColumnIndex(Constants.USER_NAME_KEY);
                int pWordIndex = cur.getColumnIndex(Constants.PASSWORD);
                String returnUserName = cur.getString(uNameIndex);
                String returnPassword = cur.getString(pWordIndex);
                tvPassword.setText(returnPassword);
                tvDisplayUserName.setText(returnUserName);
                break;


        }
    }
}
