package cn.tech.kolaer.platform.test

import android.os.Parcel
import android.os.Parcelable

class InfoEntity : Parcelable {
    var title: String? = null
    var showNumber: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.title)
        dest.writeString(this.showNumber)
    }

    constructor()

    constructor(title: String, showNumber: String) {
        this.title = title
        this.showNumber = showNumber
    }

    private constructor(`in`: Parcel) {
        this.title = `in`.readString()
        this.showNumber = `in`.readString()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<InfoEntity> = object : Parcelable.Creator<InfoEntity> {
            override fun createFromParcel(source: Parcel): InfoEntity {
                return InfoEntity(source)
            }

            override fun newArray(size: Int): Array<InfoEntity?> {
                return arrayOfNulls(size)
            }
        }
    }
}
