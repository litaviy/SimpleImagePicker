package com.rammstein.imagepickertest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.rammstein.imagepickertest.R;

import java.io.File;
import java.util.List;

/**
 * Created by klitaviy on 4/10/17.
 */

public class DeviceImagesAdapter extends RecyclerView.Adapter<DeviceImagesAdapter.DeviceImage> {

    public interface DeviceImageAdapterListener {
        void onItemSelect(final File file);
    }

    private Context mContext;
    private List<File> mImages;
    private DeviceImageAdapterListener mListener;

    public DeviceImagesAdapter(final Context context,
                               final List<File> images,
                               final DeviceImageAdapterListener listener) {
        mContext = context;
        mImages = images;
        mListener = listener;
    }

    @Override
    public DeviceImage onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new DeviceImage(
                LayoutInflater.from(mContext).inflate(R.layout.device_images_item_image, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(final DeviceImage holder, final int position) {
        Glide.with(mContext)
                .load(mImages.get(position))
                .crossFade()
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class DeviceImage extends RecyclerView.ViewHolder {

        private ImageView mImage;

        public DeviceImage(final View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.share_item_image);
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mListener.onItemSelect(mImages.get(getAdapterPosition()));
                }
            });
            mImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImage.getLayoutParams();
                    params.height = mImage.getWidth();
                    return true;
                }
            });
        }
    }
}
