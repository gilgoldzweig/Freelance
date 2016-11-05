package goldzweigapps.freelance.Network;

import android.os.Looper;

import java.io.IOException;

import goldzweigapps.freelance.Interface.ResponseCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCalls {

    public static void Post(String url, final ResponseCallBack callBack, String... post){

                FormBody.Builder builder = new FormBody.Builder();
                for (String add : post){
                    String name = add.split(", ")[0];
                    String variable = add.split(", ")[1];
                    builder.add(name, variable);

                }

                final OkHttpClient client = new OkHttpClient();


                final Request request = new Request.Builder()
                        .url(url)
                        .post(builder.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.Response(e.getMessage());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        callBack.Response(response.body().string());

//                        if (response.isSuccessful()){
//                            callBack.Response(response.body().string());
//                        }else{
//                            callBack.Response("Response was not successful");
//                        }

                    }
                });
    }

    public static void Get(String url, final ResponseCallBack callBack){
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.Response(e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.Response(response.body().string());

            }
        });

    }



}
