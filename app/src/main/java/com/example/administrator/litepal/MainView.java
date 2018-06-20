package com.example.administrator.litepal;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainView extends Activity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private Button mCreateDb, mInsertData, mDelete;
    private ListView mListView;
    private int mItemId;//用来记录当前点击的项目ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        //初始化成员变量
        mListView = (ListView) this.findViewById(R.id.listView);
        mCreateDb = (Button) this.findViewById(R.id.createdb);
        mInsertData = (Button) this.findViewById(R.id.insert);
        mDelete = (Button) this.findViewById(R.id.delete);

        //初始化ListView
        listViewInit();

        //设置按钮监听
        mCreateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        mInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this,WriteView.class);
                startActivityForResult(intent,1);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemId == 0){
                    Toast.makeText(MainView.this, "Please get intem id,first.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String id = String.valueOf(mItemId);
                    DataSupport.deleteAll(Diary.class, "id=?", id);
                    MainView.this.listViewInit();
                }
            }
        });



        //设置ListView监听
        mListView.setOnItemClickListener(this); //点击
        mListView.setOnItemLongClickListener(this); //长按
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

                MainView.this.listViewInit();
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
                MainView.this.listViewInit();
            }

            if(resultCode == 1);//按下取消键 什么都不用做


        }

    }

    //将数据库里的部分内容写入ListView
    public void listViewInit(){
        Toast.makeText(this, "有执行ListViewInit（）", Toast.LENGTH_LONG).show();
        List<Diary> diarys = DataSupport.findAll(Diary.class);
        List<Item> items = new ArrayList<Item>();
        for(Diary diary : diarys){
            String idStr = String.valueOf(diary.getId());
            Item item = new Item(idStr,diary.getTitle(),diary.getTime());
            items.add(item);
        }
        if(items.isEmpty()){
            Toast.makeText(MainView.this, "arrlsDiary is Empty", Toast.LENGTH_SHORT).show();
        }

        DiaryAdapter adapter = new DiaryAdapter(MainView.this,R.layout.diary_item,items);
        mListView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Item item = (Item) parent.getItemAtPosition(position);
        mItemId = Integer.parseInt(item.getiId());
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
        Item item = (Item) parent.getItemAtPosition(position);
        //Toast.makeText(this, item.getiTitle(), Toast.LENGTH_SHORT).show();

        List<Diary> diarys = DataSupport.findAll(Diary.class);
        for(Diary diary : diarys) {
            int iId = Integer.parseInt(item.getiId());
            if(diary.getId() == iId){
                String uTitle = diary.getTitle();
                String uBody = diary.getBody();
                //Toast.makeText(MainView.this, uTitle+uBody, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainView.this, WriteView.class);
                intent.putExtra("title", uTitle);
                intent.putExtra("body", uBody);
                intent.putExtra("id", item.getiId());
                startActivityForResult(intent, 1);

            }
        }
        return true;
    }
}
