# GraduationNotes

Application desktop de gestion de notes scolaires developpee en Java (Swing + SQLite).
Concue pour un contexte BTS SIO SLAM -- elle supporte trois roles utilisateurs : **Administrateur**, **Enseignant** et **Etudiant**.

> **Avertissement** : cette application contient des vulnerabilites de securite intentionnelles a des fins pedagogiques (audit OWASP Top 10). Ne pas deployer en production.

---

## Fonctionnalites

### Administrateur
- Gestion complete des utilisateurs (creation, modification, suppression)
- Gestion des matieres (CRUD)
- Consultation et gestion de toutes les notes

### Enseignant
- Consultation des notes de tous les etudiants
- Saisie de nouvelles notes (valeur, coefficient, commentaire)
- Consultation de la liste des etudiants et des matieres

### Etudiant
- Consultation de ses propres notes par matiere
- Visualisation des coefficients et moyennes ponderees

---

## Stack technique

| Composant | Technologie |
|-----------|-------------|
| Langage | Java (JDK 8+) |
| Interface graphique | Java Swing |
| Base de donnees | SQLite (fichier `notes.db`) |
| Driver JDBC | sqlite-jdbc 3.36.0.3 |
| IDE cible | Eclipse IDE |

---

## Prerequis

- JDK 8 ou superieur installe
- Eclipse IDE (ou tout autre IDE Java)
- Aucune installation de base de donnees requise (SQLite est embarque)

---

## Installation et lancement

1. **Cloner le depot**
   ```bash
   git clone https://github.com/<votre-compte>/GraduationNotes.git
   ```

2. **Importer dans Eclipse**
   - `File > Import > Existing Projects into Workspace`
   - Selectionner le dossier racine du projet

3. **Verifier le classpath**
   Le fichier `lib/sqlite-jdbc-3.36.0.3.jar` doit etre present dans le Build Path.
   Clic droit sur le projet -> `Build Path > Configure Build Path > Add JARs`

4. **Lancer l'application**
   Executer `src/Main.java` (classe principale avec `main()`)

La base de donnees `notes.db` est creee et peuplee automatiquement au premier demarrage via `DatabaseInitializer`.

---

## Identifiants par defaut

| Role | Nom d'utilisateur | Mot de passe |
|------|-------------------|--------------|
| Administrateur | `admin` | `admin123` |
| Enseignant | `teacher1` | `teacher123` |
| Etudiant | `student1` | `student123` |

---

## Structure du projet

```
GraduationNotes-main/
├── src/
│   ├── Main.java                        # Point d'entree
│   ├── dao/
│   │   ├── DatabaseConnection.java      # Connexion SQLite
│   │   ├── UserDAO.java                 # Acces donnees utilisateurs
│   │   ├── GradeDAO.java                # Acces donnees notes
│   │   └── SubjectDAO.java              # Acces donnees matieres
│   ├── model/
│   │   ├── User.java                    # Entite de base
│   │   ├── Student.java                 # Etudiant (herite User)
│   │   ├── Teacher.java                 # Enseignant (herite User)
│   │   ├── Subject.java                 # Matiere
│   │   └── Grade.java                   # Note
│   ├── security/
│   │   └── AuthenticationService.java   # Authentification
│   ├── ui/
│   │   ├── LoginFrame.java              # Fenetre de connexion
│   │   ├── AdminDashboard.java          # Interface administrateur
│   │   ├── TeacherDashboard.java        # Interface enseignant
│   │   └── StudentDashboard.java        # Interface etudiant
│   └── utils/
│       └── DatabaseInitializer.java     # Initialisation et seed BDD
├── lib/
│   └── sqlite-jdbc-3.36.0.3.jar
├── bin/                                 # Bytecode compile (Eclipse)
└── notes.db                             # Base de donnees SQLite (generee)
```

---

## Modele de donnees

```
User (id, username, password, role, fullName, email)
 ├── Student (classGroup, grades[])
 └── Teacher (taughtSubjects[])

Subject (id, code, name, description, defaultCoefficient)

Grade (id, value, coefficient, title, comment, date, studentId->User, subject->Subject)

teacher_subjects (teacher_id, subject_id)  <- table de jonction
```

---

## Contexte pedagogique -- TP Securite (BTS SIO SLAM)

Cette application est le support d'un TP d'audit de securite de 4h.
Elle contient des vulnerabilites intentionnelles couvrant plusieurs categories OWASP Top 10 :

- Injection SQL (requetes par concatenation de chaines)
- Mots de passe stockes en clair
- Controle d'acces insuffisant (IDOR)
- Journalisation de donnees sensibles
- Absence de protection contre le brute-force

### Deroulé du TP

**Etape 1 -- Decouverte (1h)**
Lancer l'application, explorer les fonctionnalites, identifier les comportements suspects.

**Etape 2 -- Audit (1h30)**
Lire les parties critiques du code source, identifier les failles, evaluer leur criticite (faible / moyen / eleve), les referencer avec l'OWASP Top 10.

Livrable : rapport d'audit (liste des failles, criticite, pistes de correction, references OWASP, consequences potentielles).

**Etape 3 -- Corrections (1h30)**
Choisir 2 a 3 failles critiques, les corriger proprement, tester, documenter avant/apres.

Livrable : rapport d'intervention (contexte, actions techniques, preuves de correction, retour d'experience, suggestions).

**Dossier a remettre** : projet modifie + rapport d'audit + rapport d'intervention (PDF ou `.md`).

---
