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

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by krehl on 05.07.2015.
 */
public class ArtistsArrayAdapter extends ArrayAdapter<Artist> {

    final static Boolean INDICATORS = false;
    Picasso picasso = Picasso.with(getContext());

    public ArtistsArrayAdapter(Context context) {
        super(context,R.layout.artist_item,new ArrayList<Artist>());
        picasso.setIndicatorsEnabled(INDICATORS);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(R.layout.artist_item, null);
            holder = new ViewHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.artistName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.artistImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Artist artist = getItem(position);
        if (artist.images.size() != 0 && artist.images.get(0).url != null) {
            picasso.load(artist.images.get(0).url).placeholder(R.drawable.artist_placeholder).fit().centerCrop().into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.artist_placeholder);
        }
        holder.artistName.setText(artist.name);
        return convertView;
    }

    class ViewHolder {
        TextView artistName;
        ImageView imageView;
    }
}
