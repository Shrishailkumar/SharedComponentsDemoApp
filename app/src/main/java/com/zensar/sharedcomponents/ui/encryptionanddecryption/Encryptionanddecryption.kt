package com.zensar.sharedcomponents.ui.encryptionanddecryption

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.model.EncryptionData
import com.android.appcomponents.viewmodel.EncryptionViewModal
import com.zensar.sharedcomponents.databinding.FragmentEncryptionanddecryptionBinding
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class Encryptionanddecryption : Fragment() {

    private var _binding: FragmentEncryptionanddecryptionBinding? = null

    private lateinit var mEncryptedText : TextView
    private lateinit var mInputText : EditText
    private lateinit var mEncryptBtn: Button
    private lateinit var mDecryptBnt: Button

    private lateinit var encryptionViewModal:  EncryptionViewModal
    private val binding get() = _binding!!
    var secreteKey: SecretKey ?= null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentEncryptionanddecryptionBinding.inflate(inflater,container,false)
        val root: View = binding.root

        mEncryptedText = binding.encryptedData
        mInputText = binding.encryptInput
        mEncryptBtn = binding.encryptBtn
        mDecryptBnt = binding.decryptBtn
        encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)

        var key: SecretKey = generateSecreteKey()

        mEncryptBtn.setOnClickListener(){
            activity?.let {
                val input: String = mInputText.text.toString()
                context?.let { it1 ->
                    encryptionViewModal.toBase64Encode(input)
                        .observe(it, Observer<EncryptionData?> {
                            updateUI(it)
                        })
                }
            }
        }
        mDecryptBnt.setOnClickListener(){
            activity?.let {
                val input: String = mEncryptedText.text.toString()
                context?.let { it1 ->
                    encryptionViewModal.toBase64Dncode(input)
                        ?.observe(it, Observer<EncryptionData?> {
                            updateUI(it)
                        })
                }
            }

        }

        return root
    }
    private fun updateUI(encryptionData: EncryptionData) {
        mEncryptedText.setText( encryptionData.encryptAndDecryptData)
    }

    private fun generateSecreteKey() : SecretKey {
        val keyGen: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }

}