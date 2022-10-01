package com.example.textrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText mResultEt, mResultEt1;
    ImageView mPreviewIv;
    Button speechbtn,clearbtn, translatebtn;

    TextToSpeech textToSpeech;

    String currentLanguage = "";


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermisssion[];
    Uri image_uri;
    FirebaseTranslator englishGermanTranslator;
    Spinner spinner;
    String[] language = {"None", "Afrikaans", "Albanian", "Arabic", "Belarusian", "Bengali", "Bulgarian", "Catalan",
            "Chinese", "Croatian", "Czech",  "Danish", "Dutch", "English", "Esperanto", "Estonian", "Finnish",
            "French", "Galician", "German", "Georgian", "Greek", "Gujarati", "Haitian", "Hebrew", "Hindi",
            "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese",  "Kannada", "Korean",
            "Lithuanian", "Latvian", "Macedonian", "Malay", "Maltese", "Marathi", "Norwegian", "Persian",
            "Polish", "Portuguese", "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swedish",
            "Swahili", "Tamil", "Tagalog", "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese",
            "Welsh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        mResultEt = findViewById(R.id.resultEt);
        mResultEt1 = findViewById(R.id.resultEt1);
        mPreviewIv = findViewById(R.id.imageIv);
        speechbtn = findViewById(R.id.speech_button);
        clearbtn = findViewById(R.id.clear_button);
        translatebtn = findViewById(R.id.lan_button);
        spinner = findViewById(R.id.selectlanguage);


        ArrayAdapter<String> lang = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,language);
        lang.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mPreviewIv.setImageResource(R.drawable.ic_action_name);

        spinner.setAdapter(lang);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                currentLanguage = parent.getItemAtPosition(position).toString();
                if(value.equals("None"))
                {
                    Toast.makeText(MainActivity.this,"Please select the language",Toast.LENGTH_SHORT).show();
                }
                else if(value.equals("Afrikaans"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.AF)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Albanian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.SQ)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Arabic"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.AR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Belarusian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.BE)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Bengali"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.BN)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Bulgarian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.BG)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Catalan"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.CA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Chinese"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.ZH)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Croatian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.HR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Czech"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.CS)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Danish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.DA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Dutch"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.NL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("English"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.EN)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Esperanto"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.EO)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Estonian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.ET)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Finnish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.FI)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("French"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.FR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Galician"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.GL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("German"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.DE)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Georgian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.KA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Greek"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.EL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Gujarati"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.GU)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Haitian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.HT)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Hebrew"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.HE)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Hindi"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.HI)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Hungarian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.HU)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Icelandic"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.IS)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Indonesian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.ID)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Irish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.GA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Italian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.IT)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Japanese"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.JA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Kannada"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.KN)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Korean"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.KO)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Lithuanian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.LT)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Latvian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.LV)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Macedonian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.MK)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Malay"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.MS)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Maltese"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.MT)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Marathi"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.MR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Norwegian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.NO)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Persian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.FA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Polish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.PL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Portuguese"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.PT)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Romanian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.RO)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Russian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.RU)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Slovak"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.SK)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Slovenian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.SL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Spanish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.ES)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Swedish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.SV)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Swahili"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.SW)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Tamil"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.TA)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Tagalog"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.TL)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Telugu"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.TE)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Thai"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.TH)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Turkish"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.TR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Ukrainian"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.UK)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Urdu"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.UR)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Vietnamese"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.VI)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

                else if(value.equals("Welsh"))
                {
                    FirebaseTranslatorOptions options =
                            new FirebaseTranslatorOptions.Builder()
                                    // below line we are specifying our source language.
                                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                                    // in below line we are displaying our target language.
                                    .setTargetLanguage(FirebaseTranslateLanguage.CY)
                                    // after that we are building our options.
                                    .build();

                    englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        speechbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mResultEt.getText().toString();
                int speech = textToSpeech.speak(s,TextToSpeech.QUEUE_ADD,null);
                if(!mResultEt.getText().toString().matches(""))
                {
                    Toast.makeText(MainActivity.this, "Text to speech started. Please increase the volume", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Please insert some text", Toast.LENGTH_LONG).show();
                }
            }
        });
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultEt.setText("");
                mResultEt1.setText("");
                mPreviewIv.setImageResource(R.drawable.ic_action_name);
                int stop=textToSpeech.stop();
            }
        });

        translatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = mResultEt.getText().toString();
                if(currentLanguage.equals("None"))
                {
                    Toast.makeText(MainActivity.this," Please select the Language",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    translateToTelugu(string);
                }


            }
        });


        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermisssion = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    private void translateToTelugu(final String string) {
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();

        // below line is use to download our modal.
        englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // this method is called when modal is downloaded successfully.
                Toast.makeText(MainActivity.this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();

                // calling method to translate our entered text.
                translateLanguage(string);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to download modal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void translateLanguage(String input) {
        englishGermanTranslator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                mResultEt1.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage) {
            showImageImportDialog();
        }
        if (id == R.id.addImageText) {
            showImageImportDialog();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showImageImportDialog() {
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCamerapermission();
                    } else {
                        pickCamera();
                    }
                }
                if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragepermission();
                    } else {
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragepermission() {
        ActivityCompat.requestPermissions(this, storagePermisssion, STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCamerapermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                mPreviewIv.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if(!recognizer.isOperational()){
                    Toast.makeText(this, "Sorry! No Text Detected", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    for(int i=0; i<items.size(); i++){
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    mResultEt.setText(sb.toString());
                }
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}