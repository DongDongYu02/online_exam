package cn.zjipc.yd.online_exam.service;

import cn.zjipc.yd.online_exam.bean.*;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.text.ParseException;
import java.util.List;

@Service
public interface StudentService {
    /**
     * 学生登录身份认证
     *
     * @param student 封装了学生学号和密码的对象
     */
    Student studentAuth(Student student);

    /**
     * 获取学生专业考试列表
     *
     * @param major 学生的专业
     */
    List<Paper> getStudentPaperList(String major);

    /**
     * 获取考生开始考试后的试卷信息
     *
     * @param papName 试卷名称
     */
    Paper getExamPaper(String papName);

    /**
     * 获取考生考试的试卷题目
     *
     * @param papName 试卷名称
     */
    List<Question> getQuestionListByPapName(String papName);

    /**
     * 将考生选择的题目与答案保存到redis
     *
     * @param stuId      考生id
     * @param questionId 题目id
     * @param answer     答案
     */
    void saveAnswer(String stuId, String questionId, String answer);

    /**
     * 查询当前题目考生是否回答过
     * @param stuId 考生id
     * @param question 封装了一道题的分页数据对象
     * @return 如果redis缓存中存在该题目，则将考生选择的答案封装进题目对象中
     */
    List<Question> checkIsAnswer(String stuId,List<Question> question);

    /**
     * 阅卷
     *
     * @param submitAnswers 学生提交的题目与答案
     * @param papName       试卷名称
     * @param stuId         学生id
     */
    Integer referExam(String papName, Integer stuId);

    /**
     * 获取学生历史考试成绩
     *
     * @param stuId 学生id
     */
    List<StudentScores> getStudentScores(Integer stuId);

    /**
     * 判断学生是否已经做过试卷
     *
     * @param stuId   学生id
     * @param papName 试卷名称
     */
    boolean checkPaperDone(Integer stuId, String papName);

    /**
     * 添加学生考试信息临时数据
     *
     * @param stuId   学生id
     * @param papName 试卷名称
     */
    void addStudentExamTemp(Integer stuId, String papName);

    /**
     * 获取学生临时考试信息的试卷名称
     *
     * @param stuId 学生id
     * @return null代表没有考试信息
     */
    String getExamTempPapNameByStuId(Integer stuId);

    /**
     * 获取考生当前考试的剩余时间
     *
     * @param stuId   考生id
     * @param papName 试卷名称
     * @return 考试剩余的毫秒数
     */
    long getExamRemaining(Integer stuId, String papName) throws ParseException;

    /**
     * 删除学生当前考试临时数据
     * 通常在学生交卷后或者考试时间到后执行
     *
     * @param stuId 学生id
     */
    int deleteExamTemp(Integer stuId);

}
