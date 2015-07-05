package de.kkrehl.udacity.spotifystreamer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.common.collect.ImmutableMap;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Query;
import retrofit.http.QueryMap;


public class ArtistDetails extends ActionBarActivity {
    final static String ARTIST_ID = "artist_id";
    ListView mTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        final SpotifyApi spotifyApi = new SpotifyApi();
        final SpotifyService spotifyService = spotifyApi.getService();
        mTracks = (ListView) findViewById(R.id.trackList);
        final TracksArrayAdapter tracksArrayAdapter = new TracksArrayAdapter(this, spotifyService);
        mTracks.setAdapter(tracksArrayAdapter);
        Intent intent = getIntent();
        String artistID = intent.getStringExtra(ARTIST_ID);


        spotifyService.getArtistTopTrack(artistID, ImmutableMap.<String, Object>of("country", "de"), new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                tracksArrayAdapter.clear();
                for (Track track: tracks.tracks) {
                    tracksArrayAdapter.add(track);
                }
                tracksArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("Spotify","Could not load top tracks");
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
