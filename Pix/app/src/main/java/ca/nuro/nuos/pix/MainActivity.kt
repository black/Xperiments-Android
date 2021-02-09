package ca.nuro.nuos.pix

import android.Manifest
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import ca.nuro.nuos.musicplayer_kotlin.extensions.OnRVItemClickListener
import ca.nuro.nuos.pix.databinding.ActivityMainBinding
import ca.nuro.nuos.pix.extensions.Folder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val folderList = arrayListOf<Folder>()
    private var folderAdapter: FolderAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        validatePermission(permissions)

        folderAdapter = FolderAdapter(this,folderList)
        binding.folderView.adapter = folderAdapter

        folderAdapter?.setOnItemClickListener(object : OnRVItemClickListener {
            override fun OnItemClick(pos: Int) {
                Log.d("FOLDER_NAME",folderList[pos].title)
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun imgInit(){

    }

    private fun validatePermission(permissions: List<String>) {
        Dexter.withContext(this)
                .withPermissions(permissions)
                .withListener(
                        object : MultiplePermissionsListener {
                            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                            }

                            override fun onPermissionRationaleShouldBeShown(
                                    p0: MutableList<PermissionRequest>?,
                                    p1: PermissionToken?
                            ) {
                                AlertDialog.Builder(this@MainActivity)
                                        .setTitle(R.string.storage_permission_rationale_title)
                                        .setMessage(R.string.storage_permission_rationale_message)
                                        .setNegativeButton(
                                                android.R.string.cancel,
                                                DialogInterface.OnClickListener { dialogInterface, i ->
                                                    dialogInterface.dismiss()
                                                    p1?.cancelPermissionRequest()
                                                })
                                        .setPositiveButton(
                                                android.R.string.ok,
                                                DialogInterface.OnClickListener { dialogInterface, _ ->
                                                    dialogInterface.dismiss()
                                                    p1?.continuePermissionRequest()
                                                })
                                        .show()
                            }
                        }
                ).check()
    }
}