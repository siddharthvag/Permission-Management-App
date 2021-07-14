package com.example.myapplicationtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class RoomHOD extends AppCompatActivity {

    private String ID;
    private String status;
    private Button accept;
    private Button reject;
    private String name;
    private String names[];
    AlertDialog.Builder b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_hod);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
        TableLayout choice = (TableLayout) findViewById(R.id.choice);
        TableLayout accepted = (TableLayout) findViewById(R.id.accepted);
        TableLayout rejected = (TableLayout) findViewById(R.id.rejected);
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.reject);
        b = new AlertDialog.Builder(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
        ArrayList<String> data = getIntent().getStringArrayListExtra("data");
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(data.get(0));
        TextView councilDetail = (TextView) findViewById(R.id.council_detail);
        councilDetail.setText(data.get(1));
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(data.get(2));
        TextView locationDetail = (TextView) findViewById(R.id.place_location);
        locationDetail.setText(data.get(3));
        status = data.get(4);
        ID = data.get(5);
        TextView date = (TextView) findViewById(R.id.place_date);
        date.setText(data.get(6));
        TextView time = (TextView) findViewById(R.id.place_time);
        time.setText(data.get(8));
        name = data.get(9);
        names = name.split(",");
        if(status.equals("pending"))
        {
            choice.setVisibility(View.VISIBLE);
        }
        else if(status.equals("accepted"))
        {
            accepted.setVisibility(View.VISIBLE);
        }
        else if(status.equals("rejected"))
        {
            rejected.setVisibility(View.VISIBLE);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setMessage("Are you sure you want to accept?").setTitle("Confirmation")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore.getInstance().collection("permissions").document(ID).update("status", "accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RoomHOD.this, "Success Accept Update", Toast.LENGTH_SHORT).show();
                                        choice.setVisibility(View.INVISIBLE);
                                        accepted.setVisibility(View.VISIBLE);
                                        rejected.setVisibility(View.INVISIBLE);
                                        Resources mResources = getResources();
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
                                        String p3 = data.get(1) + " is conducting ‘ "+data.get(0) + "’, " + data.get(2) + ". We request you to grant us the permission to use the room " + data.get(3) + " for  " + data.get(6)+ ". We assure you that all the infrastructure of the room will be taken care of and no damage will be made.";
                                        TextPaint mTextPaint=new TextPaint();
                                        StaticLayout staticLayout = new StaticLayout(p3, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);
                                        canvas.translate(30,350);
                                        staticLayout.draw(canvas);
                                        //canvas.drawText(p3, 30, 350, paint);
                                        String p4 = "Thanking You";
                                        canvas.drawText(p4, 20, 150, paint);
                                        paint.setColor(Color.parseColor("#4CAF50"));
                                        String sign = "ACCEPTED PERMISSION";
                                        canvas.drawText(sign, canvas.getWidth()-230, 240, paint);
                                        paint.setColor(Color.BLACK);
                                        //String h1 = "Mr. Shubham Tawde";
                                        canvas.drawText(names[0], canvas.getWidth()-230, 270, paint);
                                        String h2 = "HOD, " + names[1];
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
                                        String name = data.get(5);
                                        String target = dir + name + ".pdf";
                                        File filepath = new File(target);
                                        try {
                                            document.writeTo(new FileOutputStream(filepath));
                                            Toast.makeText(RoomHOD.this, "Created PDF", Toast.LENGTH_SHORT).show();
                                        } catch (IOException e) {
                                            Toast.makeText(RoomHOD.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        document.close();
                                        String toEmail = data.get(7);
                                        String sub = "Accepted Permission for Event " + data.get(0);

                                        /*
                                        Recommended to use App Passwords. Refer to https://support.google.com/accounts/answer/185833?hl=en for more info.
                                         */
                                        String sendEmail = "youremail@example.com";
                                        String sendPassword = "yourpassword";

                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                                                Looper.prepare();
                                                send(sendEmail, sendPassword, toEmail, sub, "Find attached herewith the accepted Permission Letter");
                                            }
                                        });
                                        thread.start();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RoomHOD.this, " Accept", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog a = b.create();
                a.setTitle("Confirmation");
                a.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setMessage("Are you sure you want to reject?").setTitle("Confirmation")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore.getInstance().collection("permissions").document(ID).update("status", "rejected").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RoomHOD.this, "Success Reject Update", Toast.LENGTH_SHORT).show();
                                        choice.setVisibility(View.INVISIBLE);
                                        accepted.setVisibility(View.INVISIBLE);
                                        rejected.setVisibility(View.VISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RoomHOD.this, "Failed Reject", Toast.LENGTH_SHORT).show();
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
            }
        });
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
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(msg);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String dir = Environment.getExternalStorageDirectory().getPath() + "/Permissions/";
            String filename = dir + ID + ".pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            //send message
            Transport.send(message);
            Toast.makeText(RoomHOD.this, "Save", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(RoomHOD.this, "Fail", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }

}
