package com.example.myspotify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

public class DetallesFragment extends Fragment {

    private TextView artistTextView;
    private TextView songTextView;
    private ImageView artworkImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles, container, false);

        artistTextView = view.findViewById(R.id.artistTextView);
        songTextView = view.findViewById(R.id.songTextView);
        artworkImageView = view.findViewById(R.id.artworkImageView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String artista = bundle.getString("artista", "");
            String cancion = bundle.getString("cancion", "");
            String imagenUrl = bundle.getString("imagen", "");

            artistTextView.setText(artista);
            songTextView.setText(cancion);
            Glide.with(requireActivity()).load(imagenUrl).into(artworkImageView);
        }

        return view;
    }
}
