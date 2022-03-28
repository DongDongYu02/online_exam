package cn.zjipc.yd.online_exam.service;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.mapper.TeacherMapper;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.thymeleaf.spring5.util.SpringValueFormatter;

import java.util.List;

@Service
public interface TeacherService {
    /**
     * 教师登录认证接口
     *
     * @param teacher 封装了教师工号和密码的对象
     */
    Teacher teacherAuth(Teacher teacher);

    /**
     * 获取教师专业对应的学生列表
     *
     * @param stuMajor 对应专业名称
     */
    List<Student> getMyStudentList(String stuMajor);

    /**
     * 获取教师专业对应的试卷列表
     */
    List<Paper> getMyPaperList(String major);

    /**
     * 获取试卷信息byId
     *
     * @param id 试卷id
     */
    Paper getMyPaperById(Integer id);

    /**
     * 检查试卷名是否已存在
     *
     * @param papName 试卷名
     */
    Paper checkPapName(String papName);

    /**
     * 添加教师所在专业的试卷
     *
     * @param paper 封装了试卷信息的对象,试卷所属专业是操作教师的专业
     */
    int addMyPaper(Paper paper);

    /**
     * 教师修改试卷信息
     *
     * @param paper 封装了试卷信息的对象
     */
    int updateMyPaper(Paper paper);

    /**
     * 教师删除试卷信息
     *
     * @param id 试卷id
     */
    int deleteMyPaper(Integer id);

    /**
     * 根据试卷名称获取获取封装了题目的试卷信息
     *
     * @param papName 试卷名称
     * @return 封装了题目信息的试卷对象
     */
    Paper checkPaperQuestion(String papName);

    /**
     * 根据试卷名称获取所有题目
     *
     * @param papName 试卷名称
     */
    List<Question> getQuestionByPaperName(String papName);

    /**
     * 教师获取题目信息byId
     *
     * @param id 题目id
     */
    Question getQuestionById(Integer id);

    /**
     * 教师添加题目
     *
     * @param question 封装了题目信息的对象
     * @param queName  试卷题目
     */
    int addQuestion(Question question, String queName);

    /**
     * 教师修改题目信息
     *
     * @param question 封装了题目信息的接口
     */
    int updateQuestion(Question question);

    /**
     * 教师删除题目ById
     *
     * @param id 题目id
     */
    int deleteQuestion(Integer id);

    /**
     * 获取学生考试成绩
     *
     * @param stuId 学生id
     * @return
     */
    List<StudentScores> getStudentResults(Integer stuId);
}
