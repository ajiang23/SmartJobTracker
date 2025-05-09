# SmartJobTracker

![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)  
![License](https://img.shields.io/badge/license-MIT-blue.svg)

---

## 🚀 What is SmartJobTracker?

A lightweight Java Swing application that helps students and professionals stay on top of their job search by:

- **Adding**, **updating**, and **deleting** job applications  
- **Filtering** and **visualizing** your application pipeline (pie chart)  
- **Saving** & **loading** sessions to/from JSON (`data/jobApplication.json`)  
- **Capturing** full job postings for offline review & interview prep (cached automatically)  

---

## ✨ New in v1.2: Job Posting Capture

> **Capture and view the full job description** right inside the app—no more hunting through your browser history!  
>
> - Automatically fetches the posting when you add a new application  
> - Caches it for **offline** access and **interview preparation**  
> - “View Job Posting” button opens a resizable popup panel  

---

## 📋 User Stories

1. **Add** a new job application  
2. **View** and **filter** all applications  
3. **Update** an application’s status  
4. **Delete** an application  
5. **Save** & **load** application data (JSON)  
6. **Visualize** status breakdown via pie chart  
7. **Fetch** and **cache** full job postings  

---

## 🖥️ Getting Started

### 1. Clone repo

```bash
git clone https://github.com/YourUsername/SmartJobTracker.git
cd SmartJobTracker
```

### 2. Prerequisites
- Java 11+ (Temurin/AdoptOpenJDK or Oracle)

### 3. Build & Run
If you’re not using Maven/Gradle, use the provided launcher:

```bash
# from project root
chmod +x run.command
./run.command
```

Otherwise, with Maven:
```bash
mvn clean package        # builds SmartJobTracker-1.2.0.jar in target/
java -jar target/SmartJobTracker-1.2.0.jar
```

Or with Gradle: 
```bash
gradle clean shadowJar   # builds SmartJobTracker-all.jar in build/libs/
java -jar build/libs/SmartJobTracker-all.jar
```

## 📖 Usage
1. Add Job → click Add Job → fill form → OK
2. View Status Breakdown → Menu → View Status Breakdown
3. Save → Menu → Save Applications
4. Load → Menu → Load Applications
5. View Job Posting → select entry → View Job Posting → popup with full description

## 🛠️ Packaging & Distribution
1. Ensure your Maven/Gradle config includes FlatLaf, Jsoup, and JFreeChart dependencies.
2. Build the “uber‑jar” with mvn package or gradle shadowJar.
3. (Optional) Create a native installer with jpackage:
```bash
jpackage \
  --input target/ \
  --name SmartJobTracker \
  --main-jar SmartJobTracker-1.2.0.jar \
  --icon src/main/resources/icon.png \
  --type dmg   # or exe, rpm, deb
```
4. Distribute the folder containing:
```bash
SmartJobTracker/
├─ SmartJobTracker-fat.jar
├─ run.command
├─ data/
│  └─ jobApplication.json  ← auto‑created if missing
└─ README.md
```

## 🏗️ Future Roadmap
- AI-powered Interview Prep: generate practice questions from cached postings & uploaded resume
- Advanced Tags & Filters: multi‑criteria search (role, location, tech stack)
- Export & Sharing: PDF/CSV export of your pipeline
- Cross-Platform Installers: .exe, .dmg, .AppImage via jpackage

## 📜 License
```bash
MIT License

Copyright (c) 2025 Alicia Jiang

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the “Software”), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:  

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.  

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.
```

<sub>Built with ❤️ by Alicia Jiang</sub>