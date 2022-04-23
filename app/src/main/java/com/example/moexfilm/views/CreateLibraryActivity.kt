package com.example.moexfilm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityCreateLibraryBinding
import com.example.moexfilm.application.Application.Access.CLIENT_ID
import com.example.moexfilm.models.data.SubGDriveItem
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.ScanItem
import com.example.moexfilm.viewModels.CreateLibraryViewModel
import com.example.moexfilm.views.fileExplorer.FileExplorerActivity
import com.example.moexfilm.views.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

class CreateLibraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateLibraryBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val createLibraryViewModel: CreateLibraryViewModel by viewModels()
    private var folderSelected: GDriveItem? = null
    private var subFolderSelected: SubGDriveItem? = null
    private lateinit var name: String
    private lateinit var type: String
    private lateinit var language: String
    private lateinit var accountId: String

    private var responseGoogleSignIn =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { response ->
            if (response.resultCode == RESULT_OK) {
                val data = response.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        accountId = account.id!!
                        selectFolder(accountId, account.serverAuthCode!!, account.idToken!!)
                    }
                } catch (e: ApiException) {
                }
            }
            if (response.resultCode == RESULT_CANCELED)
                Toast.makeText(this, getString(R.string.noDriveScope_error), Toast.LENGTH_LONG)
                    .show()
        }

    private var responseFolderSelected =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { response ->
            if (response.resultCode == RESULT_OK) {
                val data: Bundle = response.data!!.extras!!
                folderSelected = data.getSerializable("SELECTEDFOLDER") as GDriveItem
                subFolderSelected = data.getSerializable("SUBFOLDERS") as SubGDriveItem
                binding.tvRouteFolderSelected.text = data.getString("ROUTE")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinners()
        initLibraryCreatedObserver()
        setListener()
    }

    private fun initLibraryCreatedObserver() {
        createLibraryViewModel.libraryCreatedLiveData.observe(this) { library ->
            if (library != null) {
                returnScanItem(library)
            } else Log.d("ERROR", "ERROR")
        }
    }

    private fun returnScanItem(library: Library) {
        val scanItem = ScanItem(library.id,library.name,type, language, subFolderSelected?.children)
        val resultIntent = Intent().apply {
            putExtra("SCANITEM", scanItem)
        }
        setResult(RESULT_OK,resultIntent)
        finish()
    }

    private fun initSpinners() {
        val types = listOf(getString(R.string.movies_text), getString(R.string.tvShows_text))
        val languages = listOf("ES")
        val adapterTypes = ArrayAdapter(this, R.layout.list_item, types)
        val adapterLanguages = ArrayAdapter(this, R.layout.list_item, languages)
        (binding.spinnerTypeLibrary.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapterTypes)
            setText(adapter.getItem(0).toString(), false)
        }
        (binding.spinnerLanguages.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapterLanguages)
            setText(adapter.getItem(0).toString(), false)
        }
    }

    private fun setListener() {
        binding.btnSelectFolder.setOnClickListener { signinGoogle() }
        binding.btnFinish.setOnClickListener {
            if (checkData())
                createLibraryViewModel.createLibrary(
                    accountId, folderSelected!!.id, name,
                    emptyList(), type, language
                )
        }
    }

    private fun checkData(): Boolean {
        var valid: Boolean = true
        name = binding.tfName.text.toString()
        type = binding.tvTypeLibrary.text.toString()
        language = binding.tvLanguages.text.toString()

        binding.textInName.error = null
        if (name.isEmpty()) {
            binding.textInName.error = getString(R.string.fieldEmpty_error)
            valid = false
        }

        if (folderSelected == null) {
            Toast.makeText(this, getString(R.string.noFolderSelected_error), Toast.LENGTH_LONG)
                .show()
            valid = false
        }
        return valid
    }


    private fun signinGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestScopes(Scope(Scopes.DRIVE_FULL))
            .requestServerAuthCode(CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        responseGoogleSignIn.launch(googleSignInClient.signInIntent)
        googleSignInClient.signOut()
    }

    private fun selectFolder(accountId: String, authCode: String, idToken: String) {
        val i = Intent(this, FileExplorerActivity::class.java).apply {
            putExtra("AUTHCODE", authCode)
            putExtra("ACCOUNTID", accountId)
            putExtra("IDTOKEN", idToken)
        }
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        responseFolderSelected.launch(i)
    }

}