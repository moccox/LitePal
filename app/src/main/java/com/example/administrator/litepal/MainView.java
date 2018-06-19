package com.example.administrator.litepal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainView extends AppCompatActivity {

    Button mCreateDb, mInsertData, mDelete, mQueryAll, mUpdate;

    private final int NEW_DIARY_RESULTCODE = 1;//“增”返回值
    private final String TAG = "MainView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mCreateDb = (Button) this.findViewById(R.id.createdb);
        mInsertData = (Button) this.findViewById(R.id.insert);
        mDelete = (Button) this.findViewById(R.id.delete);
        mQueryAll = (Button) this.findViewById(R.id.query);
        mUpdate = (Button) this.findViewById(R.id.update);

        mCreateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        mInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Diary diary = new Diary();
                diary.setId(1);
                diary.setTitle("s1");
                diary.setBody("where have a dog");
                diary.setTime("2018.1.1");
                diary.save();
                */

                Intent intent = new Intent(MainView.this,WriteView.class);
                startActivityForResult(intent,1);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) MainView.this.findViewById(R.id.rowId);
                String id = et.getText().toString().trim();
                DataSupport.deleteAll(Diary.class,"id=?",id);
            }
        });

        mQueryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Diary> diarys = DataSupport.findAll(Diary.class);
                for(Diary diary : diarys){
                    Log.v(TAG,"id == "+diary.getId());
                    Log.v(TAG,"title == "+diary.getTitle());
                    Log.v(TAG,"body == "+diary.getBody());
                    Log.v(TAG,"time == "+diary.getTime());
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Diary diary = new Diary();
                diary.setBody("233333");
                diary.updateAll("id = ?","1");
                */
                EditText et = (EditText) MainView.this.findViewById(R.id.rowId);
                int  rowId = Integer.parseInt(et.getText().toString().trim());

                List<Diary> diarys = DataSupport.findAll(Diary.class);
                for(Diary diary : diarys) {
                    if(diary.getId() == rowId){
                        String uTitle = diary.getTitle();
                        String uBody = diary.getBody();
                        Toast.makeText(MainView.this, uTitle+uBody, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainView.this, WriteView.class);
                        intent.putExtra("title", uTitle);
                        intent.putExtra("body", uBody);
                        intent.putExtra("id", et.getText().toString().trim());
                        startActivityForResult(intent, 1);

                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == 1)
        {

            if(resultCode == 0){
                //新建 按下确定键 返回结果 将结果写入数据库
                Bundle data = intent.getExtras();
                String resultTitle = data.getString("title");
                String resultBody = data.getString("body");
                String resultTime = data.getString("time");

                Diary diary = new Diary();
                diary.setId(1);
                diary.setTitle(resultTitle);
                diary.setBody(resultBody);
                diary.setTime(resultTime);
                diary.save();
            }
            if(resultCode == 3){
                //更改 按下确定键 返回结果 将结果写入数据库
                Bundle data = intent.getExtras();
                String resultTitle = data.getString("title");
                String resultBody = data.getString("body");
                String resultTime = data.getString("time");
                String resultId = data.getString("id");

                Diary diary = new Diary();
                diary.setTitle(resultTitle);
                diary.setBody(resultBody);
                diary.setTime(resultTime);
                diary.updateAll("id=?",resultId);
            }

            if(resultCode == 1);//按下取消键 什么都不用做


        }

    }
}
