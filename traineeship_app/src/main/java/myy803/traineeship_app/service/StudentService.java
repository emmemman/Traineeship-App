package myy803.traineeship_app.service;

import myy803.traineeship_app.domain.Student;

public interface StudentService {
    Student findStudentByUsername(String username);

    void saveStudent(Student student);

    void saveStudentLogbook(String username, String logbook);
}
