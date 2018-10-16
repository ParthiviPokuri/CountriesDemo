package com.example.praveenp.logitechtestapp.adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.praveenp.logitechtestapp.R;
import com.example.praveenp.logitechtestapp.dao.Country;

import java.io.InputStream;
import java.util.List;

/**
 * Created by praveenp on 24-01-2016.
 */
public class DeviceAdapter extends BaseAdapter {

    private List<Country> mCountryList;
    private Context mContext;
    private GenericRequestBuilder<Uri,InputStream,SVG,PictureDrawable> mRequestBuilder;
    public DeviceAdapter(Context context, List<Country> list){
        mContext = context;
        mCountryList = list;

        mRequestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
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

    public void updateDeviceInfo(List<Country> list){
        mCountryList = list;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mCountryList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mCountryList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.devices_row, null);
            holder = new ViewHolder();
            holder.mCOuntryName = (TextView) convertView.findViewById(R.id.country_name);
            holder.mCountryLogo = (ImageView) convertView.findViewById(R.id.country_logo);
            convertView.setTag(holder);

        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCOuntryName.setText(((Country) getItem(position)).getName());
        Log.i("RAMATEST"," Flag Url "+((Country)getItem(position)).getFlag());
        Uri uri = Uri.parse(((Country)getItem(position)).getFlag());
        mRequestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(holder.mCountryLogo);
        return convertView;
    }

    static class ViewHolder{
        TextView mCOuntryName;
        ImageView mCountryLogo;
    }
}
