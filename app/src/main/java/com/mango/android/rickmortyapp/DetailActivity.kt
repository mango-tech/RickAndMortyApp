package com.mango.android.rickmortyapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID";

    public static void start(Context context, int characterId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER_ID, characterId);
        context.startActivity(intent);
    }

    private TextView mNameTv;
    private TextView mStatusTv;
    private TextView mSpeciesTv;
    private TextView mTypeTv;
    private TextView mGenderTv;

    private int mCharacterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        mNameTv = findViewById(R.id.tv_name_item);
        mStatusTv = findViewById(R.id.tv_status_item);
        mSpeciesTv = findViewById(R.id.tv_species_item);
        mTypeTv = findViewById(R.id.tv_type_item);
        mGenderTv = findViewById(R.id.tv_gender_item);

        //noinspection ConstantConditions
        mCharacterId = getIntent().getExtras().getInt(EXTRA_CHARACTER_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Character character = new GetCharacterDetailTask().execute(mCharacterId).get();
            mNameTv.setText(character.name);
            mStatusTv.setText(character.status);
            mSpeciesTv.setText(character.species);
            mTypeTv.setText(character.type);
            mGenderTv.setText(character.gender);
        } catch (ExecutionException e) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(ServerErrorDialogFragment.newInstance(), null)
                    .commitAllowingStateLoss();
        } catch (InterruptedException e) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(ServerErrorDialogFragment.newInstance(), null)
                    .commitAllowingStateLoss();        }
    }

    public class GetCharacterDetailTask extends AsyncTask<Integer, Void, Character> {


        @Override
        protected Character doInBackground(Integer... integers) {
            URL url = null;
            try {
                url = new URL("https://rickandmortyapi.com/api/character/" + integers[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = urlConnection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        String json = scanner.next();
                            return parseCharacterJson(json);
                    } else {
                        return null;
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Character parseCharacterJson(String string) {
            try {
                JSONObject object = new JSONObject(string);
                Character c = new Character();
                if (object.has("id")) {
                    c.id = object.optInt("id");
                }
                if (object.has("name")) {
                    c.name = object.optString("name");
                }
                if (object.has("status")) {
                    c.status  = object.optString("status");
                }
                if (object.has("species")) {
                    c.species = object.optString("species");
                }
                if (object.has("type")) {
                    c.type = object.optString("type");
                }
                if (object.has("gender")) {
                    c.gender = object.optString("gender");
                }
                if (object.has("image")) {
                    c.image = object.optString("image");
                }
                return c;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }
}
