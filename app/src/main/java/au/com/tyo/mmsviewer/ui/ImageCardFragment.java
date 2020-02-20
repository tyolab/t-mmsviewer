package au.com.tyo.mmsviewer.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;

import au.com.tyo.mmsviewer.R;


public class ImageCardFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_URI = "uri";

    private int id;
    private String uri;
    private OnActionListener actionListener;
    private ImageView imageView;

    public ImageCardFragment() {

    }

    public static ImageCardFragment newInstance(int id, File file) {
        ImageCardFragment fragment = new ImageCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_URI, Uri.fromFile(file).toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getInt(ARG_ID);
            this.uri = getArguments().getString(ARG_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_card, container, false);

        imageView = v.findViewById(R.id.imageView1);
        imageView.setImageURI(Uri.parse(this.uri));

        return v;
    }


    public void onAction() {
        if (actionListener != null) {
            actionListener.onAction(this.id);
        }
    }

    // @Override
    // public void onAttach(Context context) {
    //     super.onAttach(context);
    //     if (context instanceof OnActionListener) {
    //         actionListener = (OnActionListener) context;
    //     } else {
    //         throw new RuntimeException(context.toString()
    //                 + " must implement OnActionListener");
    //     }
    // }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    public interface OnActionListener {
        void onAction(int id);
    }
}
