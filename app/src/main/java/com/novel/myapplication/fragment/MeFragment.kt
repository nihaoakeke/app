package com.novel.myapplication.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.novel.myapplication.activity.BookCollsActivity
import com.novel.myapplication.activity.BookCreationActivity
import com.novel.myapplication.activity.RegisterActivity
import com.novel.myapplication.bean.UserBean
import com.novel.myapplication.dao.UserDao
import com.novel.myapplication.databinding.FragmentMeBinding
import com.novel.myapplication.utils.SharePrefUtils

class MeFragment : Fragment(){
    private var fragmentMeBinding: FragmentMeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMeBinding = inflater?.let { FragmentMeBinding.inflate(it, container, false) }
        return fragmentMeBinding!!!!.getRoot()
    }

    override fun onResume() {
        super.onResume()

        fragmentMeBinding!!.personalName.text = activity?.let { SharePrefUtils.getName(it) }
        var useName =""
        activity?.let {
            useName = SharePrefUtils.getName(it).toString()
        }
        var userDao:UserDao = UserDao()
         var user = userDao.getUser(useName)
        user?.let {
            fragmentMeBinding!!.personalNumber.text = user.introduce }

        fragmentMeBinding!!.item1.setOnClickListener {
            var intent = Intent(activity, RegisterActivity::class.java)
            intent.putExtra("user", activity?.let { it1 -> SharePrefUtils.getName(it1) })
            requireActivity().startActivity(intent)
        }
        fragmentMeBinding!!.item2.setOnClickListener {
            var intent = Intent(activity, BookCollsActivity::class.java)
            intent.putExtra("user", activity?.let { it1 -> SharePrefUtils.getName(it1) })
            requireActivity().startActivity(intent)
        }
        fragmentMeBinding!!.item3.setOnClickListener {
            var intent = Intent(activity, BookCreationActivity::class.java)
            requireActivity().startActivity(intent)
        }
        fragmentMeBinding?.let {

            fragmentMeBinding!!.personalAvatar.setOnClickListener{
                openImagePicker()
            }

        }
        }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       var contentResolver = requireActivity().contentResolver
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val absolutePath = activity?.let { imageUri?.let { it1 -> getRealPathFromURI(it, it1) } }
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            fragmentMeBinding!!.personalAvatar.setImageBitmap(bitmap)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 100
    }

    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var result: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                result = it.getString(columnIndex)
            }
            it.close()
        }
        return result
    }



}