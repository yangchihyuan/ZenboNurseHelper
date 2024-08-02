package tw.edu.cgu.ai.zenbo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

public class SaveImage {
    static public void save(@NonNull Bitmap FrameBitmap, String appendix) {
        long millisecondCurrent = System.currentTimeMillis();
        Date currentTime = Calendar.getInstance().getTime();
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOutputStream = null;
        File file = new File(path + "/Captures/", Long.toString(millisecondCurrent) + appendix + ".jpg");
        try {
            fOutputStream = new FileOutputStream(file);

            FrameBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);

            fOutputStream.flush();
            fOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
