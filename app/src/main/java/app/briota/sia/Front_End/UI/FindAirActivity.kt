package app.briota.sia.Front_End.UI

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.briota.sia.Front_End.view.adapter.CustomAdapter
import app.briota.sia.R
import eu.findair.findairble.entities.FindAirDevice
import eu.findair.findairble.entities.PhoneState
import eu.findair.findairble.entities.Usage
import eu.findair.findairble.managers.FindAirSDK
import eu.findair.findairble.managers.PermissionManager
import eu.findair.findairble.utils.DeviceState
import eu.findair.findairble.utils.FAStatics
import eu.findair.findairble.utils.FindAirSDKBuilder
import eu.findair.findairble.utils.Utils
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class FindAirActivity : AppCompatActivity() {

    lateinit var initializeBTN : Button
    lateinit var connectBTN: Button
    lateinit var readHistoryBTN : Button
    lateinit var termsBTN : Button

    lateinit var logTV: TextView
    lateinit var findAirSDK: FindAirSDK
    var logString = ""
    lateinit var devicesRV: RecyclerView

    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("dd-M hh:mm:ss")
    lateinit var phoneState: PhoneState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_air2)


        txtTitle.setText("Find Air")

        back_tol.setOnClickListener {
            onBackPressed()
        }
        //Setup UI
        initializeBTN = findViewById(R.id.initializeBTN)

        connectBTN = findViewById(R.id.connectBTN)
        readHistoryBTN = findViewById(R.id.readHistoryBTN)
        logTV = findViewById(R.id.log)
        termsBTN = findViewById(R.id.termsBTN)
        logTV.setMovementMethod(ScrollingMovementMethod())
        devicesRV = findViewById(R.id.recycler)
        devicesRV.setLayoutManager(LinearLayoutManager(this))

        //Create FindAirSDK instance using FindAirSDKBuilder,

        //Create FindAirSDK instance using FindAirSDKBuilder,
        findAirSDK = FindAirSDKBuilder(this)
            .setUsagesListener { usage -> onNewUsageAdded(usage) }
            .setDevListener { findAirDeviceList ->
                onDevicesStateChanges(
                    findAirDeviceList
                )
            }.setPhoneStateListener { phoneState -> onPhoneStateChanged(phoneState) }.build()

        phoneState = findAirSDK.init()

        if (phoneState.getLocationPermission() != FAStatics.PERMISSION_STATUS_GRANTED) {
            val manager = PermissionManager()
            if (phoneState.getLocationPermission() == FAStatics.PERMISSION_ONLY_WHILE_USING_APP) {
                manager.requestBackgroundLocation(this, 0)
            }
            manager.requestLocationPermission(this, 0)
            phoneState = findAirSDK.checkPhoneState()
            if (phoneState.getLocationPermission() == FAStatics.PERMISSION_ONLY_WHILE_USING_APP) {
                //Request for location in background
                manager.requestLocationPermission(this, 0)
            }
        }
        initializeBTN.setOnClickListener { v: View? -> findAirSDK.init() }

        termsBTN.setOnClickListener { v: View? ->
            findAirSDK.getAllResearchTerms { list ->
                Log.d(
                    "Findair",
                    list.toString()
                )
            }
        }

        readHistoryBTN.setOnClickListener { v: View? ->
            findAirSDK.getUsagesHistory { list ->
                for (usage in list) {
                    Log.d(
                        "Findair",
                        simpleDateFormat.format(Date(usage.date))
                    )
                }
            }
        }

        ActivityCompat.requestPermissions(
            this, arrayOf(permission.BLUETOOTH_CONNECT, permission.BLUETOOTH_SCAN),
            0
        )

        connectBTN.setOnClickListener { v: View? ->
            if (FindAirSDK.isInitialized()) {
                findAirSDK.scanAndConnectToNearbyDevice()
                findAirSDK.scanAndConnectWithCallback { findAirDeviceList ->
                    for (device in findAirDeviceList) {
                        if (device.state == DeviceState.CONNECTING) {
                            findAirSDK.stopScan()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "initialize first!", Toast.LENGTH_LONG).show()
            }
        }
        Utils.checkLocationAvailability(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                val permissionManager = PermissionManager()
                permissionManager.requestBackgroundLocation(this, 0)
            }
        }
    }

    //Example implementation for listener for new incoming usages
    @SuppressLint("DefaultLocale")
    private fun onNewUsageAdded(puff: Usage) {
        logString = """${simpleDateFormat.format(Date())}    usage from:${
            simpleDateFormat.format(
                Date(puff.date)
            )
        }
$logString"""
        Handler(mainLooper).post { logTV!!.text = logString }
        Log.d("findairone", "new usage")
        Handler(mainLooper).post {
            Toast.makeText(
                this,
                "NEW USAGE",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Example implementation for listener for phone state changes
    private fun onPhoneStateChanged(phoneState: PhoneState) {
        phoneState.isBluetoothOn
        phoneState.getLocationPermission()
        phoneState.isLocationOn
        Log.d(
            "findairone",
            "bluetooth " + phoneState.isBluetoothOn + "location permission: " + phoneState.getLocationPermission()
                    + "location on " + phoneState.isLocationOn
        )
    }

    fun onDevicesStateChanges(findAirDeviceList : List<FindAirDevice>) {

        Log.d("findairone", "device state changed")
        runOnUiThread {

            fun run() {
                devicesRV!!.adapter = CustomAdapter(findAirDeviceList,
                    View.OnClickListener { view -> findAirSDK.disconnect((view.tag as FindAirDevice).mac) })
            }
        }


        var notificationBuilder = NotificationCompat.Builder(
            baseContext
        )
            .setOngoing(true)
            .setChannelId("findair")
            .setSmallIcon(R.drawable.ic_launcher_foreground_2)
            .setPriority(Notification.PRIORITY_MIN)

        var content = ""

        for (device: FindAirDevice? in findAirDeviceList) {
            var newContent = "" + device!!.getMac() + ": "
            newContent += if (device.getState() == DeviceState.CONNECTED) {
                "CONNECTED\n"
            } else if (device.getState() == DeviceState.CONNECTING) {
                "CONNECTING\n"
            } else if (device.getState() == DeviceState.FORBIDDEN) {
                "FORBIDDEN\n"
            } else if (device.getState() == DeviceState.SYNCED) {
                findAirSDK.getUsagesHistory { usages ->
                    Log.d(
                        "findairone",
                        " number of usages in db: " + usages.size
                    )
                }
                "SYNCED\n"
            } else {
                "DISCONNECTED\n"
            }
            content += newContent
            if (findAirDeviceList.indexOf(device) == 0) newContent += "\n"
            logString = simpleDateFormat.format(Date()) + "    " + newContent + logString
        }

        Handler(mainLooper).post { logTV!!.text = logString }

        notificationBuilder.setContentText(content)
        val notification = notificationBuilder.build()
        val mNotifyMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(findAirSDK.serviceNotificationId, notification)
    }

}