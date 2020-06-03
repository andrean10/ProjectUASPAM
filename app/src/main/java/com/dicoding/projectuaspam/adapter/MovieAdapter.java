package com.dicoding.projectuaspam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.projectuaspam.R;
import com.dicoding.projectuaspam.model.ResultsItem;
import com.dicoding.projectuaspam.view.DetailMovieActivity;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String URL_IMG = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private ArrayList<ResultsItem> data = new ArrayList<>();
    private OnItemClickCallBack onItemClickCallBack;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public MovieAdapter(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    public ArrayList<ResultsItem> getData() {
        return data;
    }

    public void setData(ArrayList<ResultsItem> resultsItems) {
        if (resultsItems.size() > 0) {
            data.clear();
        }
        data.addAll(resultsItems);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.items, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        ResultsItem item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(URL_IMG + item.getPosterPath())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.img_not_found)
                        .error(R.drawable.img_not_found)
                        .override(100, 150)
                )
                .into(holder.imgPhoto);
        holder.tvTitle.setText(item.getTitle());
        holder.tvRelease.setText(item.getReleaseDate());
        holder.tvDescription.setText(item.getOverview());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClickCallBack.OnItemClicked(data.get(holder.getAdapterPosition()));

                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_NAME, data.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickCallBack {
        void OnItemClicked(ResultsItem resultsItem);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvRelease, tvDescription;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_date);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
