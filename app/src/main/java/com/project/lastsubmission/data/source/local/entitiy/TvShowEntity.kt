package com.project.lastsubmission.data.source.local.entitiy

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_tvshow")
@Parcelize
data class TvShowEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @NonNull
    @ColumnInfo(name = "tvshow_id")
    var tvShowId: Int = 0,

    @ColumnInfo(name = "tvshow_title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var desc: String? = null,

    @ColumnInfo(name = "tvshow_poster")
    var poster: String? = null,

    @ColumnInfo(name = "tvshow_preview_image")
    var imgPreview: String? = null,

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
): Parcelable
