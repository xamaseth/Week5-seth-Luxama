Week 5 Seth Luxama PIERRE LOUIS
---
This project showcases dynamic flashcards with database persistence, navigation between cards, and interactive user feedback for answers.

🚀 Features
---
Flashcard Quiz System

Displays a question with three possible answers.

Provides instant feedback on selection (correct ✅ / incorrect ❌).

Highlights the correct answer when a wrong one is chosen.

Card Management

Add new flashcards via AddCardActivity.

Delete the current flashcard directly from the main screen.

Edit functionality integrated for updating cards (UI present).

Persistent storage with a local FlashcardDatabase.

Navigation & History

Next ➡️ → Loads a random card.

Back ⬅️ → Returns to the previously viewed card (tracked with history).

Prevents repeat cycles by maintaining navigation state.

UI Interactions

Tap the question text to reset answer states.

Toggle extra layout visibility with a button (toggleBtn).

Buttons dynamically hide/show when no flashcards exist.

Answer Shuffling

Each flashcard’s answers are randomized for every load, ensuring a fresh challenge.
---
🛠️ Tech Stack

Language: Kotlin

Framework: Android SDK

Database: Custom FlashcardDatabase (SQLite wrapper)

UI Components: LinearLayout, RelativeLayout, TextView, ImageView

Navigation: Intent + Activity lifecycle (onResume)

Extensions: AndroidX (enableEdgeToEdge, ViewCompat, WindowInsetsCompat)
---
🎮 How It Works

Launch App → Loads existing flashcards (random card if available).

Answer Question → Tap one option; feedback shows instantly.

Reset Answers → Tap the question text to reset options.

Navigate Cards →

Next → Random flashcard.

Back → Previously viewed flashcard.

Add Card → ➕ button opens AddCardActivity.

Delete Card → 🗑️ button removes current flashcard.

Empty State → If no cards exist, app prompts user to add one.

---
Demo.

![Week5 Seth Luxama](https://github.com/user-attachments/assets/b34fffb2-abd8-4294-bc4d-303299ca7282)
