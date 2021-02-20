package com.jayandra.rumahpintar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Transaksi extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<DataTransaksi> list;
    ListView listView;
    Button daftarTrans,masukTrans;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.tl_transaksi);
        listView = findViewById(R.id.lv_transaksi);
        masukTrans = (Button) findViewById(R.id.btn_tambah);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        masukTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transaksi.this,TambahTransaksi.class));
                finish();
            }
        });
        tampilData();

    }
    void tampilData(){
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://sintia-trimilinia.000webhostapp.com/ambilDataTransaksi.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject getData=jsonArray.getJSONObject(i);
                                String nama = getData.getString("nama");
                                String judul = getData.getString("judul");
                                String lama = getData.getString("lama");
                                String jumlah = getData.getString("jumlah");

                                //Toast.makeText(getApplicationContext(),kategori+" "+kelas+" "+jumlah,Toast.LENGTH_LONG).show();
                                list.add(new DataTransaksi(nama,judul,lama,jumlah));
                                //Toast.makeText(getApplicationContext(),list.toString(),Toast.LENGTH_LONG).show();
                            }
                            Adapter adapter = new Adapter(Transaksi.this,list);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<DataTransaksi> model;
    public Adapter(Context context, ArrayList<DataTransaksi>model){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model = model;

    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    TextView nama,judul,lama,jumlah;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_transaksi,parent,false);
        nama = (TextView) view.findViewById(R.id.tv_peminjam);
        judul = (TextView) view.findViewById(R.id.tv_judulBuku);
        lama = (TextView) view.findViewById(R.id.tv_lamaPeminjaman);
        jumlah = (TextView) view.findViewById(R.id.tv_jumlahBuku);

        nama.setText("Nama Peminjam: "+model.get(position).getKodePeminjam());
        judul.setText("Judul Buku: "+model.get(position).getKodeBuku());
        lama.setText("Lama Peminjaman: "+model.get(position).getLama()+" hari");
        jumlah.setText("Jumlah Buku: "+model.get(position).getJumlah()+" buah");

        return view;
    }

}