package com.example.carljohnson.studyapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //打开或创建test.db数据库
        SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS person");
        //创建person表
        db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
        Person person = new Person();
        person.name = "john";
        person.age = 30;
        //插入数据
        db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.name, person.age});

        person.name = "david";
        person.age = 33;
        //ContentValues以键值对的形式存放数据
        ContentValues cv = new ContentValues();
        cv.put("name", person.name);
        cv.put("age", person.age);
        //插入ContentValues中的数据
        db.insert("person", null, cv);

        cv = new ContentValues();
        cv.put("age", 35);
        //更新数据
        db.update("person", cv, "name = ?", new String[]{"john"});

        Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"33"});
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
        }
        c.close();

        //删除数据
        db.delete("person", "age < ?", new String[]{"35"});

        //关闭当前数据库
        db.close();

        //删除test.db数据库
//      deleteDatabase("test.db");

        Button a = (Button) findViewById(R.id.lisBtn);
        a.setOnClickListener(new a());
    }

    public class Person {
        public int _id;
        public String name;
        public int age;
        public String info;

        public Person() {
        }

        public Person(String name, int age, String info) {
            this.name = name;
            this.age = age;
            this.info = info;
        }
    }

    class a implements View.OnClickListener{
        @Override
        public void onClick(View v){
            TextView b = (TextView) findViewById(R.id.txt);
            //b.setText("监听按钮");
            SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
            Cursor a = db.rawQuery("SELECT * FROM person WHERE name = ?", new String[]{"john"});
            while(a.moveToNext()){
                b.setText(a.getString(1));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void xmlClick(View v){
        TextView sum = (TextView) findViewById(R.id.txt);
        //EditText num1 = ;
        String sum1 = new Float(new Float(((EditText)findViewById(R.id.num1)).getText().toString()) + new Float(((EditText)findViewById(R.id.num2)).getText().toString())).toString();
        //String sum1 = "1231";//(((EditText)findViewById(R.id.num1)).getText()+((EditText) findViewById(R.id.num2)).getText()).toString();
        sum.setText(sum1);
    }
}
