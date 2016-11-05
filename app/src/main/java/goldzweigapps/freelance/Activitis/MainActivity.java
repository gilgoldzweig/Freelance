package goldzweigapps.freelance.Activitis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import goldzweigapps.freelance.Interface.ResponseCallBack;
import goldzweigapps.freelance.Network.HttpCalls;
import goldzweigapps.freelance.R;
public class MainActivity extends AppCompatActivity {
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                HttpCalls.Post("http://gilgoldzweig.pythonanywhere.com/api/verf", new ResponseCallBack() {
                    @Override
                    public void Response(String ResponseBody) {
                        Log.d("tag", ResponseBody);
                        if (ResponseBody.contains("error")){
                            Snackbar.make(v, "Password is wrong you cant login", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, "Password is wrong you cant login", Toast.LENGTH_LONG).show();
                        }else if (ResponseBody.contains("ok")){
                            Intent intent = new Intent(MainActivity.this, FacebookActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onUi() {

                    }
                },"pass, "+password.getText().toString());
            }
        });


    }


}
