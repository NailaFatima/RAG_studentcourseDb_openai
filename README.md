# 🧠 RAG Student Course DB

A **Retrieval-Augmented Generation (RAG)** web application that allows users to ask **natural language questions** about student and course data.  
The system intelligently converts questions into SQL queries, executes them on a MySQL database, and displays the results as an interactive HTML table — powered by **Spring Boot**, **ReactJS**, and **LLM integration (OpenAI or Ollama)**.

---

## 🚀 Overview

This project demonstrates a practical application of **AI-assisted database querying** using the **RAG approach**:

1. **User** asks a question (e.g., *"List all students enrolled in Database Systems"*).  
2. **LLM** (OpenAI GPT / Ollama local model) generates a valid SQL `SELECT` query.  
3. The **Spring Boot backend** executes the query on the MySQL database.  
4. **Results** are returned to the **ReactJS frontend** and displayed in a professional, minimal UI.

---

## 🧩 Architecture

```
 ┌────────────────────┐
 │     ReactJS UI     │
 │ (Tailwind Styling) │
 └─────────┬──────────┘
           │ (GET /chat/ask?question=...)
 ┌─────────▼──────────┐
 │ Spring Boot Server │
 │  - REST Controller │
 │  - Service Layer   │
 └─────────┬──────────┘
           │
 ┌─────────▼──────────┐
 │ LLM (OpenAI/Ollama)│
 │  Generates SQL      │
 └─────────┬──────────┘
           │
 ┌─────────▼──────────┐
 │   MySQL Database   │
 │ student_course_db  │
 └────────────────────┘
```

---

## 🧠 Core Components

### 🧾 1. **Backend (Spring Boot)**

**Tech Stack:**
- Java 17+
- Spring Boot 3.x
- Spring AI (OpenAI / Ollama)
- Spring JDBC
- MySQL

**Main Files:**
- `StudentCourseController.java` → REST endpoint `/chat/ask`
- `StudentCourseResource.java` → Generates SQL with LLM, executes query, returns HTML
- `application.properties` → Configures model, DB, and API keys

**Endpoints:**
```
GET /chat/ask?question=Show all students enrolled in Database Systems
```

**Response:**  
An HTML `<table>` displaying the SQL query result.

---

### 💬 2. **Frontend (React + Tailwind)**

**Tech Stack:**
- ReactJS (Vite)
- Tailwind CSS
- Axios for API calls

**Features:**
- Minimal and professional UI
- Input box for questions
- Loading indicator while querying
- Table rendered from backend HTML
- Responsive layout with Tailwind styling

**Design Colors:**
| Role | Color | Tailwind Class |
|------|--------|----------------|
| Primary | `#2563EB` | `blue-600` |
| Accent | `#FACC15` | `yellow-400` |
| Background | `#F9FAFB` | `gray-50` |
| Text | `#1E293B` | `slate-800` |

---

### 🗄️ 3. **Database Schema**

**Database:** `student_course_db`

**Tables:**
```sql
Students(student_id, first_name, last_name, email, date_of_birth)
Courses(course_id, course_code, course_name, course_description, credits)
Enrollments(enrollment_id, student_id, course_id, enrollment_date)
```

---

## ⚙️ Setup Instructions

### 1️⃣ Backend Setup

#### **Clone the repository**
```bash
git clone https://github.com/yourusername/RAG_studentcourseDb.git
cd RAG_studentcourseDb
```

#### **Configure `application.properties`**
```properties
spring.application.name=RAG_studentcourseDb

# Choose ONE of the following integrations

# --- OpenAI Integration ---
spring.ai.openai.api-key=sk-your-key
spring.ai.openai.chat.options.model=gpt-4o-mini

# --- OR Local Ollama Integration ---
# spring.ai.ollama.chat.options.model=llama3

# --- Database ---
spring.datasource.url=jdbc:mysql://localhost:3306/student_course_db
spring.datasource.username=root
spring.datasource.password=root
```

#### **Run Spring Boot**
```bash
mvn spring-boot:run
```

The backend will start on **`http://localhost:8080`**

---

### 2️⃣ Frontend Setup

#### **Create React app (if not already present)**
```bash
npm create vite@latest rag-frontend -- --template react
cd rag-frontend
npm install
npm install axios tailwindcss
npx tailwindcss init -p
```

#### **Add Tailwind setup**
In `tailwind.config.js`:
```js
content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
theme: { extend: {} },
plugins: [],
```

In `index.css`:
```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

#### **Run frontend**
```bash
npm run dev
```

Frontend runs on **`http://localhost:5173`**

---

## 🧪 Example Query

**Input:**  
> “List all students enrolled in the Database Systems course.”

**LLM Output SQL:**  
```sql
SELECT s.first_name, s.last_name 
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON c.course_id = e.course_id
WHERE c.course_name = 'Database Systems';
```

**Frontend Display:**  
A styled HTML table listing the results.

---

## ⚠️ Common Issues

| Issue | Cause | Solution |
|--------|--------|-----------|
| `429 insufficient_quota` | OpenAI account exceeded free limit | Add billing or switch to Ollama |
| `Model not found` | Wrong model name | Use `gpt-4o-mini` or `llama3` |
| `Empty results` | No matching rows | Verify database data |
| `CORS error` | Frontend blocked API call | Add `@CrossOrigin("*")` on the controller |

---

## 🧰 Enhancements Roadmap
- [ ] Add context-aware memory (previous questions)
- [ ] Add SQL validation / safety checks
- [ ] Display generated SQL to user
- [ ] Support for UPDATE/DELETE with approval
- [ ] Dockerize for full deployment

---

## 🧑‍💻 Author

**Naila Fatima**  
Full-stack Developer | AI-integrated Web Apps | React & Spring Boot Specialist  

📧 *naila.fatima@example.com*  
🔗 *[LinkedIn](#)* | *[GitHub](#)*

---

## 🪶 License
This project is licensed under the **MIT License** — feel free to use and modify it for learning or development.
