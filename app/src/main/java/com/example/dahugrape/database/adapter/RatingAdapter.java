package com.example.dahugrape.database.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.databinding.C2ItemRatingBinding;

public class RatingAdapter extends ListAdapter<Rating, RatingAdapter.RatingViewHolder> {

    RatingInterface ratingInterface;

    public RatingAdapter(RatingInterface ratingInterface) {
        super(Rating.itemCallback);
        this.ratingInterface = ratingInterface;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 载入视图 Data binding
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        C2ItemRatingBinding c2ItemRatingBinding = C2ItemRatingBinding.inflate(layoutInflater, parent, false);
        // 应用interface
        c2ItemRatingBinding.setRatingInterface(ratingInterface);
        return new RatingViewHolder(c2ItemRatingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Rating rating = getItem(position);
        holder.ItemRatingBinding.setRating(rating);
        holder.ItemRatingBinding.executePendingBindings();
    }

    public interface RatingInterface {
    }

    class RatingViewHolder extends RecyclerView.ViewHolder {

        C2ItemRatingBinding ItemRatingBinding;

        public RatingViewHolder(C2ItemRatingBinding binding) {
            super(binding.getRoot());
            this.ItemRatingBinding = binding;
        }
    }
}
