# 🪄 Quick Git Branch Guide

## 1. Create a new branch
```bash
git checkout -b <branch-name>
```
Example:
```bash
git checkout -b feature/login-page
```

---

## 2. Make and stage changes
```bash
git add .
```

Or stage specific files:
```bash
git add path/to/file
```

---

## 3. Commit your changes
```bash
git commit -m "Describe your changes"
```

---

## 4. Push your new branch to remote
```bash
git push -u origin <branch-name>
```

Example:
```bash
git push -u origin feature/login-page
```

---

## 5. Optional commands
List branches:
```bash
git branch
```

Switch branches:
```bash
git checkout main
```

Delete a local branch (after merge):
```bash
git branch -d <branch-name>
```

---

### 💡 Tip
After setting the upstream with `-u`, you can just run:
```bash
git push
```
next time.
