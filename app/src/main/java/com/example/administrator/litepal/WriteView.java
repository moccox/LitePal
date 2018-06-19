package com.example.administrator.litepal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


public class WriteView extends Activity {

    private Button mDetermine;
    private Button mCancel;
    private EditText mtTitle;
    private EditText mtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_view);

        mDetermine = (Button) this.findViewById(R.id.determine);
        mCancel = (Button) this.findViewById(R.id.cancel);
        mtTitle = (EditText) this.findViewById(R.id.tTitle);
        mtBody = (EditText) this.findViewById(R.id.tBody);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //有附带内容 为修改模式
            String originalTitle = extras.getString("title");
            String originalBody = extras.getString("body");
            final String originalId = extras.getString("id");

            mtTitle.setText(originalTitle);
            mtBody.setText(originalBody);

            mDetermine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mtTitle.getText().toString().trim();
                    String body = mtBody.getText().toString().trim();
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                            + calendar.get(Calendar.MINUTE) + ":";

                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("body", body);
                    intent.putExtra("time", time);
                    intent.putExtra("id",originalId);
                    setResult(3, intent);
                    finish();
                }
            });

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
            });
        }

        else {
            mDetermine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mtTitle.getText().toString().trim();
                    String body = mtBody.getText().toString().trim();
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.YEAR) + "-"
                            + (calendar.get(Calendar.MONTH) + 1) + "-"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "-"
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                            + calendar.get(Calendar.MINUTE) + ":";

                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("body", body);
                    intent.putExtra("time", time);
                    setResult(0, intent);
                    finish();

                }
            });

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
            });

        }

    }

}
