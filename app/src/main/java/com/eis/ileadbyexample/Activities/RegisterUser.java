package com.eis.ileadbyexample.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eis.ileadbyexample.Api.RetrofitClient;
import com.eis.ileadbyexample.Models.DefaultResponse;
import com.eis.ileadbyexample.Others.ViewDialog;
import com.eis.ileadbyexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {

    private EditText id,pass,mobno;
    private FloatingActionButton fabbtn;
    private Button loginpage;
    RelativeLayout rl;
    String mPhoneNumber,dbprefix;
    ViewDialog progress;
    Spinner spnArea;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_register_user);
        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();
        progress = new ViewDialog(this);
        id = findViewById(R.id.lid);
        pass = findViewById(R.id.pass);
        mobno = findViewById(R.id.mobileno);
        fabbtn = findViewById(R.id.register);
        loginpage = findViewById(R.id.login);
        spnArea=findViewById(R.id.spnarea);
        getArea();
        rl = findViewById(R.id.rl);
        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mPhoneNumber = tMgr.getLine1Number();
        mobno.setText(mPhoneNumber);//.substring(2,12)
        //dbprefix = "palsons2";
    }

    public void DoRegister(View arg){

            String lid = id.getText().toString().trim();
            String password = pass.getText().toString().trim();
            final String mobileno = mobno.getText().toString().trim();

            if (lid.isEmpty()) {
                id.setError("Login ID is required");
                id.requestFocus();
                return;
            }

            if (lid.length() < 5) {
                id.setError("Enter a valid login ID");
                id.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                pass.setError("Password is required");
                pass.requestFocus();
                return;
            }

            if (password.length() > 5) {
                pass.setError("Please enter valid password");
                pass.requestFocus();
                return;
            }

            if (mobileno.isEmpty()) {
                mobno.setError("Not able to read mobile number. Check SIM 1 slot has SIM card or not");
                mobno.requestFocus();
                return;
                /*Snackbar snackbar = Snackbar.make(rl, "Not able to read mobile number. Check SIM 1 slot has SIM card", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                return;*/
            }

            progress.show();
            Call<DefaultResponse> call = RetrofitClient
                    .getInstance().getApi().registerUser(lid, password,mobileno,spnArea.getSelectedItem().toString().trim());
            call.enqueue(new Callback<DefaultResponse>() {

                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse dResponse = response.body();

                    progress.dismiss();


                    if (!dResponse.isError()) {
                        // Log.d("message",loginResponse.getMessage());
                        if (dResponse.getErrormsg().equalsIgnoreCase("SUCCESS")) {
                            Intent intent = new Intent(RegisterUser.this,OTPVerification.class);
                            intent.putExtra("ecode",id.getText().toString().trim());
                            intent.putExtra("mobile",mobno.getText().toString().trim());
                            intent.putExtra("dbprefix",spnArea.getSelectedItem().toString().trim());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(rl, dResponse.getErrormsg(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    progress.dismiss();
                    if (t instanceof IOException) {
                        Snackbar snackbar = Snackbar.make(rl, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(rl, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });

    }

    public void GotoLogin(View arg){
        Intent intent = new Intent(RegisterUser.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getArea() {

        progress.show();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, RetrofitClient.BASE_URL+"getdbnames", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                List<String> arrayList =new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String[] dblist = jsonObject.getString("dbnames").split(",");
                    for (String s: dblist) {
                        Log.d("dbnames",s);
                        arrayList.add(s);
                    }

                    //arrayList.add(jsonObject.getString("area_name"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterUser.this,  android.R.layout.simple_spinner_dropdown_item, arrayList);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spnArea.setAdapter(adapter);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();

            }
        }){};
        requestQueue.add(stringRequest);
    }
}
