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
        // Log sederhana menggunakan getBase()
        getBase().log(XposedInterface.LOG_LEVEL_INFO, "VCAM: Module loaded for " + param.getPackageName());
    }

    private void startFFmpegTranslator(String rtmpUrl, Surface targetSurface) {
        String cmd = "-i " + rtmpUrl + " -f android_surface " + targetSurface.toString();

        FFmpegKit.executeAsync(cmd, session -> {
            if (ReturnCode.isSuccess(session.getReturnCode())) {
                getBase().log(XposedInterface.LOG_LEVEL_INFO, "VCAM: FFmpeg Success");
            } else {
                getBase().log(XposedInterface.LOG_LEVEL_ERROR, "VCAM: FFmpeg Failed");
            }
        });
    }
}
