package com.example.feihualing;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputStream context = null;
                try {
                    context = getResources().getAssets().open("shi300.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(context));
                    String str = "", lineContext;
                    String words = editText.getText().toString();
                    int count=0;
                    String title="";
                    while ((lineContext = br.readLine()) != null) {
                        try {
                            if (lineContext.charAt(0) <= '9' && lineContext.charAt(0) >= '0') {
                                title = lineContext.substring(3,lineContext.length());
                                if(title.contains(words)){
                                    str += count + 1 + "." + title +"\n";
                                    count++;
                                }
                            } else {
                                if (lineContext.contains(words)) {
                                    str += count + 1 + "." + lineContext + "----" + title+"\n";
                                    count++;
                                }
                            }
                        }catch (Exception e){}
                    }
                    int k=0;
                    //开始给文本变色
                    SpannableStringBuilder builder=new SpannableStringBuilder(str);
                    while( k >= 0)
                    {
                        int i=str.indexOf(words, k);//从k开始检索，当找不到时i=-1；
                        if (i == -1)
                            break;
                        k = i + 1;
//                        字体加粗
                        builder.setSpan(new ForegroundColorSpan(Color.BLUE),i,i + words.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                        字体染色
                        builder.setSpan( new StyleSpan(android.graphics.Typeface.BOLD),i,i+words.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                    textView.setText(builder);
                    textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                    textView.setHorizontallyScrolling(true);
                    Toast.makeText(getApplicationContext(), "搜索到"+count+"条结果！",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
