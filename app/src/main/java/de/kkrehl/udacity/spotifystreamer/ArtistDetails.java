package de.kkrehl.udacity.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.collect.ImmutableMap;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ArtistDetails extends ActionBarActivity {

    ListView mTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        String artistName = "";
        String artistID = "";
        Intent intent = getIntent();
        if (intent != null) {
            artistID = intent.getStringExtra(MainActivity.ARTIST_ID);
            artistName = intent.getStringExtra(MainActivity.ARTIST_NAME);
        } else {
            finish(); //Abort the activity if there is no intent
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Top 10 Tracks");
            actionBar.setSubtitle(artistName);
        }
        final SpotifyApi spotifyApi = new SpotifyApi();
        final SpotifyService spotifyService = spotifyApi.getService();
        mTracks = (ListView) findViewById(R.id.trackList);
        final TracksArrayAdapter tracksArrayAdapter = new TracksArrayAdapter(this);
        mTracks.setAdapter(tracksArrayAdapter);

        spotifyService.getArtistTopTrack(artistID, ImmutableMap.<String, Object>of("country", "de"), new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                if (tracks.tracks.size() == 0) {
                    tracksArrayAdapter.clear();
                    Toast.makeText(getApplicationContext(), "No tracks found.", Toast.LENGTH_SHORT).show();
                } else {
                    tracksArrayAdapter.clear();
                    for (Track track: tracks.tracks) {
                        tracksArrayAdapter.add(track);
                    }
                    tracksArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("Spotify","Could not load top tracks");
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artist_details, menu);
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
