# BookBridge Demo

A tiny, portable presentation build of BookBridge.

This repository is intentionally self-contained. It does **not** require MySQL, NetBeans, Maven, or any external Java libraries.

## 2-minute setup (Windows)

1. Install **JDK 17 or newer** if Java is not already installed.
2. Download or clone this repository.
3. Double-click `RUN.bat`.

That's it.

The launcher compiles `BookBridgeDemo.java` and starts the application.

### Demo login

- Username: `admin`
- Password: `admin123`

## Command-line version

Open CMD inside this folder:

```bat
javac -d out BookBridgeDemo.java
java -cp out BookBridgeDemo
```

## What this is

This is a **presentation/demo build**, not a replacement for the original BookBridge project. It keeps the main idea and visible workflows while removing machine-specific setup requirements so the application can be demonstrated on another Windows PC quickly.
