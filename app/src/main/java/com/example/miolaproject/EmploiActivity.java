package com.example.miolaproject;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EmploiActivity extends AppCompatActivity {

    private Button dowlond;
    String getUrl;
    ImageView upload;
    Uri imageuri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi);
        dowlond =findViewById(R.id.download);
        getUrl= "https://firebasestorage.googleapis.com/v0/b/miolaproject-56510.appspot.com/o/S2-Sem12-2022.pdf?alt=media&token=d88ea755-2caa-49cd-b598-688eaa8a37e7" ;
        dowlond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(getUrl));
                String title = URLUtil.guessFileName(getUrl,null,null);
                request.setTitle(title);
                request.setDescription("Dowloading file please .........");
                String cookies = CookieManager.getInstance().getCookie(getUrl);
                request.addRequestHeader("cookie",cookies);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                Toast.makeText(EmploiActivity.this, "Dowloading started", Toast.LENGTH_SHORT).show();
            }
        });
        upload = findViewById(R.id.uploadpdf);

        // After Clicking on this we will be
        // redirected to choose pdf
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

    }
    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(EmploiActivity.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            Toast.makeText(EmploiActivity.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        Toast.makeText(EmploiActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(EmploiActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
