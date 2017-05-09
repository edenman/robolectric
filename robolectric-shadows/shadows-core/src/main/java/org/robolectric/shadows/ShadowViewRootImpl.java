package org.robolectric.shadows;

import android.os.Looper;
import android.view.ViewRootImpl;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = ViewRootImpl.class, isInAndroidSdk = false)
public class ShadowViewRootImpl {

  @Implementation
  protected static Object getWindowSession(Looper mainLooper) {
    return null;
  }

  @Implementation
  protected void playSoundEffect(int effectId) {
  }
}
