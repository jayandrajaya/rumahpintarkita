package com.jayandra.rumahpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TambahTransaksi extends AppCompatActivity {
    EditText nama,judul,lama,jumlah;
    Button masuk,daftarTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);
        nama = (EditText) findViewById(R.id.et_nama);
        judul = (EditText) findViewById(R.id.et_judul);
        jumlah = (EditText) findViewById(R.id.et_jumlah);
        lama = (EditText) findViewById(R.id.et_lama);
        masuk = (Button) findViewById(R.id.btn_masukan);
        daftarTrans = (Button) findViewById(R.id.btn_Daftar);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!nama.equals(""))&&(!judul.equals(""))&&(!jumlah.equals(""))&&(!lama.equals(""))){
                    String nm = nama.getText().toString();
                    String jd = judul.getText().toString();
                    String jm = jumlah.getText().toString();
                    String lm = lama.getText().toString();
                    kirimData(nm,jd,jm,lm);
                    Toast.makeText(getApplicationContext(),"Data terkirim",Toast.LENGTH_LONG).show();
                    nama.setText("");
                    judul.setText("");
                    jumlah.setText("");
                    lama.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Mohon lengkapi data",Toast.LENGTH_LONG).show();
                }
            }
        });
        daftarTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahTransaksi.this,Transaksi.class));
                finish();
            }
        });

    }

    void kirimData(final String nama, final String judul, final String jumlah, final String lama){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sintia-trimilinia.000webhostapp.com/input_data.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nama",nama);
                params.put("judul",judul);
                params.put("lama",lama);
                params.put("jumlah",jumlah);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}