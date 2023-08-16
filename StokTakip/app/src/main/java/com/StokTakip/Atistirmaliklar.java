package com.StokTakip;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Atistirmaliklar extends Fragment implements ITiklama{

    View rootView;
    private RecyclerView recyclerView;
    private ArrayList<UrunlerModel> urunlerModels;
    private AtistirmalikAdapter rvAdapter;
    private VeriTabaniAtistirmalik vt;
    private ImageView floatingActionButtonEkle;
    private TextView atistirmalikGelenUcret;
    private BottomAppBar bar;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        rootView = inflater.inflate(R.layout.fragment_atistirmaliklar, container, false);

        vt = new VeriTabaniAtistirmalik(getContext());

        bar = rootView.findViewById(R.id.bar);

        bar.replaceMenu(R.menu.bottom_nav_menu);


        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id){

                    case R.id.search:

                        SearchView searchView = item.getActionView().findViewById(R.id.search);

                        searchView.clearFocus();

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {

                                urunlerModels = new AtistirmaliklarVeriTabani().tumUrunler(vt);

                                rvAdapter = new AtistirmalikAdapter(getActivity(), urunlerModels, vt,
                                        Atistirmaliklar.this,
                                        Atistirmaliklar.this, Atistirmaliklar.this);

                                recyclerView.setAdapter(rvAdapter);
                                rvAdapter.notifyDataSetChanged();
                                return true;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {

                                urunlerModels = new AtistirmaliklarVeriTabani().tumUrunler(vt);

                                rvAdapter = new AtistirmalikAdapter(getActivity(), urunlerModels, vt, Atistirmaliklar.this,
                                        Atistirmaliklar.this, Atistirmaliklar.this);

                                recyclerView.setAdapter(rvAdapter);
                                filterList(s);

                                return true;
                            }

                        });
                        Toast.makeText(getContext(), "Lütfen aranılacak ürünü giriniz", Toast.LENGTH_SHORT).show();
                        break;


                }

                urunlerModels = new AtistirmaliklarVeriTabani().tumUrunler(vt);

                rvAdapter = new AtistirmalikAdapter(getActivity(), urunlerModels, vt, Atistirmaliklar.this,
                        Atistirmaliklar.this, Atistirmaliklar.this);

                recyclerView.setAdapter(rvAdapter);

                return true;
            }
        });

        atistirmalikGelenUcret = rootView.findViewById(R.id.atistirmalikGelenUcret);

        floatingActionButtonEkle = rootView.findViewById(R.id.floatingActionButtonEkle);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        floatingActionButtonEkle = rootView.findViewById(R.id.floatingActionButtonEkle);

        floatingActionButtonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kahvaltiGoster();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        urunlerModels = new AtistirmaliklarVeriTabani().tumUrunler(vt);

        rvAdapter = new AtistirmalikAdapter(getContext(),urunlerModels,vt,this, Atistirmaliklar.this, Atistirmaliklar.this);

        recyclerView.setAdapter(rvAdapter);

        return rootView;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    public void kahvaltiGoster(){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);

        EditText atistirmalikGelenFabAdi = tasarim.findViewById(R.id.atistirmalikGelenFabAdi);
        EditText atistirmalikGelenFabUcreti = tasarim.findViewById(R.id.atistirmalikGelenFabUcreti);


        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Ürün Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (atistirmalikGelenFabAdi.getText().toString().trim().equals("")
                        && atistirmalikGelenFabUcreti.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari = new AlertDialog.Builder(getContext());
                    uyari.setTitle("DİKKAT");
                    uyari.setMessage("Lütfen her iki alanıda doldurunuz.");
                    uyari.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari.show();
                }

                else if (atistirmalikGelenFabAdi.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari2 = new AlertDialog.Builder(getContext());
                    uyari2.setTitle("DİKKAT");
                    uyari2.setMessage("Lütfen ürün adı alanını doldurunuz.");
                    uyari2.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari2.show();
                }

                else if (atistirmalikGelenFabUcreti.getText().toString().trim().equals("")) {

                    AlertDialog.Builder uyari3 = new AlertDialog.Builder(getContext());
                    uyari3.setTitle("DİKKAT");
                    uyari3.setMessage("Lütfen ürün ücreti alanını doldurunuz.");
                    uyari3.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                    uyari3.show();

                }

                else {

                    String yemekAdi = atistirmalikGelenFabAdi.getText().toString().trim();

                    int yemekUcreti = Integer.parseInt((atistirmalikGelenFabUcreti.getText().toString().trim()));

                    new AtistirmaliklarVeriTabani().urunEkle(vt, yemekAdi, yemekUcreti);

                    urunlerModels = new AtistirmaliklarVeriTabani().tumUrunler(vt);

                    rvAdapter = new AtistirmalikAdapter(getContext(), urunlerModels, vt, Atistirmaliklar.this, Atistirmaliklar.this, Atistirmaliklar.this);

                    recyclerView.setAdapter(rvAdapter);


                }

            }
        });

        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.create().show();

    }


    @Override
    public void imageViewIncreaseItemClick(int position) {

    }

    @Override
    public void imageViewDecreaseItemClick(int position) {

    }

    private void filterList(String s) {

        List<UrunlerModel> filteredList = new ArrayList<>();
        for (UrunlerModel item : urunlerModels) {
            if (item.getUrunAdi().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);

            }
        }
        rvAdapter.listeyiFiltrele(filteredList);
    }

}