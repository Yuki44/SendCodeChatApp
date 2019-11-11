package com.houseof.code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.houseof.code.util.ImageHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PhotoDialog extends DialogFragment {

    private static final String TAG = "PhotoDialog";
    private static final int CAMERA_REQUEST_CODE = 4321;
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private ImageHandler imgHandler;
    private String picturePath;

    public static Bitmap rotateImage(Bitmap src, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo, container, false);

        imgHandler = new ImageHandler();

        /* Use the camera buttons */
        TextView takePhoto = view.findViewById(R.id.dialogTakePhoto);
        takePhoto.setOnClickListener(v -> {
            Log.d(TAG, "onClick: starting camera.");
            // TODO /* Must implement with Camera 2 API */
        });

        ImageButton takePhotoImage = view.findViewById(R.id.take_photo_bttn);
        takePhotoImage.setOnClickListener(v -> {
            Log.d(TAG, "onClick: starting camera.");
            // TODO /* Must implement with Camera 2 API */
        });

        /* Browse the gallery buttons */
        ImageButton selectPhotoImage = view.findViewById(R.id.chose_photo_bttn);
        selectPhotoImage.setOnClickListener(v -> {
            Log.d(TAG, "onClick: accessing phone memory.");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICKFILE_REQUEST_CODE);
        });


        TextView selectPhoto = view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(v -> {
            Log.d(TAG, "onClick: accessing phone memory.");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICKFILE_REQUEST_CODE);
        });

        TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(v -> {
            Log.d(TAG, "onClick: closing dialog.");
            getDialog().dismiss();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                final Uri selectedImageUri = data.getData();
                final String imagePath = imgHandler.getFilePath(getContext(), selectedImageUri);
                final InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                final Bitmap imgRotated = imgHandler.modifyOrientation(selectedImage, imagePath);
                ChatroomActivity.mSelectedImage = imgRotated;

                getDialog().dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "You haven't chosen a picture");
        }
    }
}