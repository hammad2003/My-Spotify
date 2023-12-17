package com.example.myspotify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myspotify.databinding.FragmentItunesBinding;
import com.example.myspotify.databinding.ViewholderContenidoBinding;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myspotify.databinding.FragmentItunesBinding;
import com.example.myspotify.databinding.ViewholderContenidoBinding;
import java.util.List;

public class ItunesFragment extends Fragment {

    private FragmentItunesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentItunesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar mainToolbar = requireActivity().findViewById(R.id.toolbar);
        mainToolbar.setTitle("Buscar");

        ItunesViewModel itunesViewModel = new ViewModelProvider(this).get(ItunesViewModel.class);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                itunesViewModel.buscar(s);
                return false;
            }
        });

        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        contenidosAdapter.setOnItemClickListener(contenido -> abrirDetallesFragment(contenido));
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        itunesViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Itunes.Respuesta>() {
            @Override
            public void onChanged(Itunes.Respuesta respuesta) {
                if (respuesta != null) {
                    contenidosAdapter.establecerListaContenido(respuesta.results);
                }
            }
        });
    }

    private void abrirDetallesFragment(Itunes.Contenido contenido) {
        Bundle bundle = new Bundle();
        bundle.putString("artista", contenido.artistName);
        bundle.putString("cancion", contenido.trackName);
        bundle.putString("imagen", contenido.artworkUrl100);

        DetallesFragment detallesFragment = new DetallesFragment();
        detallesFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detallesFragment)
                .addToBackStack(null)
                .commit();
    }
}


//public class ItunesFragment extends Fragment {
//
//    private FragmentItunesBinding binding;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return (binding = FragmentItunesBinding.inflate(inflater, container, false)).getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Toolbar mainToolbar = requireActivity().findViewById(R.id.toolbar);
//        mainToolbar.setTitle("Buscar");
//
//        ItunesViewModel itunesViewModel = new ViewModelProvider(this).get(ItunesViewModel.class);
//
//        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) { return false; }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                itunesViewModel.buscar(s);
//                return false;
//            }
//        });
//
//        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
//        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);
//
//        itunesViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Itunes.Respuesta>() {
//            @Override
//            public void onChanged(Itunes.Respuesta respuesta) {
//                if (respuesta != null) {
//                    contenidosAdapter.establecerListaContenido(respuesta.results);
//                }
//            }
//        });
//
//    }
//
//    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
//        ViewholderContenidoBinding binding;
//
//        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
//
//    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder>{
//        List<Itunes.Contenido> contenidoList;
//
//        @NonNull
//        @Override
//        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
//            Itunes.Contenido contenido = contenidoList.get(position);
//
//            holder.binding.title.setText(contenido.trackName);
//            holder.binding.artist.setText(contenido.artistName);
//            Glide.with(requireActivity()).load(contenido.artworkUrl100).into(holder.binding.artwork);
//        }
//
//        @Override
//        public int getItemCount() {
//            return contenidoList == null ? 0 : contenidoList.size();
//        }
//
//        void establecerListaContenido(List<Itunes.Contenido> contenidoList){
//            this.contenidoList = contenidoList;
//            notifyDataSetChanged();
//        }
//    }
//}