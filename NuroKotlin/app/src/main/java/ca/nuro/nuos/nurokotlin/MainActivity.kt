package ca.nuro.nuos.nurokotlin

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.os.*
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ca.nuro.nuos.nurokotlin.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import devin.com.linkmanager.LinkManager
import devin.com.linkmanager.bean.Angle
import devin.com.linkmanager.bean.DataType
import devin.com.linkmanager.bean.Power

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val  signalViewModel:SignalViewModel by viewModels()
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Permissions*/
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        validatePermission(permissions)

        try {
            LinkManager.getInstance().init(application)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        observers()
    }

    private fun observers(){
        signalViewModel.getConnection().observe(this, { msg ->
            binding.connectionStatus.text = msg
            binding.connectProgress.visibility = if (msg == "connected") {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
        signalViewModel.getFocus().observe(this, { msg ->
            binding.focusProgress.progress = msg
        })
        signalViewModel.getCalm().observe(this, { msg ->
            binding.calmProgress.progress = msg
        })
        signalViewModel.getRaw().observe(this, { msg ->
            binding.rawProgress.progress = msg
        })
        signalViewModel.getOther().observe(this, { msg ->
            binding.appreciationProgress.progress = msg[0]
            binding.powerProgress.progress = msg[1]
            binding.versionView.text = "Version: " + msg[2]
        })
        signalViewModel.getMotion().observe(this, { msg ->
            binding.motionXProgress.progress = msg[0]
            binding.motionYProgress.progress = msg[1]
            binding.motionZProgress.progress = msg[2]
        })
    }

    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                DataType.CODE_UP_SEND_START -> {

                }
                DataType.CODE_UP_SEND_ING -> {
                }
                DataType.CODE_UP_SEND_END -> {
                }
                DataType.CODE_UP_SUCCEED -> {
                }
                DataType.CODE_CONNECT_SUCCEED -> signalViewModel.setConnection("Connected")
                DataType.CODE_CONNECT_FAIL -> signalViewModel.setConnection("Failed")
                DataType.CODE_POOR_SIGNAL -> signalViewModel.setSignalStrength(msg.arg1)
                DataType.CODE_ATTENTION -> signalViewModel.setFocus(msg.arg1)
                DataType.CODE_MEDITATION -> signalViewModel.setCalm(msg.arg1)
                DataType.CODE_RAW -> signalViewModel.setRaw(msg.arg1)
                DataType.CODE_EEGPOWER -> {
                    val power = msg.obj as Power
                    val bands = IntArray(8)
                    val other = IntArray(3)
                    bands[0] = power.delta
                    bands[1] = power.theta
                    bands[2] = power.lowAlpha
                    bands[3] = power.highAlpha
                    bands[4] = power.lowBeta
                    bands[5] = power.highBeta
                    bands[6] = power.lowGamma
                    bands[7] = power.middleGamma
                    other[0] = power.fancyDegree //喜好度
                    other[1] = power.electric //电量值
                    other[2] = power.version //版本号
                    signalViewModel.setBands(bands)
                    signalViewModel.setOther(other)
                }
                DataType.CODE_ANGLE -> {
                    Int
                    val angle = msg.obj as Angle
                    val motion = IntArray(3)
                    motion[0] = angle.yaw //偏航角度值
                    motion[1] = angle.bow //俯仰角度值
                    motion[2] = angle.across //横滚角度值
                    signalViewModel.setMotion(motion)
                }
            }
        }
    }

    fun connectSensor(view: View?) {
        LinkManager.getInstance().setConnectiDeviceFirst(handler)
        binding.connectProgress.visibility = View.VISIBLE
    }

    fun disConnectSensor(view: View?) {
        LinkManager.getInstance().close()
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