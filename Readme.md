# 🔗 JPA Entity Relationships & Mapping

This project implements three major JPA/Hibernate entity relationships:

```text
Professor 1 ───────── * Subject

Professor * ───────── * Student

Student   1 ───────── 1 Administrator
```

---

## 1. 👨‍🏫 Professor → Subject

### Relationship

```text
One Professor → Many Subjects
```

A professor can teach multiple subjects, while each subject belongs to one professor.

### Professor Entity

```java
@OneToMany(mappedBy = "professor")
private List<Subject> subjects = new ArrayList<>();
```

### Subject Entity

```java
@ManyToOne
@JoinColumn(name = "professor_id")
private Professor professor;
```

### Database

The foreign key is stored in the `subject` table:

```text
subject
-------------------------
id
name
professor_id  ← FK
```

### Mapping Method

The relationship is mapped using the IDs of existing Professor and Subject:

```java
public Professor mapSubject(Long professorId, Long subjectId) {

    Professor professor = professorRepo.findById(professorId)
            .orElseThrow(() ->
                    new EntityNotFoundException("Professor not found"));

    Subject subject = subjectRepo.findById(subjectId)
            .orElseThrow(() ->
                    new EntityNotFoundException("Subject not found"));

    professor.getSubjects().add(subject);
    subject.setProfessor(professor);

    return professorRepo.save(professor);
}
```

The method:

1. Finds the Professor.
2. Finds the Subject.
3. Adds the Subject to the Professor's subject list.
4. Sets the Professor inside the Subject.
5. Saves the Professor.

### API

```http
POST /professors/{professorId}/subjects/{subjectId}
```

Example:

```http
POST http://localhost:8080/professors/1/subjects/2
```

No request body is required.

---

# 2. 👨‍🏫 Professor ↔ Student

## Many-to-Many Relationship

```text
Many Professors ↔ Many Students
```

A Professor can be associated with multiple Students, and a Student can be associated with multiple Professors.

### Professor Entity

Professor is the owning side:

```java
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
        name = "professor_student",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
)
private List<Student> students = new ArrayList<>();
```

### Student Entity

Student is the inverse side:

```java
@ManyToMany(mappedBy = "students")
private List<Professor> professors = new ArrayList<>();
```

### Junction Table

A Many-to-Many relationship requires a third table:

```text
professor_student
-------------------------
professor_id
student_id
```

Example:

```text
professor_id | student_id
-------------|-----------
1            | 2
1            | 3
2            | 2
```

This means:

* Professor 1 is associated with Student 2.
* Professor 1 is associated with Student 3.
* Professor 2 is associated with Student 2.

---

## Mapping Method

```java
public Professor mapStudent(Long professorId, Long studentId) {

    Professor professor = professorRepo.findById(professorId)
            .orElseThrow(() ->
                    new EntityNotFoundException("Professor not found"));

    Student student = studentRepo.findById(studentId)
            .orElseThrow(() ->
                    new EntityNotFoundException("Student not found"));

    professor.getStudents().add(student);
    student.getProfessors().add(professor);

    return professorRepo.save(professor);
}
```

### Mapping Logic

```java
professor.getStudents().add(student);
```

Adds the Student to the Professor's list.

```java
student.getProfessors().add(professor);
```

Adds the Professor to the Student's list.

Both sides are synchronized in Java.

### API

```http
POST /professors/{professorId}/students/{studentId}
```

Example:

```http
POST http://localhost:8080/professors/1/students/2
```

No request body is required.

---

# 3. 🎓 Student ↔ Administrator

## One-to-One Relationship

```text
One Student ↔ One Administrator
```

Each Student has one Administrator record, and each Administrator record belongs to one Student.

### Student Entity

Student is the owning side:

```java
@OneToOne
@JoinColumn(name = "administrator_id")
private Administrator administrator;
```

### Administrator Entity

Administrator is the inverse side:

```java
@OneToOne(mappedBy = "administrator")
private Student student;
```

### Database

The foreign key is maintained by the Student table:

```text
student
-------------------------
id
name
administrator_id  ← FK
```

---

## Mapping Method

```java
public Student mapAdministrator(Long id, Long administratorId) {

    Administrator administrator = administratorRepo.findById(administratorId)
            .orElseThrow(() ->
                    new EntityNotFoundException("Administrator not found"));

    Student student = studentRepo.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException("Student not found"));

    student.setAdministrator(administrator);
    administrator.setStudent(student);

    return studentRepo.save(student);
}
```

### Mapping Logic

```java
student.setAdministrator(administrator);
```

Sets the Administrator inside Student.

```java
administrator.setStudent(student);
```

Synchronizes the reverse side in Java.

The Student is the owning side because it contains `@JoinColumn`.

### API

```http
POST /students/{studentId}/administrators/{administratorId}
```

Example:

```http
POST http://localhost:8080/students/1/administrators/2
```

No request body is required.

---

# 🔑 Owning Side vs Inverse Side

One of the most important concepts learned in this project is the **owning side** of a JPA relationship.

| Relationship            | Owning Side | Inverse Side  |
| ----------------------- | ----------- | ------------- |
| Student ↔ Administrator | Student     | Administrator |
| Professor ↔ Subject     | Subject     | Professor     |
| Professor ↔ Student     | Professor   | Student       |

The owning side is normally the side that contains:

```java
@JoinColumn
```

or:

```java
@JoinTable
```

The inverse side uses:

```java
mappedBy
```

---

# 🔄 Bidirectional Mapping

The project uses bidirectional relationships.

For example:

```text
Professor
   ↓
Students
   ↓
Professors
   ↓
Students
```

Therefore, when mapping relationships, both Java-side references are synchronized.

### One-to-One

```java
student.setAdministrator(administrator);
administrator.setStudent(student);
```

### One-to-Many / Many-to-One

```java
professor.getSubjects().add(subject);
subject.setProfessor(professor);
```

### Many-to-Many

```java
professor.getStudents().add(student);
student.getProfessors().add(professor);
```

---

# ⚡ Lazy Fetching

The Many-to-Many relationship uses:

```java
fetch = FetchType.LAZY
```

Example:

```java
@ManyToMany(fetch = FetchType.LAZY)
```

Lazy fetching means related entities are not unnecessarily loaded immediately.

The related Students are loaded when the relationship is accessed.

---

# 🔗 Cascade

Cascade controls whether operations on one entity are propagated to related entities.

Example:

```java
@OneToOne(cascade = CascadeType.ALL)
```

Common cascade types:

```text
PERSIST
MERGE
REMOVE
REFRESH
DETACH
ALL
```

Cascade should be used carefully, especially with Many-to-Many relationships where entities can be shared.

---

# ⚠️ JSON Infinite Recursion

Bidirectional relationships can cause recursive JSON responses.

For example:

```text
Professor
   ↓
Student
   ↓
Professor
   ↓
Student
   ↓
...
```

This can result in a `StackOverflowError` during JSON serialization.

To prevent this, `@JsonIgnore` can be placed on one side:

```java
@JsonIgnore
@ManyToMany(mappedBy = "students")
private List<Professor> professors;
```

`@JsonIgnore` only affects JSON serialization. It does not remove the JPA relationship.

---

# 🧠 Key Learning

This project demonstrates how relationships are handled in a real relational database:

```text
@OneToOne
    ↓
Foreign Key

@OneToMany + @ManyToOne
    ↓
Foreign Key

@ManyToMany
    ↓
Junction Table
```

The relationship is not created by sending complete nested objects from Postman. Instead, existing entities can be connected using their IDs through dedicated mapping APIs.

Example:

```text
POST /professors/1/students/2
```

means:

```text
Professor ID = 1
Student ID   = 2

        ↓

Create relationship
```

This approach helped practice **JPA relationship mapping, foreign keys, junction tables, owning/inverse sides, bidirectional relationships, lazy loading, cascade behavior, and JSON serialization.**
