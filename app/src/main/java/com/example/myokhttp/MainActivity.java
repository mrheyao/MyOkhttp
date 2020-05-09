package com.example.myokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  String url="https://v.juhe.cn/historyWeather/citys";
    RequestBean mRequestBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestBean =  new RequestBean();
        mRequestBean.setKey("bb52107206585ab074f5e59a8c73875b");
        mRequestBean.setProvice_id("2");

        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                for (int i = 0; i < 100; i++) {
                    MyOkhttp.sendRequest(url, mRequestBean, ResponseBean.class, new IJsonListener<ResponseBean>() {
                        @Override
                        public void onSuccess(ResponseBean o) {
                            Log.i("testtt","请求次数：成功"+
                                   o.getResultcode()+"  "+o.getError_code() );
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }
        });


    }
}
