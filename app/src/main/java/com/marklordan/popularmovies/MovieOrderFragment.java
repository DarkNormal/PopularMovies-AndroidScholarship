package com.marklordan.popularmovies;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by mark on 30/01/17.
 */

public class MovieOrderFragment extends DialogFragment {

    private IOrderSelection mCallback;
    private static final String SORT_ORDER_KEY = "com.marklordan.popularmovies.SORT_ORDER";

    public static MovieOrderFragment newInstance(String sortOrder) {

        Bundle args = new Bundle();
        args.putString(SORT_ORDER_KEY, sortOrder);
        MovieOrderFragment fragment = new MovieOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (IOrderSelection) context;
        }
        catch(ClassCastException e){
            Log.e("MovieOrderFragment", e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String sortOrder = getArguments().getString(SORT_ORDER_KEY);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_sort_order, null);
        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.order_radio_group);
        final RadioButton topRatedButton = (RadioButton) v.findViewById(R.id.top_rated_radio_button);
        final RadioButton mostPopularButton = (RadioButton) v.findViewById(R.id.popular_radio_button);
        final RadioButton favouriteButton = (RadioButton) v.findViewById(R.id.favourite_radio_button);
        if(sortOrder.equals(getString(R.string.popular))){
            mostPopularButton.setChecked(true);
        }
        else if(sortOrder.equals(getString(R.string.rated))){
            topRatedButton.setChecked(true);
        }
        else{
            favouriteButton.setChecked(true);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Movie Order")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedOption = radioGroup.getCheckedRadioButtonId();
                        String sort_order = getString(R.string.popular);
                        if(selectedOption == topRatedButton.getId()){
                            sort_order = getString(R.string.rated);
                        }
                        else if(selectedOption == favouriteButton.getId()){
                            sort_order = getString(R.string.favourites);
                        }
                        mCallback.onSelectedOrderOption(sort_order);
                    }
                })
                .create();
    }
}
