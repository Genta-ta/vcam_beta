package com.gentata.vcam;

import android.view.Surface;
import androidx.annotation.NonNull;
import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.ReturnCode;

public class MainHook extends XposedModule {

    /* Konstruktor harus tepat seperti ini untuk LibXposed */
    public MainHook(@NonNull XposedInterface base, @NonNull ModuleLoadedParam param) {
        super(base, param);
    }

    @Override
    public void onPackageLoaded(@NonNull PackageLoadedParam param) {
        super.onPackageLoaded(param);
        
        // Gunakan getBase().log() karena log() biasa tidak ada di class ini
        getBase().log("VCAM dimuat di: " + param.getPackageName());
    }

    private void startFFmpegTranslator(String rtmpUrl, Surface targetSurface) {
        // Perintah FFmpeg untuk nembak ke Surface
        String cmd = "-i " + rtmpUrl + " -f android_surface " + targetSurface.toString();

        FFmpegKit.executeAsync(cmd, session -> {
            if (ReturnCode.isSuccess(session.getReturnCode())) {
                getBase().log("FFmpeg Berhasil menghubungkan RTMP!");
            } else {
                getBase().log("FFmpeg Gagal!");
            }
        });
    }
}
