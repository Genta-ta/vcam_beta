package com.gentata.vcam;

import androidx.annotation.NonNull;
import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;

public class MainHook extends XposedModule {

    public MainHook(@NonNull XposedInterface base, @NonNull ModuleLoadedParam param) {
        super(base, param);
    }

    @Override
    public void onPackageLoaded(@NonNull PackageLoadedParam param) {
        super.onPackageLoaded(param);
        // Di sinilah nanti kita akan meletakkan kode untuk "mencegat" kamera
        if (param.isSystem() || param.getPackageName().equals("com.android.systemui")) {
            return;
        }
        
        log("Berhasil masuk ke aplikasi: " + param.getPackageName());
    }
}
