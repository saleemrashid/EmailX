# EmailX

Gradle project for building the AOSP Email and Exchange2 applications, with the
package names `com.android.emailx` and `com.android.exchangex` respectively, to
avoid interference with existing installations.

Includes patches for privacy improvements, bug fixes, and new features:

 * Fixed various compilation and compatibility issues

 * Changed primary color to blue, to distinguish it from the official AOSP
   application

 * Removed device-specific identifiers and dropped `READ_PHONE_STATE`
   permission

 * Removed Device Administration implementation for Exchange servers

 * Implemented support for multiple OAuth providers

## Gmail Support

In order to use Google Accounts (e.g. Gmail and G Suite), without enabling the
"Allow less secure apps" option, you must include an OAuth2 provider definition
in `Email/Email/res/xml/oauth.xml`.

OAuth2 provider definitions for a wide variety of services can be found by
extracting `res/xml/oauth.xml` from a recent copy of the Gmail APK. This has
the advantage of classifying the client as an official Google application,
rather than a third-party OAuth2 application.

## Building

1. Download the repositories from AOSP

```bash
./gradlew updateSubmodules
```

2. Apply the included patches. This step is required for the build to succeed

```bash
./gradlew applyPatches
```

3. Build the applications

```bash
./gradlew build
```

4. Install the application on a connected device. This requires you to
   [configure Gradle for app signing](https://developer.android.com/studio/publish/app-signing)

```bash
./gradlew installRelease
```

## Usage

Since the application is intended to be shipped as a system application, it
does not handle runtime permissions correctly. As such, before first use, both
the "Email" app and the "Exchange Services" app should be granted all
permissions via the Settings app.
