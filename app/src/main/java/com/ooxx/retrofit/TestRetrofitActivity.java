package com.ooxx.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ooxx.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by wengzhch on 2016/4/11.
 */
public class TestRetrofitActivity extends Activity{

    Button btn;

    static class ToStringConverterFactory extends Converter.Factory {
        private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");


        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return new Converter<ResponseBody, String>() {
                    @Override
                    public String convert(ResponseBody value) throws IOException {
                        return value.string();
                    }
                };
            }
            return null;
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

            if (String.class.equals(type)) {
                return new Converter<String, RequestBody>() {
                    @Override
                    public RequestBody convert(String value) throws IOException {
                        return RequestBody.create(MEDIA_TYPE, value);
                    }
                };
            }
            return null;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);
        btn = (Button) findViewById(R.id.test_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction();
            }
        });
    }

    private void doAction (){

        new Thread(){

            @Override
            public void run() {
                try{
                    Log.d("a","a");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://www.baidu.com")
                            .addConverterFactory(new ToStringConverterFactory())
                            .build();
                    BaiduService service = retrofit.create(BaiduService.class);
                    Call<String> call =  service.getMainPage();
                    Response response =  call.execute();
                    Log.d("ooxx", "receive msg = " + response.body().toString());
                    Log.d("","c");
                    Log.d("c","c");
                }catch(Exception e){
                    Log.d("exception",""+e);
                }
            }
        }.start();
    }

}
