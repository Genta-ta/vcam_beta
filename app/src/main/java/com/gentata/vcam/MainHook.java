package com.gentata.vcam;

import android.view.Surface;
import androidx.annotation.NonNull;
import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.ReturnCode;

public class MainHook extends XposedModule {

    public MainHook(@NonNull XposedInterface base, @NonNull ModuleLoadedParam param) {
        super(base, param);
    }

    @Override
    public void onPackageLoaded(@NonNull PackageLoadedParam param) {
        super.onPackageLoaded(param);
        
        // Perbaikan isSystem(): Di API ini, kita cek lewat param
        // Jika aplikasi yang dimuat bukan target kita, lewatkan saja
        log("Penerjemah FFmpeg mencoba dimuat di: " + param.getPackageName());
    }

    private void startFFmpegTranslator(String rtmpUrl, Surface targetSurface) {
        // Perintah FFmpeg
        String cmd = "-i " + rtmpUrl + " -f android_surface " + targetSurface.toString();

        FFmpegKit.executeAsync(cmd, session -> {
            if (ReturnCode.isSuccess(session.getReturnCode())) {
                log("FFmpeg Berhasil menghubungkan RTMP ke Kamera!");
            } else {
                log("FFmpeg Gagal: " + session.getFailStackTrace());
            }
        });
    }

    // Perbaikan metode log agar sesuai dengan XposedInterface
    private void log(String message) {
        // LibXposed log butuh level (misal 2 untuk INFO) dan tag
        getBase().log(2, "VCAM-FFMPEG", message);
    }
}
