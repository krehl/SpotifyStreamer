package de.kkrehl.udacity.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by krehl on 05.07.2015.
 */
public class TracksArrayAdapter extends ArrayAdapter<Track> {

    private static SpotifyService spotify;
    Picasso picasso = Picasso.with(getContext());
    final static Boolean INDICATORS = true;

    public TracksArrayAdapter(Context context, SpotifyService spotify) {
        super(context,R.layout.track_item,new ArrayList<Track>());
        this.spotify = spotify;
        picasso.setIndicatorsEnabled(INDICATORS);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Context context = getContext();

        if (null == v) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.track_item, null);
        }

        TextView tvTrack = (TextView) v.findViewById(R.id.trackName);
        ImageView imageView = (ImageView) v.findViewById(R.id.trackImage);
        TextView tvAlbum = (TextView) v.findViewById(R.id.trackAlbumName);
        Track track = getItem(position);
        if (track.album.images.size() != 0) {
            picasso.load(track.album.images.get(0).url).placeholder(R.drawable.artist_placeholder).fit().centerCrop().into(imageView);
        } else {
            imageView.setImageResource(R.drawable.artist_placeholder);
        }
        tvTrack.setText(track.name);
        tvAlbum.setText(track.album.name);
        return v;
    }
}
