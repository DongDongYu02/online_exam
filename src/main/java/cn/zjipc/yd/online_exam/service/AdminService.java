package cn.zjipc.yd.online_exam.service;

import cn.zjipc.yd.online_exam.bean.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    /**
     * 保存管理员信息
     *
     * @param admin 封装管理员信息
     */
    Integer saveAdmin(Admin admin);

    /**
     * 验证用户名密码
     *
     * @param admin 封装了用户名和密码的管理员对象
     */
    Admin checkAdmin(Admin admin);

    /**
     * 获取管理员列表
     */
    List<Admin> getAdminList();

    /**
     * 修改管理员信息
     *
     * @param admin 封装了管理员信息的对象
     */
    int updateAdmin(Admin admin);

    /**
     * 获取管理员信息ById
     *
     * @param id 管理员id
     */
    Admin getAdminById(Integer id);

    /**
     * 获取管理员信息by用户名
     * @param username 用户名
     */
    Admin getAdminByUsername(String username);

    /**
     * 删除管理员byId
     *
     * @param id 管理员id
     */
    int deleteAdminById(Integer id);

    /**
     * 获取教师列表
     *
     * @return 教师列表集合
     */
    List<Teacher> getTeacherList();

    /**
     * 获取专业信息列表
     *
     * @return 专业信息集合
     */
    List<Major> getMajorList();

    /**
     * 检查专业名称是否已存在
     * @param majName 专业名称
     */
    Major checkMajorName(String majName);

    /**
     * 添加教师信息
     *
     * @param teacher 封装了教师信息的对象
     */
    int addTeacher(Teacher teacher);

    /**
     * 修改教师信息
     *
     * @param teacher 封装了教师信息的对象
     */
    int updateTeacher(Teacher teacher);

    /**
     * 获取教师信息byId，用于修改时回显
     *
     * @param id 教师工号
     */
    Teacher getTeacherById(Integer id);

    /**
     * 删除教师byId
     */
    int deleteTeacherById(Integer id);

    /**
     * 获取学生列表信息
     */
    List<Student> getStudentList();

    /**
     * 获取学生信息byId
     *
     * @param id 学生学号
     */
    Student getStudentById(Integer id);

    /**
     * 添加学生信息
     *
     * @param student 封装了学生信息的对象
     */
    int addStudent(Student student);

    /**
     * 修改学生信息
     *
     * @param student 封装了学生信息的对象
     */
    int updateStudent(Student student);

    /**
     * 删除学生ByID
     * @param id 学生学号
     */
    int deleteStudentById(Integer id);

    /**
     * 添加专业信息
     * @param major 封装了专业信息的对象
     */
    int addMajor(Major major);

    /**
     * 修改专业信息
     * @param major 封装了专业信息的对象
     */
    int updateMajor(Major major);

    /**
     * 获取专业信息byId
     * @param id 专业id
     */
    Major getMajorById(Integer id);

    /**
     * 根据id删除专业
     * @param id 专业id
     */
    int deleteMajorById(Integer id);

    /**
     * 获取所有试卷信息
     */
    List<Paper> getAllPaper();

    /**
     * 查询试卷信息byId
     * @param id 试卷id
     */
    Paper getPaperById(Integer id);

    /**
     * 检查试卷名称是否已存在
     * @param papName 试卷名称
     */
    Paper checkPapName(String papName);

    /**
     * 添加试卷信息
     * @param paper 封装了试卷信息的对象
     */
    int addPaper(Paper paper);

    /**
     * 修改试卷信息
     * @param paper 封装了试卷信息的对象
     */
    int updatePaper(Paper paper);

    /**
     * 删除试卷信息
     * @param id 试卷id
     */
    int deletePaper(Integer id);

    /**
     * 获取题目列表
     */
    List<Question> getAllQuestion();

    /**
     * 获取题目信息byId
     * @param id 题目id
     */
    Question getQuestionById(Integer id);

    /**
     * 检查试卷题目是否已存在
     * @param queName 试卷题目
     */
    Question checkQueName(String queName);

    /**
     * 添加题目信息
     * @param question 封装了题目信息的对象
     */
    int addQuestion(Question question);
    /**
     * 修改题目信息
     * @param question 封装了题目信息的对象
     */
    int updateQuestion(Question question);

    /**
     * 删除题目信息byId
     * @param id 题目id
     */
    int deleteQuestion(Integer id);
}
