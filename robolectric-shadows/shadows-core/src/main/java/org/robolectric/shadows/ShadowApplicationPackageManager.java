package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static org.robolectric.RuntimeEnvironment.getRobolectricPackageManager;
import static org.robolectric.shadow.api.Shadow.directlyOn;

import android.annotation.DrawableRes;
import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.StringRes;
import android.annotation.UserIdInt;
import android.app.ApplicationPackageManager;
import android.app.PackageInstallObserver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstrumentationInfo;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.KeySet;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.MoveCallback;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager.OnPermissionsChangedListener;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.UserHandle;
import android.os.storage.VolumeInfo;
import java.util.List;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(value = ApplicationPackageManager.class, isInAndroidSdk = false, looseSignatures = true)
public class ShadowApplicationPackageManager extends ShadowPackageManager {

  @Implementation(minSdk = VERSION_CODES.LOLLIPOP)
  protected PackageInstaller getPackageInstaller() {
    return getDelegatePackageManager().getPackageInstaller();
  }

  @Implementation
  public List<PackageInfo> getInstalledPackages(int flags) {
    return getRobolectricPackageManager().getInstalledPackages(flags);
  }

  @Implementation
  public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
    return getRobolectricPackageManager().getActivityInfo(component, flags);
  }

  @Implementation
  public boolean hasSystemFeature(String name) {
    return getRobolectricPackageManager().hasSystemFeature(name);
  }

  @Implementation
  protected int getComponentEnabledSetting(ComponentName componentName) {
    return getDelegatePackageManager().getComponentEnabledSetting(componentName);
  }

  @Implementation
  protected @Nullable String getNameForUid(int uid) {
    return getDelegatePackageManager().getNameForUid(uid);
  }

  @Implementation
  protected @Nullable String[] getPackagesForUid(int uid) {
    return getDelegatePackageManager().getPackagesForUid(uid);
  }

  @Implementation
  protected int getApplicationEnabledSetting(String packageName) {
    return getDelegatePackageManager().getApplicationEnabledSetting(packageName);
  }

  @Implementation
  protected ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().getProviderInfo(component, flags);
  }

  @Implementation
  public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
    getRobolectricPackageManager().setComponentEnabledSetting(componentName, newState, flags);
  }

  @Implementation
  protected void setApplicationEnabledSetting(String packageName, int newState, int flags) {
    getDelegatePackageManager().setApplicationEnabledSetting(packageName, newState, flags);
  }

  @Implementation
  public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
    return getRobolectricPackageManager().getApplicationInfo(packageName, flags);
  }

  @Implementation
  public ResolveInfo resolveActivity(Intent intent, int flags) {
    return getRobolectricPackageManager().resolveActivity(intent, flags);
  }

  @Implementation
  protected ProviderInfo resolveContentProvider(String name, int flags) {
    return getDelegatePackageManager().resolveContentProvider(name, flags);
  }

  @Implementation
  protected ProviderInfo resolveContentProviderAsUser(String name, int flags, @UserIdInt int userId) {
    return getDelegatePackageManager().resolveContentProviderAsUser(name, flags, userId);
  }

  @Implementation
  public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
    return getRobolectricPackageManager().getPackageInfo(packageName, flags);
  }

  @Implementation
  public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
    return getRobolectricPackageManager().queryIntentServices(intent, flags);
  }

  @Implementation
  public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
    return getRobolectricPackageManager().queryIntentActivities(intent, flags);
  }

  @Implementation
  public int checkPermission(String permName, String pkgName) {
    return getRobolectricPackageManager().checkPermission(permName, pkgName);
  }

  @Implementation
  public ActivityInfo getReceiverInfo(ComponentName className, int flags) throws NameNotFoundException {
    return getRobolectricPackageManager().getReceiverInfo(className, flags);
  }

  @Implementation
  public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
    return getRobolectricPackageManager().queryBroadcastReceivers(intent, flags);
  }

  @Implementation
  public ResolveInfo resolveService(Intent intent, int flags) {
    return getRobolectricPackageManager().resolveService(intent, flags);
  }

  @Implementation
  public ServiceInfo getServiceInfo(ComponentName className, int flags) throws NameNotFoundException {
    return getRobolectricPackageManager().getServiceInfo(className, flags);
  }

  @Implementation
  protected Resources getResourcesForApplication(@NonNull ApplicationInfo applicationInfo) throws PackageManager.NameNotFoundException {
    return getDelegatePackageManager().getResourcesForApplication(applicationInfo);
  }

  @Implementation
  protected List<ApplicationInfo> getInstalledApplications(int flags) {
    return getDelegatePackageManager().getInstalledApplications(flags);
  }

  @Implementation
  protected String getInstallerPackageName(String packageName) {
    return getDelegatePackageManager().getInstallerPackageName(packageName);
  }

  @Implementation
  protected PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().getPermissionInfo(name, flags);
  }

  @Implementation(minSdk = M)
  protected boolean shouldShowRequestPermissionRationale(String permission) {
    return permissionRationaleMap.containsKey(permission) ? permissionRationaleMap.get(permission) : false;
  }

  @Implementation
  protected FeatureInfo[] getSystemAvailableFeatures() {
    return systemAvailableFeatures.isEmpty() ? null : systemAvailableFeatures.toArray(new FeatureInfo[systemAvailableFeatures.size()]);
  }

  @Implementation
  protected void verifyPendingInstall(int id, int verificationCode) {
    if (verificationResults.containsKey(id)) {
      throw new IllegalStateException("Multiple verifications for id=" + id);
    }
    verificationResults.put(id, verificationCode);
  }

  @Implementation
  protected void freeStorageAndNotify(long freeStorageSize, IPackageDataObserver observer) {
    getDelegatePackageManager().freeStorageAndNotify(freeStorageSize, observer);
  }

  @Implementation
  protected void freeStorageAndNotify(String volumeUuid, long freeStorageSize, IPackageDataObserver observer) {
    getDelegatePackageManager().freeStorageAndNotify(volumeUuid, freeStorageSize, observer);
  }

  @Implementation
  protected void setInstallerPackageName(String targetPackage, String installerPackageName) {
    getDelegatePackageManager().setInstallerPackageName(targetPackage, installerPackageName);
  }

  @Implementation
  protected List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
    return getDelegatePackageManager().queryIntentContentProviders(intent, flags);
  }

  @Implementation
  protected List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int flags, int userId) {
    return getDelegatePackageManager().queryIntentContentProvidersAsUser(intent, flags, userId);
  }

  @Implementation
  protected String getPermissionControllerPackageName() {
    return getDelegatePackageManager().getPermissionControllerPackageName();
  }

  @Implementation(maxSdk = JELLY_BEAN)
  protected void getPackageSizeInfo(String packageName, IPackageStatsObserver observer) {
    getDelegatePackageManager()
        .getPackageSizeInfoAsUser(packageName, UserHandle.myUserId(), observer);
  }

  @Implementation(minSdk = JELLY_BEAN_MR1, maxSdk = M)
  protected void getPackageSizeInfo(String pkgName, int uid, final IPackageStatsObserver callback) {
    getDelegatePackageManager().getPackageSizeInfoAsUser(pkgName, uid, callback);
  }

  @Implementation(minSdk = N)
  protected void getPackageSizeInfoAsUser(String pkgName, int uid, final IPackageStatsObserver callback) {
    getDelegatePackageManager().getPackageSizeInfoAsUser(pkgName, uid, callback);
  }

  @Implementation
  protected void deletePackage(String packageName, IPackageDeleteObserver observer, int flags) {
    getDelegatePackageManager().deletePackage(packageName, observer, flags);
  }

  @Implementation
  protected String[] currentToCanonicalPackageNames(String[] names) {
    String[] out = new String[names.length];
    for (int i = names.length - 1; i >= 0; i--) {
      if (currentToCanonicalNames.containsKey(names[i])) {
        out[i] = currentToCanonicalNames.get(names[i]);
      } else {
        out[i] = names[i];
      }
    }
    return out;
  }

  @Implementation
  protected boolean isSafeMode() {
    return getDelegatePackageManager().isSafeMode();
  }

  @Implementation
  public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
    return getDelegatePackageManager().getApplicationIcon(packageName);
  }

  @Implementation
  protected Drawable getApplicationIcon(ApplicationInfo info) {
    return getDelegatePackageManager().getApplicationIcon(info);
  }

  @Implementation
  protected Drawable getUserBadgeForDensity(UserHandle userHandle, int i) {
    return getDelegatePackageManager().getUserBadgeForDensity(userHandle, i);
  }

  @Implementation
  protected int checkSignatures(String pkg1, String pkg2) {
    return getDelegatePackageManager().checkSignatures(pkg1, pkg2);
  }

  @Implementation
  protected int checkSignatures(int uid1, int uid2) {
    return getDelegatePackageManager().checkSignatures(uid1, uid2);
  }

  @Implementation
  protected List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().queryPermissionsByGroup(group, flags);
  }

  public CharSequence getApplicationLabel(ApplicationInfo info) {
    return getRobolectricPackageManager().getApplicationLabel(info);
  }

  @Implementation
  public Intent getLaunchIntentForPackage(String packageName) {
    return getRobolectricPackageManager().getLaunchIntentForPackage(packageName);
  }

  ////////////////////////////

  @Implementation
  protected PackageInfo getPackageInfoAsUser(String packageName, int flags, int userId)
      throws NameNotFoundException {
    return getDelegatePackageManager().getPackageInfoAsUser(packageName, flags, userId);
  }

  @Implementation
  protected String[] canonicalToCurrentPackageNames(String[] names) {
    return getDelegatePackageManager().canonicalToCurrentPackageNames(names);
  }

  @Implementation
  protected Intent getLeanbackLaunchIntentForPackage(String packageName) {
    return getDelegatePackageManager().getLeanbackLaunchIntentForPackage(packageName);
  }

  @Implementation
  protected int[] getPackageGids(String packageName) throws NameNotFoundException {
    return getDelegatePackageManager().getPackageGids(packageName);
  }

  @Implementation
  protected int[] getPackageGids(String packageName, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().getPackageGids(packageName, flags);
  }

  @Implementation
  protected int getPackageUid(String packageName, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().getPackageUid(packageName, flags);
  }

  @Implementation
  protected int getPackageUidAsUser(String packageName, int userId) throws NameNotFoundException {
    return getDelegatePackageManager().getPackageUidAsUser(packageName, userId);
  }

  @Implementation
  protected int getPackageUidAsUser(String packageName, int flags, int userId)
      throws NameNotFoundException {
    return getDelegatePackageManager().getPackageUidAsUser(packageName, flags, userId);
  }

  @Implementation
  protected PermissionGroupInfo getPermissionGroupInfo(String name, int flags)
      throws NameNotFoundException {
    return getDelegatePackageManager().getPermissionGroupInfo(name, flags);
  }

  @Implementation
  protected List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
    return getDelegatePackageManager().getAllPermissionGroups(flags);
  }

  @Implementation
  protected ApplicationInfo getApplicationInfoAsUser(String packageName, int flags, int userId)
      throws NameNotFoundException {
    return getDelegatePackageManager().getApplicationInfoAsUser(packageName, flags, userId);
  }

  @Implementation
  protected String[] getSystemSharedLibraryNames() {
    return getDelegatePackageManager().getSystemSharedLibraryNames();
  }

  @Implementation
  public
  @NonNull
  String getServicesSystemSharedLibraryPackageName() {
    return getDelegatePackageManager().getServicesSystemSharedLibraryPackageName();
  }

  @Implementation
  public
  @NonNull
  String getSharedSystemSharedLibraryPackageName() {
    return getDelegatePackageManager().getSharedSystemSharedLibraryPackageName();
  }

  @Implementation
  protected boolean hasSystemFeature(String name, int version) {
    return getDelegatePackageManager().hasSystemFeature(name, version);
  }

  @Implementation
  protected boolean isPermissionRevokedByPolicy(String permName, String pkgName) {
    return getDelegatePackageManager().isPermissionRevokedByPolicy(permName, pkgName);
  }

  @Implementation
  protected boolean addPermission(PermissionInfo info) {
    return getDelegatePackageManager().addPermission(info);
  }

  @Implementation
  protected boolean addPermissionAsync(PermissionInfo info) {
    return getDelegatePackageManager().addPermissionAsync(info);
  }

  @Implementation
  protected void removePermission(String name) {
    getDelegatePackageManager().removePermission(name);
  }

  @Implementation
  protected void grantRuntimePermission(String packageName, String permissionName, UserHandle user) {
    getDelegatePackageManager().grantRuntimePermission(packageName, permissionName, user);
  }

  @Implementation
  protected void revokeRuntimePermission(String packageName, String permissionName, UserHandle user) {
    getDelegatePackageManager().revokeRuntimePermission(packageName, permissionName, user);
  }

  @Implementation
  protected int getPermissionFlags(String permissionName, String packageName, UserHandle user) {
    return getDelegatePackageManager().getPermissionFlags(permissionName, packageName, user);
  }

  @Implementation
  protected void updatePermissionFlags(String permissionName, String packageName, int flagMask,
      int flagValues, UserHandle user) {
    getDelegatePackageManager()
        .updatePermissionFlags(permissionName, packageName, flagMask, flagValues, user);
  }

  @Implementation
  protected int getUidForSharedUser(String sharedUserName) throws NameNotFoundException {
    return getDelegatePackageManager().getUidForSharedUser(sharedUserName);
  }

  @Implementation
  protected List<PackageInfo> getInstalledPackagesAsUser(int flags, int userId) {
    return getDelegatePackageManager().getInstalledPackagesAsUser(flags, userId);
  }

  @Implementation
  protected List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
    return getDelegatePackageManager().getPackagesHoldingPermissions(permissions, flags);
  }

  @Implementation
  protected ResolveInfo resolveActivityAsUser(Intent intent, int flags, int userId) {
    return getDelegatePackageManager().resolveActivityAsUser(intent, flags, userId);
  }

  @Implementation
  protected List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int flags, int userId) {
    return getDelegatePackageManager().queryIntentActivitiesAsUser(intent, flags, userId);
  }

  @Implementation
  protected List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
    return getDelegatePackageManager().queryIntentActivityOptions(caller, specifics, intent, flags);
  }

  @Implementation
  protected List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int flags, int userId) {
    return getDelegatePackageManager().queryBroadcastReceiversAsUser(intent, flags, userId);
  }

  @Implementation
  protected List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int flags, int userId) {
    return getDelegatePackageManager().queryIntentServicesAsUser(intent, flags, userId);
  }

  @Implementation
  protected List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
    return getDelegatePackageManager().queryContentProviders(processName, uid, flags);
  }

  @Implementation
  protected InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
    return getDelegatePackageManager().getInstrumentationInfo(className, flags);
  }

  @Implementation
  protected List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
    return getDelegatePackageManager().queryInstrumentation(targetPackage, flags);
  }

  @Nullable
  @Implementation
  public Drawable getDrawable(String packageName, @DrawableRes int resId, @Nullable ApplicationInfo appInfo) {
    return getDelegatePackageManager().getDrawable(packageName, resId, appInfo);
  }

  @Implementation
  public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityIcon(activityName);
  }

  public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityIcon(intent);
  }

  @Implementation
  protected Drawable getDefaultActivityIcon() {
    return getDelegatePackageManager().getDefaultActivityIcon();
  }

  @Implementation
  protected Drawable getActivityBanner(ComponentName activityName) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityBanner(activityName);
  }

  @Implementation
  protected Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityBanner(intent);
  }

  @Implementation
  protected Drawable getApplicationBanner(ApplicationInfo info) {
    return getDelegatePackageManager().getApplicationBanner(info);
  }

  @Implementation
  protected Drawable getApplicationBanner(String packageName) throws NameNotFoundException {
    return getDelegatePackageManager().getApplicationBanner(packageName);
  }

  @Implementation
  protected Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityLogo(activityName);
  }

  @Implementation
  protected Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
    return getDelegatePackageManager().getActivityLogo(intent);
  }

  @Implementation
  protected Drawable getApplicationLogo(ApplicationInfo info) {
    return getDelegatePackageManager().getApplicationLogo(info);
  }

  @Implementation
  protected Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
    return getDelegatePackageManager().getApplicationLogo(packageName);
  }

  @Implementation
  protected Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
    return getDelegatePackageManager().getUserBadgedIcon(icon, user);
  }

  @Implementation
  protected Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user, Rect badgeLocation, int badgeDensity) {
    return getDelegatePackageManager().getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity);
  }

  @Implementation
  protected Drawable getUserBadgeForDensityNoBackground(UserHandle user, int density) {
    return getDelegatePackageManager().getUserBadgeForDensityNoBackground(user, density);
  }

  @Implementation
  protected CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
    return getDelegatePackageManager().getUserBadgedLabel(label, user);
  }

  @Implementation
  protected Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
    return getDelegatePackageManager().getResourcesForActivity(activityName);
  }

  @Implementation
  protected Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
    return getDelegatePackageManager().getResourcesForApplication(appPackageName);
  }

  @Implementation
  protected Resources getResourcesForApplicationAsUser(String appPackageName, int userId) throws NameNotFoundException {
    return getDelegatePackageManager().getResourcesForApplicationAsUser(appPackageName, userId);
  }

  @Implementation
  protected void addOnPermissionsChangeListener(Object listener) {
    getDelegatePackageManager().addOnPermissionsChangeListener((OnPermissionsChangedListener) listener);
  }

  @Implementation
  protected void removeOnPermissionsChangeListener(Object listener) {
    getDelegatePackageManager().removeOnPermissionsChangeListener((OnPermissionsChangedListener) listener);
  }

  @Implementation
  protected CharSequence getText(String packageName, @StringRes int resid, ApplicationInfo appInfo) {
    return getDelegatePackageManager().getText(packageName, resid, appInfo);
  }

  @Implementation
  protected void installPackage(Uri packageURI, IPackageInstallObserver observer, int flags, String installerPackageName) {
    getDelegatePackageManager().installPackage(packageURI, observer, flags, installerPackageName);
  }

  @Implementation
  protected void installPackage(Object packageURI, Object observer, Object flags, Object installerPackageName) {
    getDelegatePackageManager().installPackage((Uri) packageURI, (PackageInstallObserver) observer, (int) flags, (String) installerPackageName);
  }

  @Implementation
  protected int installExistingPackage(String packageName) throws NameNotFoundException {
    return getDelegatePackageManager().installExistingPackage(packageName);
  }

  @Implementation
  protected int installExistingPackageAsUser(String packageName, int userId) throws NameNotFoundException {
    return getDelegatePackageManager().installExistingPackageAsUser(packageName, userId);
  }

  @Implementation
  protected void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
    getDelegatePackageManager().extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
  }

  @Implementation
  protected void verifyIntentFilter(int id, int verificationCode, List<String> failedDomains) {
    getDelegatePackageManager().verifyIntentFilter(id, verificationCode, failedDomains);
  }

  @Implementation
  protected int getIntentVerificationStatusAsUser(String packageName, int userId) {
    return getDelegatePackageManager().getIntentVerificationStatusAsUser(packageName, userId);
  }

  @Implementation
  protected boolean updateIntentVerificationStatusAsUser(String packageName, int status, int userId) {
    return getDelegatePackageManager().updateIntentVerificationStatusAsUser(packageName, status, userId);
  }

  @Implementation
  protected List<IntentFilterVerificationInfo> getIntentFilterVerifications(String packageName) {
    return getDelegatePackageManager().getIntentFilterVerifications(packageName);
  }

  @Implementation
  protected List<IntentFilter> getAllIntentFilters(String packageName) {
    return getDelegatePackageManager().getAllIntentFilters(packageName);
  }

  @Implementation
  protected String getDefaultBrowserPackageNameAsUser(int userId) {
    return getDelegatePackageManager().getDefaultBrowserPackageNameAsUser(userId);
  }

  @Implementation
  protected boolean setDefaultBrowserPackageNameAsUser(String packageName, int userId) {
    return getDelegatePackageManager().setDefaultBrowserPackageNameAsUser(packageName, userId);
  }

  @Implementation
  protected int getMoveStatus(int moveId) {
    return getDelegatePackageManager().getMoveStatus(moveId);
  }

  @Implementation
  protected void registerMoveCallback(Object callback, Object handler) {
    getDelegatePackageManager().registerMoveCallback((MoveCallback) callback, (Handler) handler);
  }

  @Implementation
  protected void unregisterMoveCallback(Object callback) {
    getDelegatePackageManager().unregisterMoveCallback((MoveCallback) callback);
  }

  @Implementation
  protected Object movePackage(Object packageName, Object vol) {
    return getDelegatePackageManager().movePackage((String) packageName, (VolumeInfo) vol);
  }

  @Implementation
  protected Object getPackageCurrentVolume(Object app) {
    return getDelegatePackageManager().getPackageCurrentVolume((ApplicationInfo) app);
  }

  @Implementation
  protected List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo app) {
    return getDelegatePackageManager().getPackageCandidateVolumes(app);
  }

  @Implementation
  protected Object movePrimaryStorage(Object vol) {
    return getDelegatePackageManager().movePrimaryStorage((VolumeInfo) vol);
  }

  @Implementation
  protected @Nullable Object getPrimaryStorageCurrentVolume() {
    return getDelegatePackageManager().getPrimaryStorageCurrentVolume();
  }

  @Implementation
  protected @NonNull List<VolumeInfo> getPrimaryStorageCandidateVolumes() {
    return getDelegatePackageManager().getPrimaryStorageCandidateVolumes();
  }

  @Implementation
  protected void deletePackageAsUser(String packageName, IPackageDeleteObserver observer, int flags, int userId) {
    getDelegatePackageManager().deletePackageAsUser(packageName, observer, flags, userId);
  }

  @Implementation
  protected void clearApplicationUserData(String packageName, IPackageDataObserver observer) {
    getDelegatePackageManager().clearApplicationUserData(packageName, observer);
  }

  @Implementation
  protected void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) {
    getDelegatePackageManager().deleteApplicationCacheFiles(packageName, observer);
  }

  @Implementation
  protected void deleteApplicationCacheFilesAsUser(String packageName, int userId, IPackageDataObserver observer) {
    getDelegatePackageManager().deleteApplicationCacheFilesAsUser(packageName, userId, observer);
  }

  @Implementation
  protected void freeStorage(String volumeUuid, long freeStorageSize, IntentSender pi) {
    getDelegatePackageManager().freeStorage(volumeUuid, freeStorageSize, pi);
  }

  @Implementation
  protected String[] setPackagesSuspendedAsUser(String[] packageNames, boolean suspended, int userId) {
    return getDelegatePackageManager().setPackagesSuspendedAsUser(packageNames, suspended, userId);
  }

  @Implementation
  protected boolean isPackageSuspendedForUser(String packageName, int userId) {
    return getDelegatePackageManager().isPackageSuspendedForUser(packageName, userId);
  }

  @Implementation
  protected void addPackageToPreferred(String packageName) {
    getDelegatePackageManager().addPackageToPreferred(packageName);
  }

  @Implementation
  protected void removePackageFromPreferred(String packageName) {
    getDelegatePackageManager().removePackageFromPreferred(packageName);
  }

  @Implementation
  protected List<PackageInfo> getPreferredPackages(int flags) {
    return getDelegatePackageManager().getPreferredPackages(flags);
  }

  public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    getDelegatePackageManager().addPreferredActivity(filter, match, set, activity);
  }

  public void addPreferredActivityAsUser(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
    getDelegatePackageManager().addPreferredActivityAsUser(filter, match, set, activity, userId);
  }

  @Implementation
  protected void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    getDelegatePackageManager().replacePreferredActivity(filter, match, set, activity);
  }

  public void replacePreferredActivityAsUser(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
    getDelegatePackageManager().replacePreferredActivityAsUser(filter, match, set, activity, userId);
  }

  @Implementation
  protected void clearPackagePreferredActivities(String packageName) {
    getDelegatePackageManager().clearPackagePreferredActivities(packageName);
  }

  public int getPreferredActivities(List<IntentFilter> outFilters,
      List<ComponentName> outActivities, String packageName) {
    return getDelegatePackageManager().getPreferredActivities(outFilters, outActivities, packageName);
  }

  @Implementation
  protected ComponentName getHomeActivities(List<ResolveInfo> outActivities) {
    return getDelegatePackageManager().getHomeActivities(outActivities);
  }

  @Implementation
  protected void flushPackageRestrictionsAsUser(int userId) {
    getDelegatePackageManager().flushPackageRestrictionsAsUser(userId);
  }

  @Implementation
  protected boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, UserHandle user) {
    return getDelegatePackageManager().setApplicationHiddenSettingAsUser(packageName, hidden, user);
  }

  @Implementation
  protected boolean getApplicationHiddenSettingAsUser(String packageName, UserHandle user) {
    return getDelegatePackageManager().getApplicationHiddenSettingAsUser(packageName, user);
  }

  @Implementation
  protected Object getKeySetByAlias(String packageName, String alias) {
    return getDelegatePackageManager().getKeySetByAlias(packageName, alias);
  }

  @Implementation
  protected Object getSigningKeySet(String packageName) {
    return getDelegatePackageManager().getSigningKeySet(packageName);
  }

  @Implementation
  protected boolean isSignedBy(String packageName, Object ks) {
    return getDelegatePackageManager().isSignedBy(packageName, (KeySet) ks);
  }

  @Implementation
  protected boolean isSignedByExactly(String packageName, Object ks) {
    return getDelegatePackageManager().isSignedByExactly(packageName, (KeySet) ks);
  }

  @Implementation
  protected VerifierDeviceIdentity getVerifierDeviceIdentity() {
    return getDelegatePackageManager().getVerifierDeviceIdentity();
  }

  @Implementation
  protected boolean isUpgrade() {
    return getDelegatePackageManager().isUpgrade();
  }

  @Implementation
  protected boolean isPackageAvailable(String packageName) {
    return getDelegatePackageManager().isPackageAvailable(packageName);
  }

  @Implementation
  protected void addCrossProfileIntentFilter(IntentFilter filter, int sourceUserId, int targetUserId, int flags) {
    getDelegatePackageManager().addCrossProfileIntentFilter(filter, sourceUserId, targetUserId, flags);
  }

  @Implementation
  protected void clearCrossProfileIntentFilters(int sourceUserId) {
    getDelegatePackageManager().clearCrossProfileIntentFilters(sourceUserId);
  }

  @Implementation
  protected Drawable loadItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo) {
    return getDelegatePackageManager().loadItemIcon(itemInfo, appInfo);
  }

  @Implementation
  protected Drawable loadUnbadgedItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo) {
    return getDelegatePackageManager().loadUnbadgedItemIcon(itemInfo, appInfo);
  }
}
