package chat.with.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.samsao.messageui.views.MessagesWindow;

import chat.with.api.connection.API;
import chat.with.api.connection.Host;
import chat.with.api.connection.Service;
import chat.with.api.model.req.ReqRegister;
import chat.with.api.model.res.ResUtama;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText txt_username,txt_password,txt_nama_lengkap;
    MaterialButton btn_register;
    String username,password, namleng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        //txt_nama_lengkap = findViewById(R.id.txt_nama_lengkap);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                //register(txt_username.getText().toString(),txt_password.getText().toString(),
                // txt_nama_lengkap.getText().toString());
                /*username = txt_username.getText().toString();
                password = txt_password.getText().toString();
                namleng = txt_nama_lengkap.getText().toString();

                if (username.trim().equals("")){
                    txt_username.setError("Isi Username dengan benar");
                } else if (password.trim().equals("")){
                    txt_password.setError("Isi Password dengan benar");
                }else if (namleng.trim().equals("")){
                    txt_nama_lengkap.setError("Nama tidak boleh kosong");
                }else {*/

                //}
                //pindah();
            }
        });
    }

    private void pindah() {
        Intent intent = new Intent(RegisterActivity.this, ChatActivity.class);
        finish();
        startActivity(intent);
    }

   /* private void register(String username,String password,String namalengkap) {

//        ReqRegister reqRegister = new ReqRegister();
//        reqRegister.setUsername(username);
//        reqRegister.setPassword(password);
//        reqRegister.setNama_lengkap(nama_lengkap);
        Call<ResUtama> callRegister = API.service().registerRequest(username,password,namalengkap);
        callRegister.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("Log Chat", response.code() + "");
                if (response.code() == 200) {

                    //ResUtama resChat = response.body();
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(response.code()+ "")
                            .show();
                }
                else{

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(response.code()+ "")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }*/

    private void register(){
        username = txt_username.getText().toString();
        password = txt_password.getText().toString();
        //namleng = txt_nama_lengkap.getText().toString();

        Call<ResUtama> tambahUser = API.service().registerRequest(username, password);

        tambahUser.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getStatus();
                pindah();
                Toast.makeText(RegisterActivity.this, "Kode : "+kode+ "\nPesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}