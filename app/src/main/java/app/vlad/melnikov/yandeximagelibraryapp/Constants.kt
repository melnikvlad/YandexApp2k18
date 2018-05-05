package app.vlad.melnikov.yandeximagelibraryapp

class Constants {
    companion object {
        val PHOTOS_IN_ROW = 2
        val ROOT = "/"
        val TYPE_DIR = "dir"
        val TYPE_FILE = "file"
        val MIME_TYPE_IMAGE = "image/"

        val BASE_URL: String = "https://cloud-api.yandex.net/v1/disk/"
        val CONTENT_TYPE_HEADER = "Content-Type"
        val CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded"
        val AUTH_TYPE_HEADER = "Authorization"
        val AUTH_TYPE_VALUE = "OAuth AQAAAAAjgYLcAADLW7zC8FA_s0wvnF-pA7nlf04"

        val PHOTO_PARCELABLE = "PhotoObject"
        val BUNDLE = "Bundle"
    }
}