package com.example.dahugrape.database.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dahugrape.database.model.Rating;
import com.example.dahugrape.database.repositories.RatingRepo;

import java.util.List;

public class RatingViewModel extends ViewModel {

    RatingRepo ratingRepo = new RatingRepo();

    MutableLiveData<Rating> ratingMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Rating>> getRatings(Activity activity) {
        return ratingRepo.getRatings(activity);
    }

    public LiveData<Rating> getRating() {
        return ratingMutableLiveData;
    }

    public void setRating(Rating rating) {
        ratingMutableLiveData.setValue(rating);
    }

}
