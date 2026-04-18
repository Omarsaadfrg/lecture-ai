<div align="center">

<img src="https://img.shields.io/badge/AI-Powered-00FF88?style=for-the-badge&logo=openai&logoColor=black" />
<img src="https://img.shields.io/badge/Platform-Android%20%7C%20iOS-00FF88?style=for-the-badge&logo=flutter&logoColor=black" />
<img src="https://img.shields.io/badge/Status-Active-00FF88?style=for-the-badge" />

# 🎓 AILecture
### *Turn Lectures into Notes — Instantly*

> Upload or record any lecture, and AI will **transcribe**, **summarize**, and **generate questions** — all in seconds.

</div>

---

## 📱 Screenshots

| Login | Home | Processing | Results |
|-------|------|------------|---------|
| Welcome screen with clean dark UI | Upload or Record your lecture | Real-time progress tracking | Full transcript with timestamps |

---

## ✨ Features

- 🎙️ **Record Lectures** — Record directly from your phone in real-time
- 📂 **Upload Audio** — Supports MP3, WAV, M4A · Up to 2 hours
- 📝 **Full Transcript** — Timestamped, word-by-word transcription
- 📋 **AI Summary** — Concise, structured summaries of key points
- ❓ **Auto Questions** — Smart Q&A generated from lecture content
- 💾 **Export Options** — Save as TXT, PDF, or copy to clipboard
- 🕓 **History** — Access all your past lectures anytime

---

## 🚀 How It Works

```
1. 🎤  Upload or Record your lecture audio
        ↓
2. ⬆️  File uploads to the cloud
        ↓
3. 🔄  AI Transcribes the audio (with timestamps)
        ↓
4. 🧠  AI Summarizes the key points
        ↓
5. ❓  AI Generates study questions
        ↓
6. ✅  You get: Transcript + Summary + Questions
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| 📱 Mobile App | Flutter / React Native |
| 🎙️ Transcription | OpenAI Whisper API |
| 🧠 Summarization | GPT-4 / Claude API |
| 🔐 Auth | Firebase Authentication |
| ☁️ Storage | Firebase Storage / AWS S3 |
| ⚙️ Backend | Node.js / FastAPI |

---

## 🎯 Use Cases

- 🎓 **Students** — Never miss a detail from long lectures
- 👩‍🏫 **Teachers** — Convert recorded classes into structured notes
- 💼 **Professionals** — Turn meetings and seminars into actionable summaries
- 🧑‍💻 **Self-learners** — Study from YouTube videos and podcasts

---

## 📊 Processing Pipeline

```
Audio File
    │
    ▼
┌─────────────┐
│  Uploading  │  ← Progress bar + ETA
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Transcribing│  ← Whisper AI
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Summarizing │  ← GPT / Claude
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Generating  │  ← Smart Q&A
└──────┬──────┘
       │
       ▼
  ✅ Results Ready
```

---

## 📦 Getting Started

```bash
# Clone the repo
git clone https://github.com/yourusername/AILecture.git

# Navigate to project
cd AILecture

# Install dependencies
flutter pub get
# or
npm install

# Add your API keys in .env
OPENAI_API_KEY=your_key_here
FIREBASE_CONFIG=your_config_here

# Run the app
flutter run
```

---

## 🔑 Environment Variables

```env
OPENAI_API_KEY=          # For Whisper + GPT
FIREBASE_API_KEY=        # Firebase Auth & Storage
BACKEND_URL=             # Your backend API URL
```

---

## 🗺️ Roadmap

- [x] Audio upload & recording
- [x] Real-time transcription
- [x] AI summarization
- [x] Question generation
- [x] Export to TXT & PDF
- [ ] 🌍 Multi-language support (Arabic, French, Spanish...)
- [ ] 📺 YouTube link input
- [ ] 🔍 Search inside transcripts
- [ ] 👥 Share notes with classmates
- [ ] 🎯 Flashcard generation

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<div align="center">

Made with ❤️ by the AILecture Team

⭐ **Star this repo if you find it useful!** ⭐

</div>
