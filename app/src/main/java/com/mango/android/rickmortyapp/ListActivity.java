package com.mango.android.rickmortyapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CharacterAdapter mCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        // init recycler view
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCharacterAdapter = new CharacterAdapter(new OnCharacterClickListener() {
            @Override
            public void onCharacterClicked(Character character) {
                DetailActivity.start(ListActivity.this, character.id);
            }
        });

        mRecyclerView.setAdapter(mCharacterAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<Character> characters = new GetCharactersTask().execute().get();
            mCharacterAdapter.bindData(characters);
        } catch (ExecutionException e) {
            e.printStackTrace();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(ServerErrorDialogFragment.newInstance(), null)
                    .commitAllowingStateLoss();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(ServerErrorDialogFragment.newInstance(), null)
                    .commitAllowingStateLoss();
        }
    }


    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------


    public interface OnCharacterClickListener {
        void onCharacterClicked(Character character);
    }

    public static class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

        private final OnCharacterClickListener mListener;

        private List<Character> mCharacterList = new ArrayList<>(0);

        public class CharacterViewHolder extends RecyclerView.ViewHolder {
            public TextView mName;

            public CharacterViewHolder(View view) {
                super(view);
                mName = view.findViewById(R.id.tv_name);
            }
        }

        public CharacterAdapter(OnCharacterClickListener onCharacterClickListener) {
            mListener = onCharacterClickListener;
        }

        public void bindData(List<Character> characters) {
            mCharacterList = new ArrayList<>(characters);
            notifyDataSetChanged();
        }

        @Override
        public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);

            return new CharacterViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CharacterViewHolder holder, final int position) {
            holder.mName.setText(mCharacterList.get(position).name);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onCharacterClicked(mCharacterList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCharacterList.size();
        }
    }

    public class GetCharactersTask extends AsyncTask<Void, Void, List<Character>> {

        @Override
        protected List<Character> doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("https://rickandmortyapi.com/api/character/");
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

                        try {
                            List<Character> list = new ArrayList<>();
                            JSONObject object = new JSONObject(json);
                            if (object.has("results")) {
                                String results = object.optString("results");
                                JSONArray jsonArray = new JSONArray(results);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Character parsedCharacter = parseCharacterJson(jsonArray.getString(i));
                                    if (parsedCharacter != null) list.add(parsedCharacter);
                                }
                                return list;
                            }
                            return list;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }
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
