package com.example.praveenp.logitechtestapp.Activity;

import android.database.Cursor;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.praveenp.logitechtestapp.R;
import com.example.praveenp.logitechtestapp.adapter.SvgDecoder;
import com.example.praveenp.logitechtestapp.adapter.SvgDrawableTranscoder;
import com.example.praveenp.logitechtestapp.adapter.SvgSoftwareLayerSetter;
import com.example.praveenp.logitechtestapp.dao.Country;
import com.example.praveenp.logitechtestapp.database.CountryFav;
import com.example.praveenp.logitechtestapp.database.FavoritesDatabase;

import java.io.InputStream;

/**
 * Created by praveenpokuri on 01/10/18.
 */
public class DetailsActivity extends AppCompatActivity {

    ImageView logo;
    TextView country;
    TextView language;
    TextView countryCode;
    ImageView favourite;
    FavoritesDatabase mFavDb;

    String countryName;
    String countryLanguage;
    String imageLogo;
    String countryCallCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        logo = (ImageView) findViewById(R.id.logo_detail);
        country = (TextView) findViewById(R.id.country_name);
        language = (TextView) findViewById(R.id.language);
        countryCode = (TextView) findViewById(R.id.country_code);
        favourite = (ImageView) findViewById(R.id.btn_fav);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            countryName = bundle.getString("COUNTRY_NAME");
            countryLanguage = bundle.getString("COUNTRY_LANG");
            countryCallCode = bundle.getString("COUNTRY_CALLING_CODE");
            imageLogo = bundle.getString("COUNTRY_FLAG");
        }
        country.setText(countryName);
        language.setText(countryLanguage);
        countryCode.setText(countryCallCode);

        mFavDb = new FavoritesDatabase(this);
        mFavDb.open();
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favourite.setColorFilter(R.color.green, android.graphics.PorterDuff.Mode.MULTIPLY);
                mFavDb.insert(countryName,countryName+" "+countryLanguage+" "+countryCallCode +" "+imageLogo);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = Uri.parse(imageLogo);
        Cursor cursor = mFavDb.fetch();
        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(CountryFav.COLUMN_COUNTRY));
                String info = cursor.getString(cursor.getColumnIndex(CountryFav.COLUMN_INFO));
                Log.i("PRAVTEST"," name "+name +" info "+info);
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();


        getGlideRquest()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(logo);



    }

    public GenericRequestBuilder getGlideRquest(){
        return Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.color.green)
                .error(R.color.green)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }
}
