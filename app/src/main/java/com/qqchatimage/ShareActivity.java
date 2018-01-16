package com.qqchatimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    FrameLayout frameQuan, frameZone, framePraise, frameConvert;
    LinearLayout linearQuan, linearZone;
    ImageView ivQuan, ivZone;
    TextView tvQuanTag, tvQuanSysTime, tvQuanSendTime;
    TextView tvZoneSysTime, tvZoneSendTime, tvZone2Tag;
    TextView tvPraiseSysTime, tvPraiseSendTime;
    TextView tvConvertList, tvConvertSysTime;
    ImageView ivConvert, ivPraise1, ivPraise2, ivPraise3;
    EditText etTag, etClock;
    RadioButton rbZone1, rbZone2, rbQuan1, rbQuan2;
    RadioGroup rbGroup;
    String rbStr;
    String roleTag;

    File folder;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    int[] praiseIds = {R.drawable.praise0, R.drawable.praise1, R.drawable.praise2, R.drawable.praise3,
            R.drawable.praise4, R.drawable.praise5, R.drawable.praise6, R.drawable.praise7,
            R.drawable.praise8, R.drawable.praise9, R.drawable.praise10, R.drawable.praise11};

    String[] converts = {"シ俊", "虚.夜月", "不屈", "小豆豆", "Kevin晏", "皮卡丘",
            "Y_Lam", "明", "向左’永垂不朽的偏爱", "胡炳昆", "回亿是曾经", "控制つ爱你", "她说", "一念永恒", "朝槿夕凉", "梧桐相待", "A0-隔壁老王",
            "LKF", "爱拼才会赢", "紫樱菲飞", "云~涧~水", "☆star&", "心&梦君", "小小舒", "丹里个丹", "蓝天", "LzHس钻戒", "心の始迹",
            "不要叫醒", "简单&爱", "涅槃", "暮光之城", "·风雨淋·", "～脚印～", "∮  瀛~", "ャ爵洳σ", "安安", "亾.生缒.莍", "断线の风筝",
            "︷Ｋī︷Ｋǐ", "葉子葉子.花..", "み开心", "落/ǖā", "丛林深处", "小田鼠~", "隱形人○", "Rebelion", "海Ge", "小旧", "珊瑚虫", "Jennifer", "维维豆奶"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        linearQuan = (LinearLayout) findViewById(R.id.ll_quan);
        linearZone = (LinearLayout) findViewById(R.id.ll_zone);
        frameQuan = (FrameLayout) findViewById(R.id.fl_quan);
        framePraise = (FrameLayout) findViewById(R.id.fl_praise);
        frameZone = (FrameLayout) findViewById(R.id.fl_zone);
        frameConvert = (FrameLayout) findViewById(R.id.fl_convert);

        ivQuan = (ImageView) findViewById(R.id.iv_quan);
        ivZone = (ImageView) findViewById(R.id.iv_zone);
        tvQuanTag = (TextView) findViewById(R.id.tv_quan_tag);
        tvQuanSysTime = (TextView) findViewById(R.id.tv_quan_sys_time);
        tvQuanSendTime = (TextView) findViewById(R.id.tv_quan_send_time);

        tvZoneSysTime = (TextView) findViewById(R.id.tv_zone_sys_time);
        tvZoneSendTime = (TextView) findViewById(R.id.tv_zone_send_time);
        tvZone2Tag = (TextView) findViewById(R.id.tv_zone2_tag);

        tvPraiseSysTime = (TextView) findViewById(R.id.tv_praise_sys_time);
        tvPraiseSendTime = (TextView) findViewById(R.id.tv_praise_send_time);
        ivConvert = (ImageView) findViewById(R.id.iv_convert);
        ivPraise1 = (ImageView) findViewById(R.id.iv_praise1);
        ivPraise2 = (ImageView) findViewById(R.id.iv_praise2);
        ivPraise3 = (ImageView) findViewById(R.id.iv_praise3);

        tvConvertList = (TextView) findViewById(R.id.tv_convert_list);
        tvConvertSysTime = (TextView) findViewById(R.id.tv_convert_sys_time);

        etTag = (EditText) findViewById(R.id.et_tag);
        etClock = (EditText) findViewById(R.id.et_clock);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        rbZone1 = (RadioButton) findViewById(R.id.rb_zone1);
        rbZone2 = (RadioButton) findViewById(R.id.rb_zone2);
        rbQuan1 = (RadioButton) findViewById(R.id.rb_quan1);
        rbQuan2 = (RadioButton) findViewById(R.id.rb_quan2);


        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/"
                + dateFormat.format(new Date(System.currentTimeMillis())) + "/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zone1:
                        rbStr = " 空间1";
                        roleTag = "1";
                        setCommonZone();
                        tvZone2Tag.setVisibility(View.GONE);
                        ivZone.setImageResource(R.drawable.zone1);
                        tvZoneSendTime.setText("今天" + getSendTimeStr());
                        break;
                    case R.id.rb_zone2:
                        rbStr = " 空间2";
                        roleTag = "2";
                        setCommonZone();
                        tvZone2Tag.setVisibility(View.VISIBLE);
                        ivZone.setImageResource(R.drawable.zone2);
                        tvZoneSendTime.setText("今天" + getSendTimeStr());
                        tvZone2Tag.setText(dateFormat.format(new Date(System.currentTimeMillis())));
                        break;
                    case R.id.rb_quan1:
                        rbStr = " 朋友圈1";
                        roleTag = "1";
                        setCommonQuan();
                        ivQuan.setImageResource(R.drawable.quan1);
                        String sendtime = getSendTimeStr();
                        tvQuanSendTime.setText(sendtime);
                        tvPraiseSendTime.setText(sendtime);
                        break;
                    case R.id.rb_quan2:
                        rbStr = " 朋友圈2";
                        roleTag = "2";
                        setCommonQuan();
                        ivQuan.setImageResource(R.drawable.quan2);
                        String sendtime2 = getSendTimeStr();
                        tvQuanSendTime.setText(sendtime2);
                        tvPraiseSendTime.setText(sendtime2);
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImage(frameConvert);
                        }
                    }, 3000);
                } else if (rbQuan1.isChecked() || rbQuan2.isChecked()) {
                    getImage(frameQuan);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImage(framePraise);
                        }
                    }, 3000);
                }
            }
        });
    }

    private String getSendTimeStr() {
        if (etClock.getText().toString().contains(":")) {
            return etClock.getText().toString();
        } else {
            return etClock.getText() + ":" + (30 + (int) (Math.random() * 10));
        }
    }

    private void setCommonZone() {
        linearQuan.setVisibility(View.GONE);
        linearZone.setVisibility(View.VISIBLE);
        String systime = timeFormat.format(new Date(System.currentTimeMillis()));
        tvZoneSysTime.setText(systime);
        tvConvertSysTime.setText(systime);

        int count = (int) (10 + 3 * Math.random());
        StringBuffer sb = new StringBuffer();
        int index = (int) (Math.random() * 4);
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                sb.append("     ");
            } else {
                sb.append("、");
            }
            sb.append(converts[index]);
            index += (int) (Math.random() * 4) + 1;
        }
        tvConvertList.setText(sb.toString());
    }

    private void setCommonQuan() {
        linearQuan.setVisibility(View.VISIBLE);
        linearZone.setVisibility(View.GONE);
        String str = etTag.getText() + "\n" + getString(R.string.content_share);
        SpannableString content = new SpannableString(str);
        int start = str.indexOf("49521");
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)),
                start, start + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)),
                str.indexOf("http"), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvQuanTag.setText(content);
        String systime = timeFormat.format(new Date(System.currentTimeMillis()));
        tvQuanSysTime.setText(systime);
        tvPraiseSysTime.setText(systime);

        int i1 = (int) (Math.random() * 4), i2 = i1 + 1 + (int) (Math.random() * 4), i3 = i2 + 1 + (int) (Math.random() * 3);
        ivPraise1.setImageResource(praiseIds[i1]);
        ivPraise2.setImageResource(praiseIds[i2]);
        ivPraise3.setImageResource(praiseIds[i3]);
    }

    private void getImage(View view) {
        Bitmap bitmap = loadBitmapFromView(view);
        try {
            saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
            Toast.makeText(this, "保存成功" + rbStr, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.draw(c);
        return screenshot;
    }

    public void saveBitmap(Bitmap bm, String name) throws IOException {
        File dir = new File(folder, roleTag);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }
}
