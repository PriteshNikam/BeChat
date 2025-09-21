# 📱 BeChat - Bluetooth Chat App

<div align="center">

*A modern Bluetooth-based chat application built with Jetpack Compose*

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)

</div>

---

## 📖 Project Overview

**BeChat** is a Bluetooth-based chat application that enables seamless communication between Android devices without requiring an internet connection. The app leverages Android's native Bluetooth capabilities to discover nearby devices, establish secure connections, and facilitate real-time text message exchange between users.

Whether you're in a remote area without network coverage or simply want a private, direct communication channel with nearby friends, BeChat provides a reliable peer-to-peer messaging solution powered by modern Android development practices.

---

## ✅ Features Implemented

### 🔐 **Permissions & Security**
- **Bluetooth Permissions**: Full implementation of `BLUETOOTH_CONNECT` and `BLUETOOTH_SCAN` permissions
- **Location Services**: Integrated location permission handling and location services verification (required for Bluetooth device discovery)
- **Runtime Permission Management**: Smooth permission request flow with user-friendly prompts

### 📡 **Bluetooth Functionality**
- **Bluetooth State Management**: Automatic detection and prompting when Bluetooth is disabled
- **Device Discovery**: Scan and discover nearby Bluetooth-enabled devices
- **Connection Establishment**: Robust server and client role implementation for chat sessions
- **Real-time Messaging**: Instant text message exchange between connected devices

### 🧭 **Navigation & Architecture**
- **Type-safe Navigation**: Modern navigation system using Jetpack Navigation Compose
- **MVVM Pattern**: Clean architecture with ViewModel and StateFlow for reactive UI updates
- **Dependency Injection**: Hilt integration for maintainable and testable code

### 🎨 **Modern UI/UX**
- **Jetpack Compose**: Fully native Compose UI with smooth animations
- **Lottie Animations**: Engaging loading animations and visual feedback
- **Material Design 3**: Contemporary design system implementation
- **Responsive Layout**: Adaptive UI that works across different screen sizes

---

## 🚀 Future Plans

- [ ] **File Sharing**: Enable sharing of images, videos, and documents
- [ ] **Encryption**: Add end-to-end encryption for secure messaging  
- [ ] **Enhanced UI**: Custom chat bubbles and improved themes
- [ ] **Device Management**: Better device list and connection indicators
- [ ] **Group Chat**: Support for multiple device connections

---

## 📷 Media

### 📱 Screenshots
*Screenshots will be added here showcasing the app's interface*

### 🎥 Demo Video
*Demo video will be embedded here showing the app in action*

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Kotlin** | Primary programming language |
| **Jetpack Compose** | Modern UI toolkit |
| **ViewModel + StateFlow** | State management and reactive programming |
| **Hilt** | Dependency injection framework |
| **Android Bluetooth API** | Bluetooth connectivity and communication |
| **Navigation Compose** | Type-safe navigation |
| **Lottie** | Animation library |
| **Coroutines** | Asynchronous programming |
| **Material Design 3** | UI design system |

---

## 📝 How to Run

### Prerequisites
- **Android Studio** (Arctic Fox or newer)
- **Android SDK** (API level 21 or higher)
- **Physical Android device** (Bluetooth functionality requires physical hardware)
- **Bluetooth support** on test devices

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/BeChat.git
   cd BeChat
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned `BeChat` directory
   - Click "OK" to open the project

3. **Sync Dependencies**
   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync process to complete

4. **Build the Project**
   - Select "Build" → "Make Project" or press `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)
   - Ensure there are no build errors

5. **Deploy to Physical Device**
   ```bash
   # Enable Developer Options and USB Debugging on your Android device
   # Connect device via USB cable
   # Run the app
   ```
   - Click "Run" → "Run 'app'" or press `Shift+F10`
   - Select your connected physical device
   - Wait for app installation and launch

6. **Test Bluetooth Chat**
   - **Grant Permissions**: Allow Bluetooth and Location permissions when prompted
   - **Enable Bluetooth**: Turn on Bluetooth if disabled
   - **Test with Two Devices**: Install the app on two different devices for full testing
   - **Start Chat**: One device acts as server, the other connects as client

### 📋 Testing Checklist
- [ ] App installs successfully on physical device
- [ ] Bluetooth permissions are granted
- [ ] Location permissions are granted
- [ ] Bluetooth can be enabled through the app
- [ ] Device discovery works
- [ ] Connection establishment succeeds
- [ ] Messages are sent and received in real-time

---

## 📈 Progress Checklist

- [x] **Bluetooth permissions** (BLUETOOTH_CONNECT, BLUETOOTH_SCAN)
- [x] **Location permission** & location services check  
- [x] **Server/client connection** roles
- [x] **Real-time chat** functionality
- [ ] **File sharing** capabilities
- [ ] **Encryption** for secure messaging
- [ ] **Group chat** support

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/PriteshNikam/BeChat/blob/main/LICENSE) file for details.

---

<div align="center">

**Made with ❤️ and Kotlin**

*Star ⭐ this repository if you find it helpful!*

</div>
