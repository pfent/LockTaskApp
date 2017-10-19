# Minimal working example of a device owner app

The app takes complete control over the device when launched, using the `startLockTask()` API.
The user should not be able to leave the app apart from killing it via adb. However, this only works
for apps, that are marked as device-owners.

To set this app as a device owner, execute:
```
adb shell dpm set-device-owner de.tum.in.fent.locktaskapp/.AdminReceiver
```
