package app.vlad.melnikov.yandeximagelibraryapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(var path: String, var name: String, val size: Int, var loading: Boolean) : Parcelable