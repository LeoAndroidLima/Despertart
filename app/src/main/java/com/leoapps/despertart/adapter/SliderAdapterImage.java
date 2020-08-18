package com.leoapps.despertart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leoapps.despertart.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterImage extends SliderViewAdapter<SliderAdapterImage.SliderAdapterVH> {

    private Context context;
    private List<String> urls = new ArrayList<String>();

    public SliderAdapterImage(Context context, List<String> urls){

        this.context = context;
        this.urls = urls;

    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_imagem, null);

        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterImage.SliderAdapterVH viewHolder, int position) {

        String url = urls.get(position);
        Glide.with(context).load(url).into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        return urls.size();
    }


    class SliderAdapterVH extends SliderViewAdapter.ViewHolder{

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;

        }
    }
}
