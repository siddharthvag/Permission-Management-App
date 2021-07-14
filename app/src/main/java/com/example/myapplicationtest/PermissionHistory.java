package com.example.myapplicationtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermissionHistory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermissionHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermissionHistory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView load;
    private ProgressBar load2;
    View parentHolder;
    private String email;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PermissionsAdapter permissionsAdapter;
    private List<Permissions> namesList = new ArrayList<>();
    private PermissionsAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public PermissionHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermissionHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static PermissionHistory newInstance(String param1, String param2) {
        PermissionHistory fragment = new PermissionHistory();
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
        parentHolder = inflater.inflate(R.layout.fragment_permission_history, container, false);
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        load = (TextView) parentHolder.findViewById(R.id.l2);
        load2 = (ProgressBar) parentHolder.findViewById(R.id.progress);
        recyclerView = (RecyclerView) parentHolder.findViewById(R.id.main_list);
        adapter = new PermissionsAdapter(namesList);
        LinearLayoutManager ln = new LinearLayoutManager(this.getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(ln);
        recyclerView.setAdapter(adapter);
        db.collection("permissions").whereEqualTo("created",email).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        load.setVisibility(View.INVISIBLE);
        load2.setVisibility(View.INVISIBLE);
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
