package com.example.appclinica.ui.registry;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appclinica.R;
import com.example.appclinica.ui.dao.CitaMemory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.appclinica.ui.helpers.Constants.CODE_MULTIPLE_IMG_GALLERY;

public class RegistryPaso3Fragment extends Fragment {

    private RegistryPaso3ViewModel mViewModel;
    private NavController navController;
    private static CitaMemory citaMemory;
    private ImageView ivPicA;
    private ImageView ivPicB;
    private ImageView ivPicC;

    public static RegistryPaso3Fragment newInstance() {
        return new RegistryPaso3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registry_paso3, container, false);

        navController = NavHostFragment.findNavController(this);

        final Bundle datosRecuperados = getArguments();
        citaMemory = RegistryPaso1Fragment.recuperarDatos(datosRecuperados);

        TextView txtPaciente1 = (TextView) root.findViewById(R.id.txtPaciente1);
        TextView txtEspecialidad1 = (TextView) root.findViewById(R.id.txtEspecialidad1);
        TextView txtMedico1 = (TextView) root.findViewById(R.id.txtMedico1);
        TextView txtSede1 = (TextView) root.findViewById(R.id.txtSede1);
        TextView txtConsultorio1 = (TextView) root.findViewById(R.id.txtConsultorio1);
        TextView txtFecha1 = (TextView) root.findViewById(R.id.txtFecha1);
        TextView txtHora1 = (TextView) root.findViewById(R.id.txtHora1);
        TextView txtTipo1 = (TextView) root.findViewById(R.id.txtTipo1);

        txtPaciente1.setText(citaMemory.getPacienteNombre());
        txtEspecialidad1.setText(citaMemory.getEspecialidadT());
        txtMedico1.setText(citaMemory.getNombreMedico());
        txtSede1.setText(citaMemory.getSedeT());
        txtConsultorio1.setText(citaMemory.getConsultorioT());
        txtFecha1.setText(citaMemory.getFecha());
        txtHora1.setText(citaMemory.getHora());
        txtTipo1.setText(citaMemory.getTipoCitaT());

        final Button button1 = root.findViewById(R.id.btnRegresar3);
        final Button button2 = root.findViewById(R.id.btnContinuar3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.bottom_registry2, datosRecuperados);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDatosMemory(view);
                Bundle datosAEnviar = new Bundle();
                RegistryPaso1Fragment.enviarDatos(datosAEnviar, citaMemory);
                navController.navigate(R.id.bottom_registry4, datosAEnviar);
            }
        });

        loadPictureEvents(root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistryPaso3ViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadPictureEvents(View root) {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }

        ivPicA = root.findViewById(R.id.RegistryPaso3Fragment_ivPicA);
        ivPicB = root.findViewById(R.id.RegistryPaso3Fragment_ivPicB);
        ivPicC = root.findViewById(R.id.RegistryPaso3Fragment_ivPicC);

        root.findViewById(R.id.RegistryPaso3Fragment_ivPicA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        root.findViewById(R.id.RegistryPaso3Fragment_ivPicB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        root.findViewById(R.id.RegistryPaso3Fragment_ivPicC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 102);
            }
        });

        root.findViewById(R.id.RegistryPaso3Fragment_btnAttachments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getText(R.string.titSelectImagesTitle)), CODE_MULTIPLE_IMG_GALLERY);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (requestCode == 100) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                ivPicA.setImageBitmap(captureImage);
                citaMemory.setImagenEvidenciaA(getByteArray(captureImage));
            } else if (requestCode == 101) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                ivPicB.setImageBitmap(captureImage);
                citaMemory.setImagenEvidenciaB(getByteArray(captureImage));
            } else if (requestCode == 102) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                ivPicC.setImageBitmap(captureImage);
                citaMemory.setImagenEvidenciaC(getByteArray(captureImage));
            } else if (requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                ClipData clipData = data.getClipData();
                Bitmap bitmapA = null;
                Bitmap bitmapB = null;
                Bitmap bitmapC = null;

                if (imageUri != null) {
                    try {
                        bitmapA = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                        ivPicA.setImageBitmap(bitmapA);
                        citaMemory.setImagenEvidenciaA(getByteArray(bitmapA));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                ivPicA.setImageURI(imageUri);
                } else if (clipData != null) {

                    if (clipData.getItemCount() >= 1) {
                        try {
                            bitmapA = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), clipData.getItemAt(0).getUri());
                            ivPicA.setImageBitmap(bitmapA);
                            citaMemory.setImagenEvidenciaA(getByteArray(bitmapA));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (clipData.getItemCount() >= 2) {
                        //ivPicB.setImageURI(clipData.getItemAt(1).getUri());
                        try {
                            bitmapB = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), clipData.getItemAt(1).getUri());
                            ivPicB.setImageBitmap(bitmapB);
                            citaMemory.setImagenEvidenciaB(getByteArray(bitmapB));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (clipData.getItemCount() >= 3) {
                        //ivPicC.setImageURI(clipData.getItemAt(2).getUri());
                        try {
                            bitmapC = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), clipData.getItemAt(2).getUri());
                            ivPicC.setImageBitmap(bitmapC);
                            citaMemory.setImagenEvidenciaC(getByteArray(bitmapC));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] getByteArray(Bitmap bitmap){
        if(bitmap == null)
            return null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void saveDatosMemory(View root) {

        //citaMemory.setImagen1("");
        //citaMemory.setImagen2("");
        //citaMemory.setImagen3("");

    }

}