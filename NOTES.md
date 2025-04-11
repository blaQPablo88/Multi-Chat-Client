# 🧠 Research Summary: Building a Multi-Client Chat App in Java

## ✨ Objective
To learn and implement Java socket programming and multithreading in preparation for developing a **networked multiplayer game**.

---

## 🚀 Why This Project?
In an upcoming project, we’ll be building a **multiplayer game over a network**. To handle multiple connected players simultaneously, we need to understand:
- Multi-socket communication (handling multiple clients)
- Multithreading (running parallel sessions independently)

---

## 📚 Learning Journey & Resources

### 🔗 YouTube Videos Studied

1. **[Java Sockets Tutorial – Server & Client Example](https://youtu.be/BqBKEXLqdvI?feature=shared)**
    - **Focus:** Core concepts of Java socket programming.
    - **Key Learnings:**
        - How to use `ServerSocket` and `Socket`
        - Basic client-server communication
        - Writing and reading data through sockets

2**[BufferedReader & BufferedWriter in Java](https://youtu.be/eQk5AWcTS8w?feature=shared)**
    - **Focus:** Efficient reading/writing of data through streams.
    - **Key Learnings:**
        - Use of `BufferedReader` and `BufferedWriter`
        - Flushing output streams properly
        - Handling user input with `Scanner`

3**[How to Make a Simple Chat App in Java](https://youtu.be/gLfuZrrfKes?feature=shared)**
    - **Focus:** Building both client and server from scratch.
    - **Key Learnings:**
        - End-to-end structure of a chat system
        - Managing connections and I/O
        - Clean shutdowns and resource management

---

## 💻 Project Breakdown

### ✅ Server Class
- Accepts incoming client connections
- Starts a new thread for each connection

### ✅ ClientHandler Class
- Dedicated to managing communication for one client
- Broadcasts messages to all other clients
- Removes disconnected clients

### ✅ Client Class
- Connects to server via `Socket`
- Listens for messages in a separate thread
- Sends user input messages to server

---

## 🛠️ Skills Gained
- Java Sockets
- Multithreading with `Runnable` and `Thread`
- Stream handling with `BufferedReader` and `BufferedWriter`
- Clean resource management
- Group chat logic that can evolve into multiplayer communication logic

---

## 🎮 Next Steps
Use this knowledge as a foundation to:
- Handle real-time player interactions in a multiplayer game
- Send game state data (not just chat text) across the network
- Implement game logic sync and data handling for each connected client

---