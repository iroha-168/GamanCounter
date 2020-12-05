package com.iroha168.gamancounter

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*

class DummyAuthUser() : FirebaseUser(){
    constructor(parcel: Parcel) : this() {
    }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }
    override fun getUid(): String {
        TODO("Not yet implemented")
    }
    override fun getProviderId(): String {
        TODO("Not yet implemented")
    }
    override fun getDisplayName(): String? {
        return  "A"
    }
    override fun getPhotoUrl(): Uri? {
        TODO("Not yet implemented")
    }
    override fun getEmail(): String? {
        TODO("Not yet implemented")
    }
    override fun getPhoneNumber(): String? {
        TODO("Not yet implemented")
    }
    override fun isEmailVerified(): Boolean {
        TODO("Not yet implemented")
    }
    override fun isAnonymous(): Boolean {
        TODO("Not yet implemented")
    }
    override fun zza(): MutableList<String> {
        TODO("Not yet implemented")
    }
    override fun zza(p0: MutableList<out com.google.firebase.auth.UserInfo>): FirebaseUser {
        TODO("Not yet implemented")
    }
    override fun zza(p0: com.google.android.gms.internal.`firebase-auth-api`.zzni) {
        TODO("Not yet implemented")
    }
    override fun getProviderData(): MutableList<out com.google.firebase.auth.UserInfo> {
        TODO("Not yet implemented")
    }
    override fun zzb(): FirebaseUser {
        TODO("Not yet implemented")
    }
    override fun zzb(p0: MutableList<MultiFactorInfo>?) {
        TODO("Not yet implemented")
    }
    override fun zzc(): FirebaseApp {
        TODO("Not yet implemented")
    }
    override fun getTenantId(): String? {
        TODO("Not yet implemented")
    }
    override fun zzd(): com.google.android.gms.internal.`firebase-auth-api`.zzni {
        TODO("Not yet implemented")
    }
    override fun zze(): String {
        TODO("Not yet implemented")
    }
    override fun zzf(): String {
        TODO("Not yet implemented")
    }
    override fun getMetadata(): FirebaseUserMetadata? {
        TODO("Not yet implemented")
    }
    override fun getMultiFactor(): MultiFactor {
        TODO("Not yet implemented")
    }
    companion object CREATOR : Parcelable.Creator<DummyAuthUser> {
        override fun createFromParcel(parcel: Parcel): DummyAuthUser {
            return DummyAuthUser(parcel)
        }
        override fun newArray(size: Int): Array<DummyAuthUser?> {
            return arrayOfNulls(size)
        }
    }
}