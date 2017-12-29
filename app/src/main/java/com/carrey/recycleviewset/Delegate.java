package com.carrey.recycleviewset;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carrey.recycleviewset.view.recycleview.adapter.ItemViewDelegate;

/**
 * Created by pccai on 2017/12/29.
 */

public interface Delegate {

    class Delegate1 extends ItemViewDelegate<Data.Data1, VH1> {

        @NonNull
        @Override
        protected VH1 onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            return new VH1(textView);
        }

        @Override
        protected void onBindViewHolder(@NonNull VH1 holder, @NonNull Data.Data1 item) {
            ((TextView) holder.itemView).setText(item.hashCode() + "");

        }
    }

    class VH1 extends RecyclerView.ViewHolder {

        public VH1(View itemView) {
            super(itemView);
        }
    }

    class Delegate2 extends ItemViewDelegate<Data.Data1, VH2> {

        @NonNull
        @Override
        protected VH2 onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            ImageView textView = new ImageView(parent.getContext());
            return new VH2(textView);
        }

        @Override
        protected void onBindViewHolder(@NonNull VH2 holder, @NonNull Data.Data1 item) {
            ((ImageView) holder.itemView).setBackgroundResource(R.mipmap.ic_launcher);

        }
    }

    class VH2 extends RecyclerView.ViewHolder {

        public VH2(View itemView) {
            super(itemView);
        }
    }

    class Delegate3 extends ItemViewDelegate<Data.Data2, VH2> {

        @NonNull
        @Override
        protected VH2 onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            ImageView textView = new ImageView(parent.getContext());
            return new VH2(textView);
        }

        @Override
        protected void onBindViewHolder(@NonNull VH2 holder, @NonNull Data.Data2 item) {
            ((ImageView) holder.itemView).setBackgroundResource(R.mipmap.ic_launcher);

        }
    }


}
