package com.example.a1million_android_clone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_dialog_profile.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragmentDialog : DialogFragment() {

    private val REQUEST_GALLERY_TAKE = 2
    private val REQUEST_IMAGE_CAPTURE = 1
    private var listener: OnProfileFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_dialog_profile, container)

        v.profile_dialog_content1.setOnClickListener {
            goToAlbum()
        }

        v.profile_dialog_content2.setOnClickListener {
            setCameraPermission()
        }

        return v
    }

    private fun launchImageCrop(uri: Uri?) {
        this.activity?.let {
            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(it, this)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnProfileFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnProfileFragmentInteractionListener")
        }
    }

    interface OnProfileFragmentInteractionListener {
        fun onProfileFragmentInteraction(msg: Uri)
    }

    private fun goToAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra("crop", true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    private fun goToCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(context!!.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(this.requireContext(), "com.example.a1million_android_clone", photoFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            } catch (ex: IOException) {

            }
        }

    }

    private var cameraImagePath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        cameraImagePath = image.absolutePath
        return image
    }

    // onActivityResult ??? ????????? ??????
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_GALLERY_TAKE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
            }

            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    launchImageCrop(Uri.fromFile(File(cameraImagePath)))
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    result.uri?.let {
                        listener?.onProfileFragmentInteraction(result.uri)
                        dismiss()
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    Toast.makeText(this.context, error.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    //?????? ????????? ?????? (????????? ????????? ?????? ?????? ????????? ?????? ???????????? ?????? ??????)
    private fun setCameraPermission() {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {//????????? ?????? ????????????(????????? ?????? ???)??? ????????? ?????? ????????? ??????
                goToCamera()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {//????????? ?????? ??????????????? ????????? ?????? ????????? ??????
            }
        }

        TedPermission.with(context)
            .setPermissionListener(permission)
            .setRationaleMessage("????????? ?????? ?????????????????? ????????? ??????????????????.")
            .setDeniedMessage("????????? ?????????????????????.?????? ?????????????????? [??? ??????]-[??????] ???????????? ????????? ??????????????????.")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
            .check()
    }

}
