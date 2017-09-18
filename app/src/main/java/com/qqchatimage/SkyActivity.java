package com.qqchatimage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkyActivity extends AppCompatActivity {
    private String TAG_SP_SKY = "tag_sp_sky";
    private int[] backs = {R.drawable.back01, R.drawable.back02, R.drawable.back03,
            R.drawable.back04, R.drawable.back05, R.drawable.back06,
            R.drawable.back07, R.drawable.back08, R.drawable.back09,
            R.drawable.back10, R.drawable.back11, R.drawable.back12,
            R.drawable.back13, R.drawable.back14, R.drawable.back15,
            R.drawable.back16, R.drawable.back17, R.drawable.back18,
            R.drawable.back19, R.drawable.back20, R.drawable.back21,
            R.drawable.back22, R.drawable.back23, R.drawable.back24,
            R.drawable.back25, R.drawable.back26, R.drawable.back27,
            R.drawable.back28, R.drawable.back29, R.drawable.back30,
            R.drawable.back31, R.drawable.back32,
            R.drawable.back34, R.drawable.back35, R.drawable.back36,
            R.drawable.back37, R.drawable.back38, R.drawable.back39,
            R.drawable.back40, R.drawable.back41, R.drawable.back42,
            R.drawable.back43, R.drawable.back44, R.drawable.back45,
            R.drawable.back46, R.drawable.back47, R.drawable.back48,
            R.drawable.back49, R.drawable.back50, R.drawable.back51,
            R.drawable.back52, R.drawable.back53, R.drawable.back54,
            R.drawable.back55, R.drawable.back56, R.drawable.back57,
            R.drawable.back58};
    private ImageView image;
    private TextView textShow;
    private EditText textContent, textCode;
    private CheckBox checkBox;
    private int timeDelay = 60;
    private File folder;
    private String msgPath;
    private boolean preview = false;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky);
        image = (ImageView) findViewById(R.id.image);
        textShow = (TextView) findViewById(R.id.text);
        textContent = (EditText) findViewById(R.id.et_web);
        textCode = (EditText) findViewById(R.id.et_code);
        checkBox = (CheckBox) findViewById(R.id.cb_preview);

        sp = getSharedPreferences("SwitchShare", Context.MODE_PRIVATE);
        editor = sp.edit();

        textContent.setText(sp.getString(TAG_SP_SKY, getString(R.string.content_sky)));

        createFolder();
        findViewById(R.id.btn_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preview) {
                    generateImage(0);
                } else {
                    timeDelay = 60;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            generateSharePic();
                            List<Integer> list = new ArrayList<>();
                            for (int i = 0; i < backs.length; i++) {
                                list.add(i);
                            }


                            for (int i = 0; i < 30; i++) {
                                Integer index = list.get((int) (list.size() * Math.random()));
                                generateImage(index);
                                list.remove(index);
                            }
                        }
                    }).start();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preview = isChecked;
            }
        });
    }

    private void createFolder() {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dir = format2.format(date) + "推广分享";

        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/" + dir + "/");
        if (folder.exists()) {
            deleteFile(folder);
        }

        msgPath = Environment.getExternalStorageDirectory().toString() + "/naruto/message.png";
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
            for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
            }
        } else {
            file.delete();
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textShow.append(msg.obj.toString());
        }
    }

    MyHandler mHandler = new MyHandler();

    private void generateSharePic() {
        Bitmap originBack = BitmapFactory.decodeResource(getResources(), R.drawable.pic_share_origin);
        int x = (int) (50 * Math.random());
        int y = (int) (40 * Math.random());
        Bitmap back = Bitmap.createBitmap(originBack, x, y,
                originBack.getWidth() - x - (int) (200 * Math.random()),
                originBack.getHeight() - y - (int) (50 * Math.random()));
        Canvas canvas = new Canvas(back);

        Paint paintb = new Paint();
        paintb.setAntiAlias(true);
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setTextSize(22);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.textColorSkyTime));
        canvas.drawText(t1, 175 - x, 125 - y, paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.codeTextColor));
        textPaint.setTextSize(25.0F);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setColor(getResources().getColor(R.color.textColorSkyDate));

        String str = textContent.getText() + "\n" + textCode.getText();
        SpannableString content = new SpannableString(str);
        try {
            int start = str.indexOf("ruansky.com");
            int end = start + 11;
            content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        editor.putString(TAG_SP_SKY, textContent.getText().toString());
        editor.commit();

        StaticLayout layout = new StaticLayout(content, textPaint, 1000, Layout.Alignment.ALIGN_NORMAL, 1.2F, 1.0F, true);
        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        canvas.translate(130 - x - getResources().getInteger(R.integer.code_x),
                240 - y - getResources().getInteger(R.integer.code_y));
        layout.draw(canvas);
        canvas.restore();//别忘了restore

        try {
            saveBitmap("分享\n", back, "分享.jpg");
        } catch (IOException e) {
            Message msg = new Message();
            msg.obj = e.getMessage();
            mHandler.sendMessage(msg);
            return;
        }
    }

    private void generateImage(int i) {
        Bitmap originBack = BitmapFactory.decodeResource(getResources(), backs[i]);
        int x = (int) (295 + 20 * Math.random());
        int y = (int) (40 * Math.random());
        Bitmap back = Bitmap.createBitmap(originBack, x, y, originBack.getWidth() - x, originBack.getHeight() - y);
        Canvas canvas = new Canvas(back);

        Paint paintb = new Paint();
        paintb.setAntiAlias(true);

        Bitmap message = BitmapFactory.decodeFile(msgPath);
        if (message == null) {
            message = BitmapFactory.decodeResource(getResources(), R.drawable.message_bg);
            canvas.drawBitmap(message, 440 - x, 275 - y, paintb);
        } else {
            Bitmap bitmap = Bitmap.createScaledBitmap(message,
                    (int) (message.getWidth() * 1.5), (int) (message.getHeight() * 1.5), true);
            canvas.drawBitmap(bitmap, 920 - x - message.getWidth(), 265 - y, paintb);
        }

        long time = System.currentTimeMillis() - timeDelay * 1000;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        paint.setTextSize(18);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.textColorTime));
        canvas.drawText(t1, 725 - x, 255 - y, paint);
        timeDelay += 5 + 5 * Math.random();

        paint.setTextSize(20);
        paint.setColor(getResources().getColor(R.color.textColorName));
        canvas.drawText("风云", 1050 - x, 290 - y, paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.codeTextColor));
        textPaint.setTextSize(19.0F);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        String str = textContent.getText() + "  " + textCode.getText();
        SpannableString content = new SpannableString(str);
        try {
            int start = str.indexOf("ruansky.com");
            int end = start + 11;
            content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        StaticLayout layout = new StaticLayout(content, textPaint, 1000, Layout.Alignment.ALIGN_NORMAL, 1.4F, 1.0F, true);
        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        canvas.translate(600 - x - getResources().getInteger(R.integer.code_x),
                400 - y - getResources().getInteger(R.integer.code_y));
        layout.draw(canvas);
        canvas.restore();//别忘了restore
//        canvas.drawText(,
//                1000 - x - getResources().getInteger(R.integer.code_x),
//                600 - y - getResources().getInteger(R.integer.code_y),
//                paint);

        if (preview) {
            image.setImageBitmap(back);
        } else {
            try {
                saveBitmap(i + "", back, time + ".jpg");
            } catch (IOException e) {
                Message msg = new Message();
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
                return;
            }
        }
    }


    public void saveBitmap(String index, Bitmap bm, String name) throws IOException {
        File f = new File(folder, name);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();

        Message msg = new Message();
        try {
            int i = Integer.parseInt(index);
            int b = i * 10;
            msg.obj = index + "\t\t\t\t\t\t\t";
        } catch (Exception e) {
            msg.obj = index;
        }
        mHandler.sendMessage(msg);
    }
}
