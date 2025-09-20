# 📱 FlashCard (Week 4 Project -Seth Luxama)

An interactive Android quiz application built with **Kotlin**. This project demonstrates handling multiple-choice questions, dynamic updates between activities, and user interaction with feedback for correct and incorrect answers.

---

## 🚀 Features

- **Interactive Quiz System**
  - Displays a question and three possible answers.
  - Provides instant feedback on user selection (correct ✅ / incorrect ❌).
  - Highlights the correct answer if the wrong option is chosen.

- **Dynamic Question Updates**
  - Add or update quiz questions through a dedicated `AddCardActivity`.
  - Updates propagate back to the main screen using `ActivityResultContracts`.

- **UI Interactions**
  - Toggle visibility of answer options.
  - Reset answer states with a simple tap on the question.

- **Clean & Reusable Functions**
  - `toggleLayout()` → Show/Hide quiz options.
  - `checkAnswer()` → Validate answers and display feedback.
  - `netwaye()` → Reset answer states.
  - `openAddCard()` → Navigate to the add/update screen.

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **Framework:** Android SDK  
- **UI Components:** `RelativeLayout`, `LinearLayout`, `TextView`, `ImageView`  
- **Navigation:** `Intent` + `ActivityResultContracts`  
- **Extensions:** AndroidX (`enableEdgeToEdge`, `ViewCompat`, `WindowInsetsCompat`)

---



## 🎮 How It Works

1. **Launch App** → Displays a default question with three answers.  
2. **Select an Answer** → Feedback is shown (correct/incorrect).  
3. **Tap Question** → Resets answer states for a fresh attempt.  
4. **Add/Update Question** → Tap ➕ button to edit question and answers.  
5. **Save Changes** → Returns to main screen with updated question/answers.  

---
Gif 
---
![Seth Luxama Week4](https://github.com/user-attachments/assets/6952d489-ee7e-4781-92fb-ff21e8fcdc13)
