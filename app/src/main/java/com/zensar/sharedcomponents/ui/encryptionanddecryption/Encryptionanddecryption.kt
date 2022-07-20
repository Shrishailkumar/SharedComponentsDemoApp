package com.zensar.sharedcomponents.ui.encryptionanddecryption

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.appcomponents.model.EncryptionData
import com.android.appcomponents.viewmodel.EncryptionViewModal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zensar.sharedcomponents.R
import com.zensar.sharedcomponents.databinding.FragmentEncryptionanddecryptionBinding
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class Encryptionanddecryption : Fragment() {

    private var _binding: FragmentEncryptionanddecryptionBinding? = null

    private lateinit var mEncryptedText: TextView
    private lateinit var mInputText: EditText
    private lateinit var mEncryptBtn: Button
    private lateinit var mDecryptBnt: Button
    private lateinit var mAlgorithmSpinner: Spinner
    private lateinit var sslCerificateBtn: Button

    private var selectedPosition: Int = 0
    lateinit var rsaKey: KeyPair


    private lateinit var encryptionViewModal: EncryptionViewModal
    private val binding get() = _binding!!
    var secreteKey: SecretKey = generateSecreteKey(256)
    var IV: ByteArray = generateIv()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEncryptionanddecryptionBinding.inflate(inflater, container, false)
        activity?.run {
            findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE
        }
        val root: View = binding.root

        mEncryptedText = binding.encryptedData
        mInputText = binding.encryptInput
        mEncryptBtn = binding.encryptBtn
        mDecryptBnt = binding.decryptBtn
        encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)
        mAlgorithmSpinner = binding.algorithmSpinner
        sslCerificateBtn = binding.sslCertificate
        generateRSASecreteKey()
        var algorithmList: Array<String> =
            requireActivity().resources.getStringArray(R.array.algorithm_list)

        if (mAlgorithmSpinner != null) {
            val adapter =
                ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, algorithmList)
            mAlgorithmSpinner.adapter = adapter

            mAlgorithmSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (selectedPosition != position) {
                        selectedPosition = position
                        mInputText.setText("")
                        mEncryptedText.text = ""
                    }
                    when (selectedPosition) {
                        1 -> mDecryptBnt.visibility = View.GONE

                        2 -> mDecryptBnt.visibility = View.VISIBLE
                        3 -> mDecryptBnt.visibility = View.GONE
                        4 -> mDecryptBnt.visibility = View.VISIBLE
                        5 -> mDecryptBnt.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        mEncryptBtn.setOnClickListener {
            processEncryptAlgorithm()
        }
        mDecryptBnt.setOnClickListener {
            processDecryptAlgorithm()

        }
        sslCerificateBtn.setOnClickListener() {
            val intent = Intent(requireActivity(), SSCertificateActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processEncryptAlgorithm() {
        when (selectedPosition) {

            1 -> {
                SHA256Algorithm()
            }
            2 -> {
                base64EncryptionAlgorithm()
            }
            3 -> {
                md5Digest()
            }
            4 -> {
                aesEncryptAlgorithm()
            }
            5 -> {
                rsaEncryptAlgorithm()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processDecryptAlgorithm() {
        when (selectedPosition) {

            2 -> base64DecryptionAlgorithm()
            4 -> aesDecryptAlgorithm()
            5 -> rsaDecryptAlgorithm()


        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun base64EncryptionAlgorithm() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun base64DecryptionAlgorithm() {
        activity?.let {
            val input: String = mEncryptedText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.toBase64Decode(input)
                    .observe(it, Observer<EncryptionData?> {
                        updateUI(it)
                    })
            }
        }
    }

    private fun aesEncryptAlgorithm() {
        activity?.let {
            val input: String = mInputText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.aesEncryptAlgorithm("123456", input)
            }
        }
        encryptionViewModal.encryptedResponse.observe(viewLifecycleOwner, {
            updateUI(EncryptionData(it))
            println("encrpted Data: $it")
        })


    }

    private fun rsaEncryptAlgorithm() {
        activity?.let {
            val input: String = mInputText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.rsaEncryptAlgorithm(input, rsaKey.public)
            }
        }
        encryptionViewModal.rsaEncryptResponse.observe(viewLifecycleOwner, {
            updateUI(EncryptionData(it))
            println("encrpted Data: $it")
        })


    }

    private fun rsaDecryptAlgorithm() {
        activity?.let {
            val input: String = mEncryptedText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.rsaDecryptAlgorithm(input, rsaKey.private)
            }
        }
        encryptionViewModal.rsaDecryptResponse.observe(viewLifecycleOwner, {
            updateUI(EncryptionData(it))
            println("encrpted Data: $it")
        })


    }

    private fun SHA256Algorithm() {

        activity?.let {
            val input: String = mEncryptedText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.hMacSha256Algoritham(secreteKey.toString(), input)
                    .observe(it, Observer<EncryptionData?> {
                        updateUI(it)
                    })
            }
        }

    }

    private fun md5Digest() {

        activity?.let {
            val input: String = mEncryptedText.text.toString()
            context?.let { it1 ->
                encryptionViewModal.md5Digest(input)
                    .observe(it, Observer<EncryptionData?> {
                        updateUI(it)
                    })
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun aesDecryptAlgorithm() {
        val input: String = mEncryptedText.text.toString()
        context?.let { it1 ->
            encryptionViewModal.aesDecryptionAlgorithm("123456", input)

        }
        encryptionViewModal.decryptedResponse.observe(viewLifecycleOwner,
            { updateUI(EncryptionData(it)) })
    }


    private fun updateUI(encryptionData: EncryptionData) {
        mEncryptedText.text = encryptionData.encryptAndDecryptData
    }

    private fun generateSecreteKey(keySize: Int): SecretKey {
        val keyGen: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGen.init(keySize)
        return keyGen.generateKey()
    }

    fun generateIv(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }

    private fun generateRSASecreteKey() {
        val kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        rsaKey = kpg.genKeyPair();
    }

}