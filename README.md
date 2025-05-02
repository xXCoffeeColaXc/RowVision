# RowVision

RowVision is a sports-training companion app for indoor rower (ergometer) users. It provides stroke-phase analysis, live metrics, session history, and configurable workout settings — all wrapped in a modern, polished Jetpack Compose UI.

---

## 🚀 Features

- **Live Stroke Feedback**  
  Countdown, stroke counter, pacing, and motivational captions during your session (demo video / dummy data).
- **Session History**  
  Scrollable list of past workouts, complete with date, duration, distance, stroke rate, and average pace.
- **Stroke-Phase Analysis**  
  Interactive donut chart breaking down your stroke into Catch → Drive → Finish → Recovery phases, with “clean strokes” count.
- **Configurable Workouts**  
  Pick your plan, intervals, goals, and notifications before you start.
- **Profile & Settings**  
  View achievements, training preferences, alerts, appearance, privacy, and more.

---

## 📱 Screens

| Screen       | Description                                          |
| ------------ | ---------------------------------------------------- |
| **Login**    | Background video, email + password fields, “Login”.  |
| **Home**     | Big pulsing START button, quick Settings & Sessions. |
| **Pre-Start**| Tilt guide & countdown before session begins.        |
| **Video**    | Full-screen demo video, live timer, stroke counter.  |
| **Sessions** | List of past sessions in ElevatedCards.              |
| **Analysis** | Stroke-phase donut chart & legend.                   |
| **Profile**  | Avatar, user name, nav to achievements & prefs.      |

---

## 🏗 Architecture

- **Jetpack Compose** for everything  
- **MVVM** pattern: one ViewModel per screen  
- **Hilt** for dependency injection  
- **Navigation-Compose** for in-app routing  
- **ExoPlayer** for bundled video playback  
- **Sensors** (rotation vector) for tilt detection  

---

## 🎨 Theme & Design

- **Primary background**: Deep Navy (`#001F3F`)  
- **Accent**: Aqua-Turquoise (`#40E0D0`)  
- **Surface**: White & Light-Gray (`#F2F2F2`)  
- **Alerts**: Orange (`#FFA500`) & Green (`#2ECC40`)  
- **Typography**: Custom Montserrat via `AppTypography`  
- **Material 3** shapes, tonal elevations, and motion  

---

## 💻 Setup & Build

1. **Clone** the repo  
   ```bash
   git clone https://github.com/your-org/rowvision.git
   cd rowvision
