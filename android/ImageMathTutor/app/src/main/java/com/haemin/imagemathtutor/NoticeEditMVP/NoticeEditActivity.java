package com.haemin.imagemathtutor.NoticeEditMVP;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.ServerFile;
import com.haemin.imagemathtutor.R;
import com.haemin.imagemathtutor.Utils.ConfirmStarter;
import com.haemin.imagemathtutor.View.UI.FileButton;

import java.util.ArrayList;

public class NoticeEditActivity extends AppCompatActivity implements NoticeEditContract.NoticeEditView {


    NoticeEditContract.NoticeEditPresenter noticeEditPresenter;
    String lectureSeq;
    String lectureName;

    private final int READ_REQUEST_CODE = 123;

    @BindView(R.id.edit_notice_title)
    EditText editNoticeTitle;
    @BindView(R.id.edit_notice_text)
    EditText editNoticeText;
    @BindView(R.id.text_lecture_name)
    TextView textLectureName;
    @BindView(R.id.group_file)
    LinearLayout holderFileGroup;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.btn_add_file)
    ImageButton btnAddFile;
    @BindView(R.id.btn_notice_upload)
    Button btnUploadNotice;

    public static void start(Context context, String lectureSeq, String lectureName) {
        Intent starter = new Intent(context, NoticeEditActivity.class);
        starter.putExtra("lectureSeq", lectureSeq);
        starter.putExtra("lectureName", lectureName);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);
        Intent fromOutside = getIntent();
        ConfirmStarter.checkIntent(this, fromOutside);
        ButterKnife.bind(this);

        noticeEditPresenter = new NoticeEditPresenter(this);
        lectureSeq = fromOutside.getStringExtra("lectureSeq");
        lectureName = fromOutside.getStringExtra("lectureName");

        initView();
    }

    private void initView() {
        textLectureName.setText(lectureName);
        btnBack.setOnClickListener(v -> finish());
        btnAddFile.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(i, READ_REQUEST_CODE);
        });
        btnUploadNotice.setOnClickListener(v -> {
            noticeEditPresenter.uploadNotice(lectureSeq, editNoticeTitle.getText().toString(), editNoticeText.getText().toString());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("NOTICE EDIT ACTIVITY", "Uri: " + uri.toString());

                noticeEditPresenter.addFile(getPath(this,uri)+"", ServerFile.FILE_TYPE_NORMAL);
            }
        }
    }
    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    @Override
    public void refreshFileList(ArrayList<ServerFile> files) {
        holderFileGroup.removeAllViews();
        for (ServerFile file : files) {

            FileButton button = new FileButton(this);
            button.setFile(file);
            button.setOnDeleteClickListener((fileButton, file1) ->{
                noticeEditPresenter.deleteFile(file1.getFileSeq());
            });
            holderFileGroup.addView(button);
        }
    }


    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String errorMsg) {

        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();

    }
}
