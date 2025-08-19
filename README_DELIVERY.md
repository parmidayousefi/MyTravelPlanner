# MyTravelPlanner — Delivery Pack

This pack prepares a **Debug-friendly** build setup so you can generate an APK quickly.
It uses placeholder keys for Maps/Auth and a stub `google-services.json` — good enough to **compile and install**.
Online features (Google Sign-In, Maps, Firebase Storage) will require your **real keys** later.

## What's included
- `app/google-services.json` — stub file (compile-time only)
- `res/values/strings.xml` — placeholder values for `google_maps_key` and `web_client_id`

## Build (Android Studio GUI)
1. Open the project in **Android Studio** (Giraffe+).
2. Let Gradle sync finish.
3. From menu: **Build > Build Bundle(s)/APK(s) > Build APK(s)**
4. APK appears at `app/build/outputs/apk/debug/app-debug.apk`

## Build (CLI)
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

> If Gradle asks for Android SDK, install the recommended components via Android Studio.

## To enable full features
1. Replace `app/google-services.json` with your real file from Firebase console.
2. Put real values in `app/src/main/res/values/strings.xml`:
   - `<string name="google_maps_key">YOUR_REAL_ANDROID_SDK_KEY</string>`
   - `<string name="web_client_id">YOUR_REAL_WEB_CLIENT_ID.apps.googleusercontent.com</string>`
3. (Optional, if you need cloud DB) Add to `app/build.gradle`:
```gradle
implementation platform("com.google.firebase:firebase-bom:33.1.2")
implementation "com.google.firebase:firebase-firestore"    // or firebase-database
```

## Quick Demo Script (for recording)
1. Launch app → **Login screen** (skip login if not configured).
2. Go to **Trips** tab → tap **FAB** → Add a trip (title, dates, notes).
3. Open **Trip Details** → try **Attach/Upload** (works fully with real Firebase).
4. Back to main → switch to **Todos** tab → add a todo.
5. Show **Bottom Navigation** switching (Trips/Todos/Profile).
6. (If keys ready) Open map in **Add Trip** or **Trip Details**.
7. (If notifications allowed) Set a reminder to trigger an **alarm/notification**.

### Recording (Phone)
- Use built-in Screen Recorder (Android). Record 720p or 1080p, 30fps.
- Demonstrate the script above in 30–60s.

### Recording (Emulator)
- Start Pixel emulator from Android Studio.
- Use the OS/Studio screen recorder to capture the same flow.
```