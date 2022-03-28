package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Question;
import cn.zjipc.yd.online_exam.bean.SubmitAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 获取所有题目
     */
    List<Question> getAllQuestion();

    /**
     * 获取题目列表by试卷名称
     *
     * @param papName 试卷名称
     */
    List<Question> getQuestionByPaperName(String papName);

    /**
     * 获取题目id与答案封装的对象集合
     *
     * @param papName 试卷名称
     */
    List<SubmitAnswer> getPaperAnswerByPaperName(String papName);

    /**
     * 获取题目信息byId
     *
     * @param id 题目id
     */
    Question getQuestionById(Integer id);

    /**
     * 获取试卷信息 根据试卷题目
     * @param queName 试卷题目
     */
    Question getQuestionByQueName(String queName);

    /**
     * 添加题目信息
     *
     * @param question 封装了题目信息的对象
     */
    int addQuestion(Question question);

    /**
     * 修改题目信息
     *
     * @param question 封装了题目信息的对象
     */
    int updateQuestion(Question question);

    /**
     * 修改题目所属试卷
     */
    int updateQuePaper(String oldPapName, String newPapName);

    /**
     * 删除题目byId
     *
     * @param id 题目id
     */
    int deleteQuestion(Integer id);

    /**
     * 根据试卷名查询该试卷的所有单选题目
     */
    List<Question> getSingleQuestionListByPapName(String papName);

    /**
     * 根据试卷名查询该试卷的所有多选题目
     */
    List<Question> getMultiQuestionListByPapName(String papName);

    /**
     * 根据试卷名查询该试卷的所有判断题目
     */
    List<Question> getJudgeQuestionListByPapName(String papName);
}
