package de.kkrehl.udacity.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    final static String ARTIST_ID = "artist_id";
    final static String ARTIST_NAME = "artist_name";
    final static String SEARCH_TERM = "SearchTerm";
    static ArtistsArrayAdapter artistArrayAdapter;
    ListView mArtists;
    EditText mSearchField;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SEARCH_TERM, mSearchField.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpotifyApi api = new SpotifyApi();
        mArtists = (ListView) findViewById(R.id.artists);
        mSearchField = (EditText) findViewById(R.id.search_text);
        if (savedInstanceState != null) {
            mSearchField.setText(savedInstanceState.getString(SEARCH_TERM));
        }
        final SpotifyService spotify = api.getService();

        artistArrayAdapter = new ArtistsArrayAdapter(this);
        mArtists.setAdapter(artistArrayAdapter);

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                spotify.searchArtists(s.toString().concat("*"), new Callback<ArtistsPager>() {
                    @Override
                    public void success(ArtistsPager artistsPager, Response response) {
                        if (artistsPager.artists.items.size()==0) {
                            artistArrayAdapter.clear();
                            Toast.makeText(getApplicationContext(),"No artists found.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        artistArrayAdapter.clear();
                        for (Artist artist : artistsPager.artists.items) {
                            artistArrayAdapter.add(artist);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Connection error.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),ArtistDetails.class);
                Artist artist = artistArrayAdapter.getItem(position);
                intent.putExtra(ARTIST_ID, artist.id);
                intent.putExtra(ARTIST_NAME, artist.name);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
