# BookBridge Demo

This repository is a **portable presentation build** of the original [BookBridge](https://github.com/hridhaan-s/BookBridge) project.

The UI is replicated from the original Java Swing application, including the same main screens, sizing, labels, buttons, colors, and basic workflow. The only deliberate change is the backend: this demo keeps books in memory instead of requiring MySQL/JDBC.

## What is replicated

### Login
- Window title: `BookBridge - Login`
- Size: `400 x 300`
- `Username:` field
- `Password:` field
- Blue `Login` button
- Demo credentials: `admin / admin123`

### Dashboard
- Window title: `BookBridge - Dashboard`
- Size: `450 x 350`
- `Welcome back, Admin!`
- `Total Books Registered: ...`
- `Donate Book`
- `Browse Books`
- `Exit Application`

### Book Management
- Window title: `BookBridge - Book Management`
- Size: `850 x 600`
- `Donate a Book` form
- Fields: Book Title, Author, Subject, Class, Condition, Donor Name
- `Donate`
- `Back to Dashboard`
- Table columns: ID, Title, Author, Subject, Class, Condition, Donor, Status
- `Refresh Table`
- `Request Selected Book`

These match the structure and visible UI of the original frames. fileciteturn48file0 fileciteturn46file0 fileciteturn45file0

## Requirements

- Windows
- **JDK 17 or newer**

Check Java from CMD:

```cmd
java -version
javac -version
```

## Fast setup — around 2 minutes

### Option 1: Command Prompt

Open CMD in this folder and run:

```cmd
javac -d out BookBridgeDemo.java
java -cp out BookBridgeDemo
```

### Option 2: One-click launcher

Double-click:

```text
RUN.bat
```

The launcher compiles the demo and starts it.

## Demo login

```text
Username: admin
Password: admin123
```

## Demo flow

1. Log in.
2. Click **Donate Book** or **Browse Books**.
3. Use the donation form to add a book.
4. Click **Refresh Table** to see the book.
5. Select an available book and click **Request Selected Book**.
6. The status changes to `Reserved`.
7. Click **Back to Dashboard**.

## Why there is no MySQL here

The original BookBridge project uses Java Swing + JDBC + MySQL. The original repository requires a database and machine-specific configuration. fileciteturn47file0

This demo is intentionally different **only at the persistence layer** so it can run on another PC without installing MySQL or the MySQL Connector/J.

The original project remains separate and unchanged:

https://github.com/hridhaan-s/BookBridge

## Using it in NetBeans

The complete demo is contained in one Java file:

```text
BookBridgeDemo.java
```

Create a normal Java project in NetBeans, add this file, and run `BookBridgeDemo`.
