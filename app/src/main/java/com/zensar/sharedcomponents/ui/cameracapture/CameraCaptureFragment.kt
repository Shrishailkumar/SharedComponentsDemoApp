package com.zensar.sharedcomponents.ui.cameracapture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.cameracapturecomponent.CameraComponent
import com.android.cameracapturecomponent.interfaces.OnImageCaptureCallback
import com.android.cameracapturecomponent.util.CameraMode
import com.bumptech.glide.Glide
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentCameracaptureBinding


class CameraCaptureFragment : Fragment() {

    private var _binding: FragmentCameracaptureBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var cameraBtn: Button
    private lateinit var imageView : ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    //add your view model here
        _binding = FragmentCameracaptureBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cameraBtn = binding.startCamera
        imageView = binding.showImage
        cameraBtn.setOnClickListener(){
           /* val cameraFragment: Fragment? = CameraComponent().initCameraPreview(CameraMode.PHOTO,true, object :
                OnImageCaptureCallback {
                override fun onError(message: String) {
                   val string = message
                }

                override fun onImageCaptured(capturedImageUri: Uri) {
                    activity?.let { it1 -> Glide.with(it1).load(capturedImageUri).into(imageView) };
                }
            })
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            cameraFragment?.let { it1 ->
                fragmentTransaction.add(R.id.nav_host_fragment_content_main,
                    it1
                )
            }
            fragmentTransaction.addToBackStack(CameraCaptureFragment::class.qualifiedName)
            fragmentTransaction.commit()*/

            val intent = Intent(requireActivity(), CamaraActivity::class.java)
            startActivity(intent)



        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}