package de.kkrehl.udacity.spotifystreamer;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Hashtable;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by krehl on 05.07.2015.
 */
public class ArtistsArrayAdapter extends ArrayAdapter<Artist> {

    private static SpotifyService spotify;
    Picasso picasso = Picasso.with(getContext());
    final static Boolean INDICATORS = true;

    public ArtistsArrayAdapter(Context context, SpotifyService spotify) {
        super(context,R.layout.artist_item,new ArrayList<Artist>());
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
            v = vi.inflate(R.layout.artist_item, null);
        }

        TextView artistName = (TextView) v.findViewById(R.id.artistName);
        ImageView imageView = (ImageView) v.findViewById(R.id.artistImage);
        Artist artist = getItem(position);
        if (artist.images.size() != 0) {
            picasso.load(artist.images.get(0).url).placeholder(R.drawable.artist_placeholder).fit().centerCrop().into(imageView);
        } else {
            imageView.setImageResource(R.drawable.artist_placeholder);
        }
        artistName.setText(artist.name);
        return v;
    }
}
