package myy803.traineeship_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.mappers.StudentMapper;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student findStudentByUsername(String username) {
        Student student = studentMapper.findByUsername(username);
        if (student == null) {
            student = new Student(username);
        }
        return student;
    }

    @Override
    public void saveStudent(Student student) {
        student.setLookingForTraineeship(true); // Logic from controller
        studentMapper.save(student);
    }

    @Override
    public void saveStudentLogbook(String username, String logbook) {
        Student student = findStudentByUsername(username);
        if (student != null) { // Should check if assigned? Controller logic had checks.
            student.setLogbook(logbook);
            studentMapper.save(student);
        }
    }
}
