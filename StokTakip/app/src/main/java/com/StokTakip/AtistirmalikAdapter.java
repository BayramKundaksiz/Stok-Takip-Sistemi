package com.StokTakip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class AtistirmalikAdapter extends RecyclerView.Adapter<AtistirmalikAdapter.NesneTutucu>{

    private Context mContext;
    private List<UrunlerModel> atistirmalikList;  // kahvaltiverileri = atistirmalikList
    private VeriTabaniAtistirmalik vt;
    private ITiklama tiklamaArayuzu;
    private ITiklama iAzaltItem;
    private ITiklama iArttirItem;

    public AtistirmalikAdapter(Context mContext, List<UrunlerModel> atistirmalikList,
                               VeriTabaniAtistirmalik vt, ITiklama tiklamaArayuzu, ITiklama iAzaltItem, ITiklama iArttirItem) {

        this.mContext = mContext;
        this.atistirmalikList = atistirmalikList;
        this.vt = vt;
        this.tiklamaArayuzu = tiklamaArayuzu;
        this.iAzaltItem = iAzaltItem;
        this.iArttirItem = iArttirItem;

    }


    @NonNull
    @Override
    public NesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.kahvalti_siralamasi,parent,false);

        final NesneTutucu vHolder = new NesneTutucu(view);

        return vHolder;
    }


    public class NesneTutucu extends RecyclerView.ViewHolder{


        private TextView atistirmalikGelenAdi, atistirmalikKalan;
        private TextView atistirmalikGelenUcreti;
        private ImageView imageViewArttir, imageViewAzalt, imageViewUcNokta;
        private CardView kahvaltiSatirCardView;

        DisplayMetrics ekranBoyutuAyari = mContext.getResources().getDisplayMetrics();

        int AdapterEkranBoyutuAyariGenislik = ekranBoyutuAyari.widthPixels;
        int AdapterEkranBoyutuAyariYukseklik = ekranBoyutuAyari.heightPixels;

        public NesneTutucu(View itemView){
            super(itemView);

            atistirmalikGelenAdi =  itemView.findViewById(R.id.atistirmalikGelenAdi);
            atistirmalikGelenUcreti = itemView.findViewById(R.id.atistirmalikGelenUcret);
            kahvaltiSatirCardView = itemView.findViewById(R.id.kahvaltiSatirCardView);
            imageViewArttir = itemView.findViewById(R.id.imageViewArttir);
            imageViewAzalt = itemView.findViewById(R.id.imageViewAzalt);
            atistirmalikKalan = itemView.findViewById(R.id.atistirmalikKalan);
            imageViewUcNokta = itemView.findViewById(R.id.imageViewUcNokta);

            atistirmalikGelenAdi.setTextSize(AdapterEkranBoyutuAyariGenislik/120);
            atistirmalikGelenAdi.setTextSize(AdapterEkranBoyutuAyariYukseklik/120);

            atistirmalikGelenUcreti.setTextSize(AdapterEkranBoyutuAyariGenislik/120);
            atistirmalikGelenUcreti.setTextSize(AdapterEkranBoyutuAyariYukseklik/120);

            atistirmalikKalan.setTextSize(AdapterEkranBoyutuAyariGenislik/120);
            atistirmalikKalan.setTextSize(AdapterEkranBoyutuAyariYukseklik/120);

            imageViewUcNokta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final UrunlerModel kahvaltiMenusu = atistirmalikList.get(getAdapterPosition());

                    PopupMenu popupMenu = new PopupMenu(mContext,itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.card_menu,popupMenu.getMenu());
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.urunSil:

                                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                                    View urunSilTasarim = layoutInflater.inflate(R.layout.urun_sil,null);

                                    TextView txt_dia = urunSilTasarim.findViewById(R.id.txt_dia);
                                    TextView textViewEvet = urunSilTasarim.findViewById(R.id.textViewEvet);
                                    TextView textViewHayir = urunSilTasarim.findViewById(R.id.textViewHayir);

                                    CardView cardViewEvet = urunSilTasarim.findViewById(R.id.cardViewEvet);
                                    CardView cardViewHayir = urunSilTasarim.findViewById(R.id.cardViewHayir);

                                    textViewEvet.setTextSize(AdapterEkranBoyutuAyariGenislik/50);
                                    textViewEvet.setTextSize(AdapterEkranBoyutuAyariYukseklik/50);

                                    textViewHayir.setTextSize(AdapterEkranBoyutuAyariGenislik/50);
                                    textViewHayir.setTextSize(AdapterEkranBoyutuAyariYukseklik/50);

                                    AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                                    ad.setView(urunSilTasarim);
                                    AlertDialog dialog = ad.create();
                                    dialog.setTitle("Ürün Sil");
                                    dialog.show();

                                    txt_dia.setTextSize(AdapterEkranBoyutuAyariGenislik/110);
                                    txt_dia.setTextSize(AdapterEkranBoyutuAyariYukseklik/110);

                                    txt_dia.setText(kahvaltiMenusu.getUrunAdi()+ " adlı ürünü " +
                                            "silmek istediğinizden emin misiniz ?");

                                    cardViewEvet.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new AtistirmaliklarVeriTabani().urunSil(vt,kahvaltiMenusu.getUrunId());
                                            atistirmalikList = new AtistirmaliklarVeriTabani().tumUrunler(vt);
                                            notifyDataSetChanged();
                                            Toast.makeText(mContext,kahvaltiMenusu.getUrunAdi()+ " ürünü silindi", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });

                                    cardViewHayir.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });





                                    ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {

                                        }
                                    });




                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setTitle(kahvaltiMenusu.getUrunAdi()+" adlı ürünü " +
                                            "silmek istediğinizden emin misiniz ?");
                                    builder.setNegativeButton("Hayır", null);
                                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            new AtistirmaliklarVeriTabani().urunSil(vt,kahvaltiMenusu.getUrunId());
                                            atistirmalikList = new AtistirmaliklarVeriTabani().tumUrunler(vt);
                                            notifyDataSetChanged();

                                        }
                                    });
                                    builder.show();*/




                                    return true;
                                case R.id.urunDuzenle:

                                    atistirmalikGoster(kahvaltiMenusu);
                                    return true;

                                default:
                                    return false;
                            }

                        }
                    });
                }
            });


            imageViewAzalt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (iAzaltItem != null) {

                        iAzaltItem.imageViewDecreaseItemClick(getAdapterPosition());

                        int urunUcreti = Integer.parseInt(atistirmalikGelenUcreti.getText().toString().trim());

                        int stokKalan = urunUcreti-1;

                        AtistirmaliklarVeriTabani kahvaltiyaUlasim = new AtistirmaliklarVeriTabani();

                        UrunlerModel urunlerModel = new UrunlerModel();

                        kahvaltiyaUlasim.urunAzalt(vt, urunlerModel.getUrunId(), stokKalan);

                        atistirmalikGelenUcreti.setText("Kalan"+stokKalan);



                    }
                }
            });

            imageViewArttir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (iArttirItem != null) {

                        iArttirItem.imageViewIncreaseItemClick(getAdapterPosition());

                        int urunUcreti = Integer.parseInt(atistirmalikGelenUcreti.getText().toString().trim());

                        int stokKalan = urunUcreti-1;

                        AtistirmaliklarVeriTabani atistirmaliklarVeriTabani = new AtistirmaliklarVeriTabani();

                        UrunlerModel urunlerModel = new UrunlerModel();

                        atistirmaliklarVeriTabani.urunArttir(vt, urunlerModel.getUrunId(), stokKalan);

                        atistirmalikGelenUcreti.setText("Kalan"+stokKalan);

                        Toast.makeText(mContext, stokKalan + "  Kaldı ", Toast.LENGTH_SHORT).show();




                    }

                }
            });


            /*imageViewAzalt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tiklamaArayuzu.onItemClick(getAdapterPosition());

                    YemekVerileri yemekVerileri = new YemekVerileri();

                    int kalanStok = yemekVerileri.getYemekUcreti();

                    int sonDurum = kalanStok - 1;

                    gelenKahvaltiUcreti.setText(""+sonDurum);

                    KahvaltiyaUlasim kahvaltiyaUlasim = new KahvaltiyaUlasim();

                    YemekVerileri verileri = new YemekVerileri();

                    kahvaltiyaUlasim.yemekAzalt(vt, verileri.getYemekId(), sonDurum);

                }
            });*/

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (tiklamaArayuzu != null) {
                       // tiklamaArayuzu.onItemClick(view,getAdapterPosition());


                        tiklamaArayuzu.onItemClick(getAdapterPosition());


                    }

                }
            });*/


        }
    }


    @Override
    public void onBindViewHolder(@NonNull NesneTutucu holder, @SuppressLint("RecyclerView") int position) {


        holder.atistirmalikGelenAdi.setText(atistirmalikList.get(position).getUrunAdi());
        holder.atistirmalikGelenUcreti.setText(""+atistirmalikList.get(position).getUrunUcreti());

        final UrunlerModel kahvaltiMenusu = atistirmalikList.get(position);

        holder.imageViewAzalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int urunUcreti = Integer.parseInt(holder.atistirmalikGelenUcreti.getText().toString().trim());

                int stokKalan = urunUcreti-1;

                AtistirmaliklarVeriTabani atistirmaliklarVeriTabani = new AtistirmaliklarVeriTabani();

                atistirmaliklarVeriTabani.urunArttir(vt, kahvaltiMenusu.getUrunId(), stokKalan);

                holder.atistirmalikGelenUcreti.setText(""+stokKalan);

                Toast.makeText(mContext, urunUcreti + " taneden " + stokKalan + " taneye stok düşümü yapıldı ", Toast.LENGTH_SHORT).show();

            }
        });

        holder.imageViewArttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int urunUcreti = Integer.parseInt(holder.atistirmalikGelenUcreti.getText().toString().trim());

                int stokKalan = urunUcreti+1;

                AtistirmaliklarVeriTabani atistirmaliklarVeriTabani = new AtistirmaliklarVeriTabani();

                atistirmaliklarVeriTabani.urunArttir(vt, kahvaltiMenusu.getUrunId(), stokKalan);

                holder.atistirmalikGelenUcreti.setText(""+stokKalan);

                Toast.makeText(mContext, urunUcreti + " taneden " + stokKalan + " taneye stok arttırımı yapıldı ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void atistirmalikGoster(final UrunlerModel urunlerModel){

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);

        EditText editTextYemekAdi = tasarim.findViewById(R.id.atistirmalikGelenFabAdi);
        EditText editTextYemekUcreti = tasarim.findViewById(R.id.atistirmalikGelenFabUcreti);


        editTextYemekAdi.setText(urunlerModel.getUrunAdi());
        editTextYemekUcreti.setText(""+urunlerModel.getUrunUcreti());

        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Ürün Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String urunAdi = editTextYemekAdi.getText().toString().trim();
                int urunUcreti = Integer.parseInt(editTextYemekUcreti.getText().toString().trim());

                new AtistirmaliklarVeriTabani().urunGuncelle(vt,urunlerModel.getUrunId()
                        ,urunAdi,urunUcreti);

                atistirmalikList = new AtistirmaliklarVeriTabani().tumUrunler(vt);
                notifyDataSetChanged();
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
    public int getItemCount() {

        return atistirmalikList.size();
        //return atistirmalikList == null ? 0 : atistirmalikList.size();
    }


    public void listeyiFiltrele(List<UrunlerModel> urunlerModels){

        atistirmalikList = urunlerModels;

        notifyDataSetChanged();

    }

}
