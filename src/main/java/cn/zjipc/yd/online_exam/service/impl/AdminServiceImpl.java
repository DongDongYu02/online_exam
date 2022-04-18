package cn.zjipc.yd.online_exam.service.impl;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.mapper.*;
import cn.zjipc.yd.online_exam.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final TeacherMapper teacherMapper;
    private final MajorMapper majorMapper;
    private final StudentMapper studentMapper;
    private final PaperMapper paperMapper;
    private final QuestionMapper questionMapper;

    public AdminServiceImpl(AdminMapper adminMapper, TeacherMapper teacherMapper, MajorMapper majorMapper, StudentMapper studentMapper, PaperMapper paperMapper, QuestionMapper questionMapper) {
        this.adminMapper = adminMapper;
        this.teacherMapper = teacherMapper;
        this.majorMapper = majorMapper;
        this.studentMapper = studentMapper;
        this.paperMapper = paperMapper;
        this.questionMapper = questionMapper;
    }

    @Override
    public Integer saveAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public Admin checkAdmin(Admin admin) {
        Admin a = adminMapper.getAdminByUsernameAndPassword(admin);
        if (a != null) {
            return a;
        }
        return null;
    }

    @Override
    public List<Admin> getAdminList() {
        return adminMapper.getAdminList();
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.getAdminByUsername(username);
    }

    @Override
    public int deleteAdminById(Integer id) {
        return adminMapper.deleteAdminById(id);
    }

    @Override
    public List<Teacher> getTeacherList() {
        return teacherMapper.getTeacherList();
    }

    @Override
    public List<Major> getMajorList() {
        return majorMapper.getMajorList();
    }

    @Override
    public Major checkMajorName(String majName) {
        return majorMapper.getMajorByMajName(majName);
    }

    @Override
    public int addTeacher(Teacher teacher) {
        return teacherMapper.addTeacher(teacher);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        return teacherMapper.getTeacherById(id);
    }

    @Override
    public int deleteTeacherById(Integer id) {
        return teacherMapper.deleteTeacher(id);
    }

    @Override
    public List<Student> getStudentList() {
        return studentMapper.getStudentList();
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public int deleteStudentById(Integer id) {
        return studentMapper.deleteStudentById(id);
    }

    @Override
    public int addMajor(Major major) {
        return majorMapper.addMajor(major);
    }

    @Override
    public int updateMajor(Major major) {
        return majorMapper.updateMajor(major);
    }

    @Override
    public Major getMajorById(Integer id) {
        return majorMapper.getMajorById(id);
    }

    @Override
    public int deleteMajorById(Integer id) {
        return majorMapper.deleteMajor(id);
    }

    @Override
    public List<Paper> getAllPaper() {
        return paperMapper.getAllPaper();
    }

    @Override
    public Paper getPaperById(Integer id) {
        return paperMapper.getPaperById(id);
    }

    @Override
    public Paper checkPapName(String papName) {
        return paperMapper.getPaperByPapName(papName);
    }

    @Override
    public int addPaper(Paper paper) {
        return paperMapper.addPaper(paper);
    }

    @Override
    public int updatePaper(Paper paper) {
        //获取修改前的题目id
        String oldPapName = paperMapper.getPaperById(paper.getId()).getPapName();
        if (paperMapper.updatePaper(paper) > 0) {
            //修改该试卷下的所有题目的所属试卷
            questionMapper.updateQuePaper(oldPapName, paper.getPapName());
            return 1;
        }
        return 0;
    }

    @Override
    public int deletePaper(Integer id) {
        return paperMapper.deletePaper(id);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionMapper.getAllQuestion();
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public Question checkQueName(String queName) {
        return questionMapper.getQuestionByQueName(queName);
    }

    @Override
    public int addQuestion(Question question) {
        return questionMapper.addQuestion(question);
    }

    @Override
    public int updateQuestion(Question question) {
        return questionMapper.updateQuestion(question);
    }

    @Override
    public int deleteQuestion(Integer id) {
        return questionMapper.deleteQuestion(id);
    }

    @Override
    public Long getTeacherCount() {
       return teacherMapper.getTeacherCount();
    }

    @Override
    public Long getStudentCount() {
        return studentMapper.getStudentCount();
    }

    @Override
    public Long getPaperCount() {
        return paperMapper.getPaperCount();
    }

    @Override
    public Long getQuestionCount() {
        return questionMapper.getQuestionCount();
    }

}
