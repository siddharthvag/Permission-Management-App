package com.example.myapplicationtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPermission.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPermission#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPermission extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PermissionsAdapter permissionsAdapter;
    Date date = new Date();
    View parentHolder;
    TextView textView1, Council, Date, Title, Description, LoadText, Book;
    ProgressBar progressBar, mprog;
    CardView cardView;
    private RecyclerView recyclerView;
    String[] rooms = {"B-507", "B-510", "B-315"};
    Spinner spinner;
    String selectedItem;
    private List<Permissions> namesList = new ArrayList<>();
    private PermissionsAdapter adapter;
    final int DATE_DIALOG_ID = 0;
    String council, title, description;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewPermission() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPermission.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPermission newInstance(String param1, String param2) {
        ViewPermission fragment = new ViewPermission();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        parentHolder = inflater.inflate(R.layout.fragment_view_permission, container, false);
        Button button = (Button) parentHolder.findViewById(R.id.select);
        LoadText = (TextView) parentHolder.findViewById(R.id.loadtext);
        Book = (TextView) parentHolder.findViewById(R.id.view);
        mprog = (ProgressBar) parentHolder.findViewById(R.id.spin_kit);
        Sprite rotatingPlane = new RotatingPlane();
        mprog.setIndeterminateDrawable(rotatingPlane);
        recyclerView = (RecyclerView) parentHolder.findViewById(R.id.main_list);
        spinner = (Spinner) parentHolder.findViewById(R.id.room_no);
        //spinner.setOnItemClickListener(this.getContext());
        ArrayAdapter<String> item = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, rooms);
        item.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                Context context = adapterView.getContext();
                Toast.makeText(context, selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapter = new PermissionsAdapter(namesList);
        LinearLayoutManager ln = new LinearLayoutManager(this.getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(ln);
        recyclerView.setAdapter(adapter);
        //cardView = (CardView) parentHolder.findViewById(R.id.card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namesList.clear();
                mprog.setVisibility(View.VISIBLE);
                LoadText.setVisibility(View.VISIBLE);
                db.collection("permissions").whereEqualTo("room",selectedItem).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        namesList.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Permissions permissions = doc.toObject(Permissions.class);
                            namesList.add(permissions);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                mprog.setVisibility(View.INVISIBLE);
                LoadText.setVisibility(View.INVISIBLE);
            }
        });

        return parentHolder;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
