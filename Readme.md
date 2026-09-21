# Professor–Subject Relationship in Spring Boot JPA

## 📌 Overview

In this module, we implemented a **One-to-Many and Many-to-One relationship** between `Professor` and `Subject` using Spring Boot, JPA, and Hibernate.

### Relationship

- One Professor can teach multiple Subjects.
- One Subject belongs to only one Professor.

```text
Professor 1 ─────────── * Subject
🛠️ Technologies Used
Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
Lombok
Maven
📂 Entity Relationship
Professor Entity

The Professor entity represents the parent side of the relationship.

@OneToMany(mappedBy = "professor")
private List<Subject> subjects = new ArrayList<>();
Subject Entity

The Subject entity represents the owning side of the relationship.

@ManyToOne
@JoinColumn(name = "professor_id")
private Professor professor;
🗄️ Database Structure
Professor Table
id	name
1	Rahul
Subject Table
id	name	professor_id
1	Java	1
2	Spring Boot	1
3	Hibernate	1

The professor_id column acts as a Foreign Key in the subject table.

🔑 Important JPA Concepts
1. @OneToMany

Used in the Professor entity.

@OneToMany(mappedBy = "professor")
private List<Subject> subjects = new ArrayList<>();

It means that one professor can have multiple subjects.

2. @ManyToOne

Used in the Subject entity.

@ManyToOne
@JoinColumn(name = "professor_id")
private Professor professor;

It means that multiple subjects can belong to one professor.

3. mappedBy
mappedBy = "professor"

Here, professor refers to the field name inside the Subject entity:

private Professor professor;

It does not refer to the database column name.

4. Owning Side

The Subject entity is the owning side because it contains:

@JoinColumn(name = "professor_id")

The foreign key is maintained by the Subject entity.

🔗 Mapping Service Method

The following method maps an existing Professor to an existing Subject.

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
Explanation
Find Professor
Professor professor = professorRepo.findById(professorId)
        .orElseThrow(() ->
                new EntityNotFoundException("Professor not found"));

Fetches the Professor using the provided ID.

Find Subject
Subject subject = subjectRepo.findById(subjectId)
        .orElseThrow(() ->
                new EntityNotFoundException("Subject not found"));

Fetches the Subject using the provided ID.

Add Subject to Professor
professor.getSubjects().add(subject);

Adds the Subject to the Professor's subject list in Java.

Set Professor in Subject
subject.setProfessor(professor);

Sets the Professor reference inside the Subject entity.

Since Subject is the owning side, this step is important for updating the foreign key.

🌐 API Endpoint
Map Professor to Subject
POST /professors/{professorId}/subjects/{subjectId}
Example Request
POST http://localhost:8080/professors/1/subjects/2
Path Variables
Variable	Description
professorId	ID of the Professor
subjectId	ID of the Subject
Postman Configuration
Method: POST
URL: http://localhost:8080/professors/1/subjects/2
Body: No body required

The Professor and Subject must already exist in the database.

🧪 Example
Before Mapping

Professor:

{
  "id": 1,
  "name": "Rahul",
  "subjects": []
}

Subject:

{
  "id": 2,
  "name": "Spring Boot",
  "professor": null
}
After Mapping

Professor:

{
  "id": 1,
  "name": "Rahul",
  "subjects": [
    {
      "id": 2,
      "name": "Spring Boot"
    }
  ]
}

The subject table will contain:

id	name	professor_id
2	Spring Boot	1
⚠️ Common Issues
1. NullPointerException

If the list is not initialized:

private List<Subject> subjects;

Then this may cause an error:

professor.getSubjects().add(subject);
Solution

Initialize the list:

private List<Subject> subjects = new ArrayList<>();
2. Infinite JSON Recursion

If both entities contain references to each other, the JSON response may become recursive:

Professor → Subject → Professor → Subject → ...

To avoid this, you can use:

@JsonIgnore
private Professor professor;

in the Subject entity.

Import:

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnore only affects JSON serialization. It does not remove the JPA relationship.

✅ Learning Outcomes

After completing this module, you should understand:

 @OneToMany
 @ManyToOne
 @JoinColumn
 mappedBy
 Owning and inverse sides
 Foreign key mapping
 Mapping existing entities using IDs
 Updating bidirectional relationships
 Testing APIs using Postman
 Handling JSON recursion
 Initializing relationship collections
🚀 Next Steps

Possible improvements for this module:

Add an API to remove a Subject from a Professor.
Add an API to change a Subject's Professor.
Fetch all Subjects of a Professor.
Add validation for duplicate mappings.
Handle exceptions globally.
Add pagination and search functionality.
👨‍💻 Author

Divanshu Gaur

This module is part of a practical Spring Boot and JPA learning project.