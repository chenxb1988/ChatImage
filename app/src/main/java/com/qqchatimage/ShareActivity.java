package com.qqchatimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenxb on 2017/10/19.
 */

public class ShareActivity extends Activity {
    FrameLayout frameQuan, frameZone;
    ImageView ivQuan, ivZone;
    TextView tvQuanTag, tvQuanSysTime, tvQuanSendTime, tvZoneSysTime, tvZoneSendTime, tvZone2Tag;
    EditText etTag;
    RadioButton rbZone1, rbZone2, rbQuan1, rbQuan2;
    RadioGroup rbGroup;
    String rbStr;

    File folder;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        frameQuan = (FrameLayout) findViewById(R.id.fl_quan);
        frameZone = (FrameLayout) findViewById(R.id.fl_zone);
        ivQuan = (ImageView) findViewById(R.id.iv_quan);
        ivZone = (ImageView) findViewById(R.id.iv_zone);
        tvQuanTag = (TextView) findViewById(R.id.tv_quan_tag);
        tvQuanSysTime = (TextView) findViewById(R.id.tv_quan_sys_time);
        tvQuanSendTime = (TextView) findViewById(R.id.tv_quan_send_time);
        tvZoneSysTime = (TextView) findViewById(R.id.tv_zone_sys_time);
        tvZoneSendTime = (TextView) findViewById(R.id.tv_zone_send_time);
        tvZone2Tag = (TextView) findViewById(R.id.tv_zone2_tag);
        etTag = (EditText) findViewById(R.id.et_tag);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        rbZone1 = (RadioButton) findViewById(R.id.rb_zone1);
        rbZone2 = (RadioButton) findViewById(R.id.rb_zone2);
        rbQuan1 = (RadioButton) findViewById(R.id.rb_quan1);
        rbQuan2 = (RadioButton) findViewById(R.id.rb_quan2);


        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zone1:
                        rbStr = "空间1";
                        frameQuan.setVisibility(View.GONE);
                        frameZone.setVisibility(View.VISIBLE);
                        tvZone2Tag.setVisibility(View.GONE);
                        ivZone.setImageResource(R.drawable.zone1);
                        tvZoneSysTime.setText(timeFormat.format(new Date(System.currentTimeMillis())));
                        tvZoneSendTime.setText("今天" + timeFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 137)));
                        break;
                    case R.id.rb_zone2:
                        rbStr = "空间2";
                        frameQuan.setVisibility(View.GONE);
                        frameZone.setVisibility(View.VISIBLE);
                        tvZone2Tag.setVisibility(View.VISIBLE);
                        ivZone.setImageResource(R.drawable.zone2);
                        tvZoneSysTime.setText(timeFormat.format(new Date(System.currentTimeMillis())));
                        tvZoneSendTime.setText("今天" + timeFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 126)));
                        tvZone2Tag.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                        break;
                    case R.id.rb_quan1:
                        rbStr = "朋友圈1";
                        setCommonQuan();
                        ivQuan.setImageResource(R.drawable.quan1);
                        tvQuanSendTime.setText(timeFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 135)));
                        break;
                    case R.id.rb_quan2:
                        rbStr = "朋友圈2";
                        setCommonQuan();
                        ivQuan.setImageResource(R.drawable.quan2);
                        tvQuanSendTime.setText(timeFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 124)));
                        break;
                    default:
                        break;
                }
            }
        });

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbZone1.isChecked() || rbZone2.isChecked()) {
                    getImage(frameZone);
                } else if (rbQuan1.isChecked() || rbQuan2.isChecked()) {
                    getImage(frameQuan);
                }
            }
        });
    }

    private void setCommonQuan(){
        frameQuan.setVisibility(View.VISIBLE);
        frameZone.setVisibility(View.GONE);
        String str = etTag.getText() + "\n" + getString(R.string.content_share);
        SpannableString content = new SpannableString(str);
        int start = str.indexOf("49521");
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)),
                start, start + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)),
                str.indexOf("http"), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvQuanTag.setText(content);
        tvQuanSysTime.setText(timeFormat.format(new Date(System.currentTimeMillis())));
    }

    private void getImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        try {
            saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
            Toast.makeText(this, "保存成功" + rbStr, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveBitmap(Bitmap bm, String name) throws IOException {
        File f = new File(folder, name);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }
}
