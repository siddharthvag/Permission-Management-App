package com.example.myapplicationtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        SecondFragment.OnFragmentInteractionListener,
        ThirdFragment.OnFragmentInteractionListener,
        ViewPermission.OnFragmentInteractionListener,
        CreatePermissionFragment.OnFragmentInteractionListener,
        HODFragment.OnFragmentInteractionListener,
        PermissionHistory.OnFragmentInteractionListener {

    Button logoutBtn;
    private DrawerLayout mDrawer;
    private static final String TAG = "MainActivity";
    boolean isFABOpen = false;
    FloatingActionButton fab;
    private List<DocumentSnapshot> list;
    String HOD[] = {"s.tawde@somaiya.edu", "siddharth.pv@somaiya.edu", "hodextc@somaiya.edu", "hodetrx@somaiya.edu", "hodcomp@somaiya.edu", "hodmech@somaiya.edu"};
    String e;
    int flag = 0;
    ArrayList<String> hods = new ArrayList<>();


    @Override

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void logout() {
        mAuth.signOut();
        googleApiClient.clearDefaultAccountAndReconnect();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    FirebaseAuth mAuth;
    //TextView name = (TextView) findViewById(R.id.name);
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            e = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            flag = 0;
            for (int i = 0; i < HOD.length; i++) {
                if (HOD[i].equals(e))
                    flag = 1;
            }
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePermissionFragment dialog = new CreatePermissionFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, CreatePermissionFragment.TAG);
            }
        });
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame, new HomeFragment());
        tx.commit();
        setTitle("Home");


        /*Resources mResources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.top);
        bitmap = Bitmap.createScaledBitmap(bitmap,720,120, true);
        Bitmap bitmap1 = BitmapFactory.decodeResource(mResources, R.drawable.bottom);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,600,130, true);

        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(700, 1000, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 80, 10 , null);
        paint.setColor(Color.BLACK);
        paint.setTextSize(13);
        paint.setTypeface(Typeface.SERIF);
        String todayAsString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String p1 = "Date: " + todayAsString;
        canvas.drawText(p1, 500, 140, paint);
        String p2 = "Respected Ma'am/Sir, ";
        canvas.drawText(p2, 30, 320, paint);
        String p3 = "BloomBox is conducting ‘Chefpreneur’, a culinary event in association with KJSCE Symphony. We request you grant us the permission to use B-311 and B-310 for the same. We assure you that all the infrastructure of the room will be taken care of and no damage will be made.";
        TextPaint mTextPaint=new TextPaint();
        StaticLayout staticLayout = new StaticLayout(p3, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);
        canvas.translate(30,350);
        staticLayout.draw(canvas);
        //canvas.drawText(p3, 30, 350, paint);
        String p4 = "Thanking You";
        canvas.drawText(p4, 20, 150, paint);
        String sign = "SIGN HERE";
        canvas.drawText(sign, canvas.getWidth()-230, 250, paint);
        String h1 = "Mr. Shubham Tawde";
        canvas.drawText(h1, canvas.getWidth()-230, 270, paint);
        String h2 = "HOD, IT Department";
        canvas.drawText(h2, canvas.getWidth()-230, 290, paint);
        String h3 = "KJSCE";
        canvas.drawText(h3, canvas.getWidth()-230, 310, paint);
        int newx = 30;
        int newy = 500;
        canvas.drawBitmap(bitmap1, newx, newy, null);
        document.finishPage(page);
        String dir = Environment.getExternalStorageDirectory().getPath() + "/Permissions/";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String target = dir + "test.pdf";
        File filepath = new File(target);
        try {
            document.writeTo(new FileOutputStream(filepath));
            Toast.makeText(this, "Created PDF", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        document.close();
*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String name = user.getDisplayName();

            //Glide.with(imageView).load(user.getPhotoUrl()).into(imageView);

            NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            View headerView = nv.getHeaderView(0);
            TextView textView = (TextView) headerView.findViewById(R.id.name);
            textView.setText(name);
            Uri photo = user.getPhotoUrl();
            ImageView imageView = (ImageView) headerView.findViewById(R.id.profile_picture);
            Glide.with(imageView).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
            TextView email = (TextView) headerView.findViewById(R.id.email);
            email.setText(user.getEmail());
            Button logoutBtn = (Button) headerView.findViewById(R.id.nav_logout);
        }
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Context context = HomeActivity.this;
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(HomeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted!", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Toast.makeText(this, "Permission is necessary!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission is granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Write Permission is granted!", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Toast.makeText(this, "Write Permission is necessary!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission is granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Toast.makeText(this, "External Storage", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0], Toast.LENGTH_SHORT).show();
                    //resume tasks needing this permission
                }else{
                    Toast.makeText(this, "Storage Permission is necessary!", Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:
                Toast.makeText(this, "External Storage 2", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0], Toast.LENGTH_SHORT).show();
                    //resume tasks needing this permission
                }else{
                    Toast.makeText(this, "Storage Permission is necessary!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String title = "";
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                title = "Home";
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                fragmentClass = ThirdFragment.class;
                title = "About";
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:
                fragmentClass = PermissionHistory.class;
                title = "Permission History";
                Toast.makeText(this, "Permission History", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_tac:
                fragmentClass = SecondFragment.class;
                title = "Terms and Conditions";
                Toast.makeText(this, "Terms and Conditions", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_viewpermission:
                if (flag == 0)
                    fragmentClass = ViewPermission.class;
                else
                    fragmentClass = HODFragment.class;
                title = "View Permission";
                break;
            case R.id.nav_logout:
                fragmentClass = null;
                logout();
                break;
            default:
                title = "Home";
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
