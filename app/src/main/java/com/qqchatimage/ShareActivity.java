package com.qqchatimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    FrameLayout framePraise, frameConvert;
    ImageView ivQuan, ivZone;
    TextView tvPraiseSysTime, tvPraiseSendTime, tvPraiseTag;
    TextView tvConvertList, tvConvertSysTime, tvZoneTag;
    ImageView ivPraise1, ivPraise2, ivPraise3;
    EditText etTag, etClock;
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

        framePraise = (FrameLayout) findViewById(R.id.fl_praise);
        frameConvert = (FrameLayout) findViewById(R.id.fl_convert);

        tvPraiseTag = (TextView) findViewById(R.id.tv_quan_tag);
        tvPraiseSysTime = (TextView) findViewById(R.id.tv_praise_sys_time);
        tvPraiseSendTime = (TextView) findViewById(R.id.tv_praise_send_time);
        ivPraise1 = (ImageView) findViewById(R.id.iv_praise1);
        ivPraise2 = (ImageView) findViewById(R.id.iv_praise2);
        ivPraise3 = (ImageView) findViewById(R.id.iv_praise3);

        tvConvertList = (TextView) findViewById(R.id.tv_convert_list);
        tvConvertSysTime = (TextView) findViewById(R.id.tv_convert_sys_time);
        tvZoneTag = (TextView) findViewById(R.id.tv_zone_tag);

        etTag = (EditText) findViewById(R.id.et_tag);
        etClock = (EditText) findViewById(R.id.et_clock);

        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/"
                + dateFormat.format(new Date(System.currentTimeMillis())) + "/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        setCommonZone();
        setCommonQuan();

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if ("".equals(etTag.getText().toString())) {
                        Toast.makeText(ShareActivity.this, "输入分享标签", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getImage(frameConvert, false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImage(framePraise, true);
                        }
                    }, 3000);
                }
            }
        });

        etTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvZoneTag.setText(s);
                tvPraiseTag.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etClock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvPraiseSendTime.setText(s + ":" + (30 + (int) (Math.random() * 10)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setCommonZone() {
        String systime = timeFormat.format(new Date(System.currentTimeMillis()));
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
        String systime = timeFormat.format(new Date(System.currentTimeMillis()));
        tvPraiseSysTime.setText(systime);
        tvPraiseSendTime.setText(etClock.getText() + ":" + (30 + (int) (Math.random() * 10)));

        int i1 = (int) (Math.random() * 4), i2 = i1 + 1 + (int) (Math.random() * 4), i3 = i2 + 1 + (int) (Math.random() * 3);
        ivPraise1.setImageResource(praiseIds[i1]);
        ivPraise2.setImageResource(praiseIds[i2]);
        ivPraise3.setImageResource(praiseIds[i3]);
    }

    private void getImage(View view, boolean over) {
        Bitmap bitmap = loadBitmapFromView(view);
        try {
            saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
            if (over) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
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
