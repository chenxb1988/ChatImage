package com.qqchatimage;

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

public class MainActivity extends AppCompatActivity {
    private int[] backs = {R.drawable.back01, R.drawable.back02, R.drawable.back03,
            R.drawable.back04, R.drawable.back05, R.drawable.back06,
            R.drawable.back07, R.drawable.back08, R.drawable.back09,
            R.drawable.back10, R.drawable.back11, R.drawable.back12,
            R.drawable.back13, R.drawable.back17, R.drawable.back21,
            R.drawable.back22, R.drawable.back29, R.drawable.back48,
            R.drawable.back34, R.drawable.back35, R.drawable.back38,
            R.drawable.back39, R.drawable.back41, R.drawable.back42,
            R.drawable.back43, R.drawable.back44, R.drawable.back45,
            R.drawable.back50, R.drawable.back51, R.drawable.back58,
            R.drawable.back52, R.drawable.back53, R.drawable.back54,
            R.drawable.back55, R.drawable.back56, R.drawable.back57};
    private ImageView image;
    private TextView text;
    private EditText web, edit;
    private CheckBox checkBox, cbAiwu;
    private int timeDelay = 60;
    private File folder;
    private String msgPath;
    private boolean preview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        web = (EditText) findViewById(R.id.et_web);
        edit = (EditText) findViewById(R.id.et_code);
        checkBox = (CheckBox) findViewById(R.id.cb_preview);
        cbAiwu = (CheckBox) findViewById(R.id.cb_aiwu);

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
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String minute = format2.format(date);

        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/" + minute + "/");
        msgPath = Environment.getExternalStorageDirectory().toString() + "/naruto/message.png";
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            text.append(msg.obj.toString());
        }
    }

    MyHandler mHandler = new MyHandler();

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
            if (cbAiwu.isChecked()) {
                message = BitmapFactory.decodeResource(getResources(), R.drawable.message_bg);
                canvas.drawBitmap(message, 480 - x, 275 - y, paintb);
            } else {
                message = BitmapFactory.decodeResource(getResources(), R.drawable.message_bg2);
                canvas.drawBitmap(message, 440 - x, 275 - y, paintb);
            }
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
        if (cbAiwu.isChecked()) {
            canvas.drawText("仙人自来", 1030 - x, 290 - y, paint);
        } else {
            canvas.drawText("风云", 1040 - x, 290 - y, paint);
        }

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(getResources().getColor(R.color.codeTextColor));
        textPaint.setTextSize(19.0F);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));


        String str = web.getText() + "  " + edit.getText();
        SpannableString content = new SpannableString(str);
        try {
            int start = str.indexOf("http");
            content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlue)), start, web.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new UnderlineSpan(), start, web.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }

        StaticLayout layout = new StaticLayout(content, textPaint, 1000, Layout.Alignment.ALIGN_NORMAL, 1.4F, 1.0F, true);
        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        if (cbAiwu.isChecked()) {
            canvas.translate(620 - x - getResources().getInteger(R.integer.code_x),
                    400 - y - getResources().getInteger(R.integer.code_y));
        } else {
            canvas.translate(600 - x - getResources().getInteger(R.integer.code_x),
                    400 - y - getResources().getInteger(R.integer.code_y));
        }
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
                saveBitmap(i, back, time + ".jpg");
            } catch (IOException e) {
                Message msg = new Message();
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
                return;
            }
        }
    }


    public void saveBitmap(int index, Bitmap bm, String name) throws IOException {
        File f = new File(folder, name);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();

        Message msg = new Message();
        msg.obj = index + "\t\t\t\t\t\t\t";
        mHandler.sendMessage(msg);
    }
}
