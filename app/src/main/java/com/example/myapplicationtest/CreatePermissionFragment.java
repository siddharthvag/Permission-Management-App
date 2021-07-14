package com.example.myapplicationtest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreatePermissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreatePermissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePermissionFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = "FullScreenDialog";


    String[] country = {"B-507", "B-312", "A-315", "A-314", "A-225", "A-409", "B-517", "B-413", "B-113", "B-112"};
    TextView s;
    int flag = 0;
    public Date date1 = new Date();
    TextView toEmail;
    String sub;
    private Button check;
    String msg;
    String email;

    String name;
    private String sendEmail;
    private FirebaseFirestore db;
    String aemail;
    private String s2 = "B-113";
    private String f = s2;
    private String l2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Permissions> namesList = new ArrayList<>();
    private PermissionsAdapter adapter;
    EditText eventname, council, descr;
    TextView date, txtDate, txtTime, time1, txtTime1, time2, tandc;
    Button button;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Toolbar toolbar;
    AlertDialog.Builder b;


    private OnFragmentInteractionListener mListener;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public CreatePermissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePermissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePermissionFragment newInstance(String param1, String param2) {
        CreatePermissionFragment fragment = new CreatePermissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_permission, container, false);
        tandc = (TextView) view.findViewById(R.id.tandc);
        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        db = FirebaseFirestore.getInstance();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.getContext());
        String r;
        s = (TextView) view.findViewById(R.id.text1);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            aemail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
            name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        }
        txtDate = (TextView) view.findViewById(R.id.in_date);
        txtTime = (TextView) view.findViewById(R.id.in_time);
        txtTime1 = (TextView) view.findViewById(R.id.in_time2);
        check = (Button) view.findViewById(R.id.check);
        button = (Button) view.findViewById(R.id.bu1);
        eventname = (EditText) view.findViewById(R.id.t2);
        council = (EditText) view.findViewById(R.id.text2);
        date = (TextView) view.findViewById(R.id.in_date);
        time1 = (TextView) view.findViewById(R.id.in_time);
        time2 = (TextView) view.findViewById(R.id.in_time2);
        descr = (EditText) view.findViewById(R.id.te3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        txtTime1 = (TextView) view.findViewById(R.id.in_time2);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time();
            }
        });
        txtTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time1();
            }
        });
//Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), country[i], Toast.LENGTH_LONG).show();
                s2 = country[i];
                f = s2;
                Log.d("Room", country[i]);
                db.collection("hods").document(country[i]).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        s.setText(documentSnapshot.get("hod").toString());
                        l2 = documentSnapshot.get("hod").toString();
                        sendEmail = documentSnapshot.get("email").toString();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(view1 -> cancel());
        toolbar.setTitle("New Permission");


        Context c = this.getContext();
        adapter = new PermissionsAdapter(namesList);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(c);

                builder.setMessage("Check Previous").setCancelable(true).setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(c, "Dismissed", Toast.LENGTH_SHORT).show();
                        namesList.clear();
                        dialogInterface.cancel();
                    }
                });
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_check_previous, null);
                RecyclerView recyclerView = dialogView.findViewById(R.id.main_list);

                recyclerView.setLayoutManager(new LinearLayoutManager(c));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

                Toast.makeText(c, f, Toast.LENGTH_SHORT).show();
                db.collection("permissions").whereEqualTo("room", f).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                //s1.setVisibility(View.INVISIBLE);
                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();

                dialog.show();

            }
        });


        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build an AlertDialog
                AlertDialog.Builder build = new AlertDialog.Builder(c);

                build.setMessage("Terms and Conditions").setCancelable(true).setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(c, "Dismissed", Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
                LayoutInflater inf = getLayoutInflater();
                View dialogV = inf.inflate(R.layout.fragment_second, null);
                build.setView(dialogV);

                final AlertDialog dial = build.create();

                dial.show();
            }
        });

        return view;
    }

    private void cancel() {
        Toast.makeText(getContext(), "Cancel Action", Toast.LENGTH_SHORT).show();
        dismiss();
    }


    private void time1() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime1.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void time() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void date() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void validate() throws ParseException {
        Context context = this.getContext();
        flag = 0;
        if (eventname.getText().toString().isEmpty()) {
            eventname.setError("Field Empty");
            flag = 1;
        }
        if (date.getText().toString().isEmpty()) {
            date.setError("Field Empty");
            flag = 1;
        }
        if (council.getText().toString().isEmpty()) {
            council.setError("Field Empty");
            flag = 1;
        }
        if (time1.getText().toString().isEmpty()) {
            time1.setError("Field Empty");
            flag = 1;
        }
        if (time2.getText().toString().isEmpty()) {
            time2.setError("Field Empty");
            flag = 1;
        }
        if (descr.getText().toString().isEmpty()) {
            descr.setError("Field Empty");
            flag = 1;
        }
        if (!date.getText().toString().isEmpty()) {
            Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(date.getText().toString());
            boolean d = date1.before(new Date());
            if (!d) {
                flag = 1;
                date.setError("Date should be ahead of current Date!");
                Toast.makeText(context, "Date should be ahead of current Date!", Toast.LENGTH_SHORT).show();
                date.requestFocus();
            } else flag = 0;
        }
        if (flag == 0) {
            b = new AlertDialog.Builder(this.getContext());
            b.setMessage("Are you sure you want to create new Permission?").setTitle("Confirmation")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            String c = council.getText().toString();
                            String en = eventname.getText().toString();
                            String d = date.getText().toString();
                            String st = time1.getText().toString();
                            String et = time2.getText().toString();
                            String des = descr.getText().toString();
                            Map<String, String> user = new HashMap<>();
                            user.put("council", c);
                            user.put("title", en);
                            user.put("created", aemail);
                            user.put("date", d);
                            String t = st + "-" + et;
                            user.put("time", t);
                            user.put("description", des);
                            user.put("name", name);
                            user.put("room", f);
                            user.put("hod_name", l2);
                            user.put("date_create", new Date().toString());
                            user.put("hod", sendEmail);
                            user.put("status", "pending");
                            sub = "Permission for " + en + ", by " + c;
                            msg = "Permission for " + en + " by " + c + "\nDescription:- " + des + "\nDate/Time :- " + d + " " + st + "- " + et + "\nRoom:- " + f;
                            db.collection("permissions").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(context, "hurray", Toast.LENGTH_SHORT).show();
                                    Bundle params = new Bundle();
                                    params.putString("email", aemail);
                                    params.putString("council", c);
                                    mFirebaseAnalytics.logEvent("create", params);

                                    /*
                                        Recommended to use App Passwords. Refer to https://support.google.com/accounts/answer/185833?hl=en for more info.
                                         */
                                    String fromSendEmail = "youremail@example.com";
                                    String sendPassword = "yourpassword";

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                                            Looper.prepare();
                                            send(fromSendEmail, sendPassword, sendEmail, sub, msg);
                                        }
                                    });
                                    thread.start();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "fai", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog a = b.create();
            a.setTitle("Confirmation");
            a.show();
        } else {
            Toast.makeText(this.getContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }


    public void send(String from, String password, String to, String sub, String msg) {
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
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
