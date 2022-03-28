package cn.zjipc.yd.online_exam.service.impl;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.mapper.*;
import cn.zjipc.yd.online_exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {RuntimeException.class, Error.class})
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private StudentScoresMapper studentScoresMapper;
    @Override
    public Teacher teacherAuth(Teacher teacher) {
        return teacherMapper.getTeacherByIdAndPassword(teacher);
    }

    @Override
    public List<Student> getMyStudentList(String stuMajor) {
        return studentMapper.getStudentByStuMajor(stuMajor);
    }

    @Override
    public List<Paper> getMyPaperList(String major) {
        return paperMapper.getPaperListByMajor(major);
    }

    @Override
    public Paper getMyPaperById(Integer id) {
        return paperMapper.getPaperById(id);
    }

    @Override
    public Paper checkPapName(String papName) {
        return paperMapper.getPaperByPapName(papName);
    }

    @Override
    public int addMyPaper(Paper paper) {
        return paperMapper.addPaper(paper);
    }

    @Override
    public int updateMyPaper(Paper paper) {
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
    public int deleteMyPaper(Integer id) {
       return paperMapper.deletePaper(id);
    }

    @Override
    public Paper checkPaperQuestion(String papName) {
        return paperMapper.getPaperWithQuestionByPapName(papName);
    }

    @Override
    public List<Question> getQuestionByPaperName(String papName) {
        return questionMapper.getQuestionByPaperName(papName);
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public int addQuestion(Question question,String queName) {
        question.setQuePaper(queName);
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
    public List<StudentScores> getStudentResults(Integer stuId) {
        return studentScoresMapper.getStudentScoresByStuId(stuId);
    }
}
