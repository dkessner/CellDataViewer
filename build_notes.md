

## Initial project setup

Ran `gdx-setup.jar` to create a libgdx project:

```bash
java -jar ~/local/gdx-setup.jar --dir . --name CellDataViewer --package io.github.dkessner --mainClass ProcessingGdxAdapter --sdkLocation $ANDROID_HOME
```

Initial gradle build failed. 
```
Unable to find method 'org.gradle.api.tasks.compile.CompileOptions.setBootClasspath(Ljava/lang/String;)V'
```

Online forums say libgdx needs gradle version 4.6.


Changed gradle wrapper to version 4.6:
```
gradle/wrapper/gradle-wrapper.properties:

...
    distributionUrl=https\://services.gradle.org/distributions/gradle-4.6-bin.zip
...

```

This caused a problem with the Android gradle plugin:
```
Minimum supported Gradle version is 5.1.1. Current version is 4.6. If using the gradle wrapper, try editing the distributionUrl in /Users/darrenkessner/dev/CellDataViewer/gradle/wrapper/gradle-wrapper.properties to gradle-5.1.1-all.zip
```

Fix: use older version of Android gradle plugin:


```
build.gradle:
        classpath 'com.android.tools.build:gradle:3.2.0'
```

