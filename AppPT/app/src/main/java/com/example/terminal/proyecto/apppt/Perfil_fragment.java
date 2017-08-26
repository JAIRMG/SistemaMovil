package com.example.terminal.proyecto.apppt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import static com.example.terminal.proyecto.apppt.R.layout.perfil;


/**
 * Created by Jair Moreno on 05/08/2017.
 */

public class Perfil_fragment extends Fragment {

    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Context mContext;
    ImageView imageV;
    EditText editUsuario;
    private Uri mSelectedImageUri;
    TextView Usuario, Usuario2, UsuarioEtiqueta1;

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
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("onViewCreated","hola .l.");
                SelectImage();
            }
        });

        FloatingActionButton editUserButton = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton2);
        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("Button2","Presiono floatingActionButton de edición");
                EditUser();
            }
        });

        imageV = (ImageView) getView().findViewById(R.id.imageView);  //Imagen de perfil
        editUsuario = (EditText) getView().findViewById(R.id.editUsuario); //textEdit para que el usuario cambie su nombre de usuario
        Usuario = (TextView) getView().findViewById(R.id.usuario);
        Usuario2 = (TextView) getView().findViewById(R.id.usuarioEtiqueta2);
        UsuarioEtiqueta1 = (TextView) getView().findViewById(R.id.usuarioEtiqueta);

        editUsuario.setVisibility(View.GONE);
        Usuario2.setVisibility(View.GONE);

        if (mSelectedImageUri != null) {
            imageV.setImageURI(mSelectedImageUri);
        }

    }


    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    public void EditUser(){

        Usuario.setVisibility(View.GONE);
        UsuarioEtiqueta1.setVisibility(View.GONE);
        Usuario2.setVisibility(View.VISIBLE);
        editUsuario.setVisibility(View.VISIBLE);
        editUsuario.setText(Usuario.getText());

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
