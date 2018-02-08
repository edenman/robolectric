package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.N_MR1;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.IUserManager;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Robolectric implementation of {@link android.os.UserManager}.
 */
@Implements(value = UserManager.class, minSdk = JELLY_BEAN_MR1)
public class ShadowUserManager {

  private boolean userUnlocked = true;
  private boolean managedProfile = false;
  private boolean isDemoUser = false;
  private Map<UserHandle, Bundle> userRestrictions = new HashMap<>();
  private BiMap<UserHandle, Long> userProfiles = HashBiMap.create();
  private Map<String, Bundle> applicationRestrictions = new HashMap<>();
  private long nextUserSerial = 0;
  private Map<UserHandle, UserState> userState = new HashMap<>();
  private Context context;
  private boolean enforcePermissions;

  @Implementation
  protected void __constructor__(Context context, IUserManager service) {
    this.context = context;
  }

  public ShadowUserManager() {
    addUserProfile(Process.myUserHandle());
  }

  /**
   * Compared to real Android, there is no check that the package name matches the application
   * package name and the method returns instantly.
   *
   * @see #setApplicationRestrictions(String, Bundle)
   */
  @Implementation(minSdk = JELLY_BEAN_MR2)
  protected Bundle getApplicationRestrictions(String packageName) {
    Bundle bundle = applicationRestrictions.get(packageName);
    return bundle != null ? bundle : new Bundle();
  }

  /**
   * Sets the value returned by {@link UserManager#getApplicationRestrictions(String)}.
   */
  public void setApplicationRestrictions(String packageName, Bundle restrictions) {
    applicationRestrictions.put(packageName, restrictions);
  }

  /**
   * Adds a profile associated for the user that the calling process is running on.
   *
   * The user is assigned an arbitrary unique serial number.
   *
   * @return the user's serial number
   */
  public long addUserProfile(UserHandle userHandle) {
    long serialNumber = nextUserSerial++;
    userProfiles.put(userHandle, serialNumber);
    return serialNumber;
  }

  @Implementation(minSdk = LOLLIPOP)
  protected List<UserHandle> getUserProfiles() {
    return ImmutableList.copyOf(userProfiles.keySet());
  }

  @Implementation(minSdk = N)
  protected boolean isUserUnlocked() {
    return userUnlocked;
  }

  /**
   * Setter for {@link UserManager#isUserUnlocked()}
   */
  public void setUserUnlocked(boolean userUnlocked) {
    this.userUnlocked = userUnlocked;
  }

  /**
   * If permissions are enforced (see {@link #enforcePermissionChecks(boolean)}) and the application
   * doesn't have the {@link android.Manifest.permission#MANAGE_USERS} permission, throws a
   * {@link SecurityManager} exception.
   *
   * @return `false` by default, or the value specified via {@link #setManagedProfile(boolean)}
   * @see #enforcePermissionChecks(boolean)
   * @see #setManagedProfile(boolean)
   */
  @Implementation(minSdk = LOLLIPOP)
  protected boolean isManagedProfile() {
    if (enforcePermissions && !hasManageUsersPermission()) {
      throw new SecurityException(
          "You need MANAGE_USERS permission to: check if specified user a " +
              "managed profile outside your profile group");
    }
    return managedProfile;
  }

  public void enforcePermissionChecks(boolean enforcePermissions) {
    this.enforcePermissions = enforcePermissions;
  }

  /**
   * Setter for {@link UserManager#isManagedProfile()}.
   */
  public void setManagedProfile(boolean managedProfile) {
    this.managedProfile = managedProfile;
  }

  @Implementation(minSdk = LOLLIPOP)
  protected boolean hasUserRestriction(String restrictionKey, UserHandle userHandle) {
    Bundle bundle = userRestrictions.get(userHandle);
    return bundle != null && bundle.getBoolean(restrictionKey);
  }

  public void setUserRestriction(UserHandle userHandle, String restrictionKey, boolean value) {
    Bundle bundle = getUserRestrictionsForUser(userHandle);
    bundle.putBoolean(restrictionKey, value);
  }

  /**
   * Removes all user restrictions set of a user identified by {@code userHandle}.
   */
  public void clearUserRestrictions(UserHandle userHandle) {
    if (userRestrictions.containsKey(userHandle)) {
      userRestrictions.remove(userHandle);
    }
  }

  @Implementation(minSdk = JELLY_BEAN_MR2)
  protected Bundle getUserRestrictions(UserHandle userHandle) {
    return getUserRestrictionsForUser(userHandle);
  }

  private Bundle getUserRestrictionsForUser(UserHandle userHandle) {
    Bundle bundle = userRestrictions.get(userHandle);
    if (bundle == null) {
      bundle = new Bundle();
      userRestrictions.put(userHandle, bundle);
    }
    return bundle;
  }

  /**
   * @see #addUserProfile(UserHandle)
   */
  @Implementation
  protected long getSerialNumberForUser(UserHandle userHandle) {
    Long result = userProfiles.get(userHandle);
    return result == null ? -1L : result;
  }

  /**
   * @deprecated prefer {@link #addUserProfile(UserHandle)} to ensure consistency of profiles known
   * to the {@link UserManager}. Furthermore, calling this method for the current user, i.e: {@link
   * Process#myUserHandle()} is no longer necessary as this user is always known to UserManager and
   * has a preassigned serial number.
   */
  @Deprecated
  public void setSerialNumberForUser(UserHandle userHandle, long serialNumber) {
    userProfiles.put(userHandle, serialNumber);
  }

  /**
   * @see #addUserProfile(UserHandle)
   */
  @Implementation
  protected UserHandle getUserForSerialNumber(long serialNumber) {
    return userProfiles.inverse().get(serialNumber);
  }

  private boolean hasManageUsersPermission() {
    return context.getPackageManager().checkPermission(permission.MANAGE_USERS, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
  }

  private void checkPermissions() {
    // TODO Ensure permisions
    //              throw new SecurityException("You need INTERACT_ACROSS_USERS or MANAGE_USERS
    // permission "
    //                + "to: check " + name);throw new SecurityException();
  }

  /**
   * @return `false` by default, or the value specified via {@link #setIsDemoUser(boolean)}
   */
  @Implementation(minSdk = N_MR1)
  protected boolean isDemoUser() {
    return isDemoUser;
  }

  /**
   * Sets that the current user is a demo user; controls the return value of
   * {@link UserManager#isDemoUser()}.
   */
  public void setIsDemoUser(boolean isDemoUser) {
    this.isDemoUser = isDemoUser;
  }

  /**
   * @see #setUserState(UserHandle, UserState)
   */
  @Implementation
  protected boolean isUserRunning(UserHandle handle) {
    checkPermissions();
    UserState state = userState.get(handle);

    if (state == UserState.STATE_RUNNING_LOCKED
        || state == UserState.STATE_RUNNING_UNLOCKED
        || state == UserState.STATE_RUNNING_UNLOCKING) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @see #setUserState(UserHandle, UserState)
   */
  @Implementation
  protected boolean isUserRunningOrStopping(UserHandle handle) {
    checkPermissions();
    UserState state = userState.get(handle);

    if (state == UserState.STATE_RUNNING_LOCKED
        || state == UserState.STATE_RUNNING_UNLOCKED
        || state == UserState.STATE_RUNNING_UNLOCKING
        || state == UserState.STATE_STOPPING) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Describes the current state of the user. State can be set using
   *  {@link UserManager#setUserState()}
   */
  public enum UserState {
    // User is first coming up.
    STATE_BOOTING,
    // User is in the locked state.
    STATE_RUNNING_LOCKED,
    // User is in the unlocking state.
    STATE_RUNNING_UNLOCKING,
    // User is in the running state.
    STATE_RUNNING_UNLOCKED,
    // User is in the initial process of being stopped.
    STATE_STOPPING,
    // User is in the final phase of stopping, sending Intent.ACTION_SHUTDOWN.
    STATE_SHUTDOWN
  }

  /**
   * Sets the current state for a given user, see {@link UserManager#isUserRunning(UserHandle)}
   * and {@link UserManager#isUserRunningOrStopping(UserHandle)}
   */
  public void setUserState(UserHandle handle, UserState state) {
    userState.put(handle, state);
  }

  /**
   * @return an empty list
   */
  @Implementation
  protected List<UserInfo> getUsers() {
    // Implement this - return empty list to avoid NPE from call to getUserCount()
    return ImmutableList.of();
  }
}
