package com.example.terminal.proyecto.apppt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.terminal.proyecto.apppt.R.layout.activity_menu;
import static com.example.terminal.proyecto.apppt.R.layout.perfil;


/**
 * Created by Jair Moreno on 05/08/2017.
 */

public class Perfil_fragment extends Fragment {

    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Context mContext;
    ImageView imageV;
    EditText editUsuario, editContrasenia;
    private Uri mSelectedImageUri;
    TextView Usuario, Contrasenia;
    FloatingActionButton guardarButton, fotoButton, editUserButton;
    String nomUsuario, passUsuario;
    LineChart chart;


    public Perfil_fragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState); System.out.print("hola perro");
        fotoButton = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);
        imageV = (ImageView) getView().findViewById(R.id.imageView);  //Imagen de perfil
        editUsuario = (EditText) getView().findViewById(R.id.editUsuario); //textEdit para que el usuario cambie su nombre de usuario
        Usuario = (TextView) getView().findViewById(R.id.usuario);
        Contrasenia = (TextView) getView().findViewById(R.id.contrasenia);
        editContrasenia = (EditText) getView().findViewById(R.id.editContrasenia);
        guardarButton = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton3);
        editUserButton = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton2);



        editUsuario.setVisibility(View.GONE);
        editContrasenia.setVisibility(View.GONE);
        guardarButton.setVisibility(View.GONE);


        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("onViewCreated","hola .l.");
                SelectImage();
            }
        });


        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("Button2","Presiono floatingActionButton de edición");
                EditUser();
            }
        });


        guardarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                guardarInfoPerfil();
            }
        });


        if(nomUsuario != null || passUsuario != null){
            Usuario.setText(nomUsuario);
            Contrasenia.setText(passUsuario);
        } else {
            Usuario.setText("");
            Contrasenia.setText("");
        }

        if (mSelectedImageUri != null) {
            imageV.setImageURI(mSelectedImageUri);
        }


         chart = (LineChart) getView().findViewById(R.id.chart);

         int[] numArr = {1,2,3,4,5,6};

        List<Entry> entries1 = new ArrayList<Entry>();

        for(int num : numArr){
            entries1.add(new Entry(num, num));
        }

         LineDataSet set1 = new LineDataSet(entries1, "Numbers");
        set1.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

         LineData lineData = new LineData(set1);



        chart.setData(lineData);
        chart.invalidate();

    }


    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    public void guardarInfoPerfil(){

        nomUsuario = editUsuario.getText().toString();
        passUsuario = editContrasenia.getText().toString();

        Usuario.setText(nomUsuario);
        Contrasenia.setText(passUsuario);

        guardarButton.setVisibility(View.GONE);
        fotoButton.setVisibility(View.VISIBLE);
        editUserButton.setVisibility(View.VISIBLE);
        editUsuario.setVisibility(View.GONE);
        editContrasenia.setVisibility(View.GONE);
        Usuario.setVisibility(View.VISIBLE);
        Contrasenia.setVisibility(View.VISIBLE);

       // InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
       // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);



    }



    public void EditUser(){

        fotoButton.setVisibility(View.GONE);
        editUserButton.setVisibility(View.GONE);
        guardarButton.setVisibility(View.VISIBLE);

        Usuario.setVisibility(View.GONE);
        editUsuario.setVisibility(View.VISIBLE);
        editUsuario.setText(Usuario.getText());


        Contrasenia.setVisibility(View.GONE);
        editContrasenia.setVisibility(View.VISIBLE);
        editContrasenia.setText(Contrasenia.getText());

    }



    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Image");



        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mSelectedImageUri = getImageUri(mContext,bmp);
                imageV.setImageURI(mSelectedImageUri);

            }else if(requestCode==SELECT_FILE){

                mSelectedImageUri = data.getData();
                imageV.setImageURI(mSelectedImageUri);
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
