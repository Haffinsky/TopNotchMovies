package com.haffa.topnotchmovies.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.haffa.topnotchmovies.Data.SearchResultsDataFetcher;
import com.haffa.topnotchmovies.DetailActivity;
import com.haffa.topnotchmovies.SearchResultsActivity;

import static com.haffa.topnotchmovies.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/24/2017.
 */

public class SearchDialog {

    public void showSearchDialog(final Activity act){

        new MaterialDialog.Builder(act)
                .content("Search for a movie")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText("OK")
                .negativeText("NEVERMIND")
                .input("type a keyword", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Log.v("input is ", String.valueOf(input));
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                SearchResultsDataFetcher searchResults = new SearchResultsDataFetcher();
                String queryParameter = dialog.getInputEditText().getText().toString();

                try {
                    searchResults.run(queryParameter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(act, SearchResultsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
            }
        })
                .show();

    }
}
