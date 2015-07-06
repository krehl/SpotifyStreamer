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

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by krehl on 05.07.2015.
 */
public class TracksArrayAdapter extends ArrayAdapter<Track> {

    final static Boolean INDICATORS = false;
    Picasso picasso = Picasso.with(getContext());

    public TracksArrayAdapter(Context context) {
        super(context,R.layout.track_item,new ArrayList<Track>());
        picasso.setIndicatorsEnabled(INDICATORS);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        ViewHolder holder;
        if (null == convertView) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(R.layout.track_item, null);
            holder = new ViewHolder();
            holder.trackName = (TextView) convertView.findViewById(R.id.trackName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.trackImage);
            holder.trackAlbumName = (TextView) convertView.findViewById(R.id.trackAlbumName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Track track = getItem(position);
        if (track.album.images.size() != 0 && track.album.images.get(0).url != null) {
            picasso.load(track.album.images.get(0).url).placeholder(R.drawable.artist_placeholder).fit().centerCrop().into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.artist_placeholder);
        }
        holder.trackName.setText(track.name);
        holder.trackAlbumName.setText(track.album.name);
        return convertView;
    }

    class ViewHolder {
        TextView trackName;
        ImageView imageView;
        TextView trackAlbumName;
    }
}
