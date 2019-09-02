package com.eren.snowframe.widget.status;

import android.app.Activity;
import android.view.View;

/**
 * @author Eren
 * <p>
 * 状态字体颜色
 */
public class StatusBarTextUtil {

    /**
     * @param activity 需要设置的 activity
     * @param dark     是否设置为黑色
     */
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
