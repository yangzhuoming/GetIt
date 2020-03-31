package com.leon.lib_base

import dagger.android.support.DaggerAppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.AnkoLogger
import pub.devrel.easypermissions.EasyPermissions

const val PERMISSION_CODE_WRITE_READ_EXTERNAL = 0x01

const val PERMISSION_CODE_HARDWARE_CAMERA = 0x02

const val PERMISSION_CODE_RECORD_AUDIO = 0x03

open class BaseActivity:
        DaggerAppCompatActivity(),
        EasyPermissions.PermissionCallbacks,
        AnkoLogger {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //TODO
    @Subscribe
    fun onEmptyEvent(event: EmptyEvent) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    protected fun requestPermissions(
        activity: BaseActivity,
        rationale: String,
        requestCode: Int,
        vararg perms: String
    ) {
        EasyPermissions.requestPermissions(activity, rationale, requestCode, *perms)
    }

    protected fun hasPermissions(vararg permissionArray: String): Boolean {
        for (permission in permissionArray) {
            if (!EasyPermissions.hasPermissions(this, permission)) {
                return false
            }
        }
        return true
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            PERMISSION_CODE_WRITE_READ_EXTERNAL -> onSDCardPermissionDenied(requestCode, perms)
            PERMISSION_CODE_HARDWARE_CAMERA -> onCameraPermissionDenied(requestCode, perms)
            PERMISSION_CODE_RECORD_AUDIO -> onRecordAudioPermissionDenied(requestCode, perms)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            PERMISSION_CODE_WRITE_READ_EXTERNAL -> onSDCardPermissionGranted(requestCode, perms)
            PERMISSION_CODE_HARDWARE_CAMERA -> onCameraPermissionGranted(requestCode, perms)
            PERMISSION_CODE_RECORD_AUDIO -> onRecordAudioPermissionGranted(requestCode, perms)
        }
    }

    open fun onSDCardPermissionGranted(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onSDCardPermissionDenied(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onCameraPermissionGranted(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onCameraPermissionDenied(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onRecordAudioPermissionGranted(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onRecordAudioPermissionDenied(requestCode: Int, perms: MutableList<String>) {

    }

}