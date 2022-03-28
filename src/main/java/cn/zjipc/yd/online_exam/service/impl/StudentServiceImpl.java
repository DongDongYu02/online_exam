package cn.zjipc.yd.online_exam.service.impl;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.mapper.*;
import cn.zjipc.yd.online_exam.service.StudentService;
import cn.zjipc.yd.online_exam.utils.StudentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final QuestionMapper questionMapper;
    private final PaperMapper paperMapper;
    private final StudentScoresMapper studentScoresMapper;


    private final RedisTemplate redisTemplate;

    public StudentServiceImpl(StudentMapper studentMapper, QuestionMapper questionMapper, PaperMapper paperMapper, StudentScoresMapper studentScoresMapper, RedisTemplate redisTemplate) {
        this.studentMapper = studentMapper;
        this.questionMapper = questionMapper;
        this.paperMapper = paperMapper;
        this.studentScoresMapper = studentScoresMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Student studentAuth(Student student) {
        return studentMapper.getStudentByIdAndPassword(student);
    }

    @Override
    public List<Paper> getStudentPaperList(String major) {
        return paperMapper.getPaperListByMajor(major);
    }

    @Override
    public Paper getExamPaper(String papName) {
        return paperMapper.getPaperWithQuestionByPapName(papName);
    }

    @Override
    public List<Question> getQuestionListByPapName(String papName) {
        return questionMapper.getQuestionByPaperName(papName);
    }

    @Override
    public void saveAnswer(String stuId, String questionId, String answer) {
        redisTemplate.opsForHash().put("StuAnswerTemp:"+stuId,questionId,answer);
    }

    @Override
    public List<Question> checkIsAnswer(String stuId, List<Question> question) {
        Question que = question.get(0);
        Boolean AnswerIsExists = redisTemplate.opsForHash().hasKey("StuAnswerTemp:" + stuId, String.valueOf(que.getId()));
        if(AnswerIsExists){
            String answer = (String)redisTemplate.opsForHash().get("StuAnswerTemp:" + stuId, String.valueOf(que.getId()));
            question.get(0).setStuAnswer(answer);
            return question;
        }
        return question;
    }

    @Override
    public Integer referExam( String papName, Integer stuId) {
        //删除考试倒计时kv
        redisTemplate.delete("stuExamRemaining:"+stuId);
        //查询试卷答案
        List<SubmitAnswer> answers = questionMapper.getPaperAnswerByPaperName(papName);

        //获取考生提交的答案
        Map<String, String> redisStuAnswer = redisTemplate.opsForHash().entries("StuAnswerTemp:" + stuId);
        List<SubmitAnswer> submitAnswers = StudentUtil.getSubmitAnswer(redisStuAnswer);

        redisTemplate.delete("StuAnswerTemp:" + stuId);
        //阅卷返回成绩结果
        double score = StudentUtil.referExam(submitAnswers, answers);
        //查询学生信息
        Student student = studentMapper.getStudentById(stuId);
        //封装学生信息试卷信息成绩
        StudentScores studentScore = new StudentScores(null, student.getId(), student.getStuName(), student.getStuMajor()
                , papName, score, null);
        //保存学生成绩信息
        return studentScoresMapper.addStudentScores(studentScore);
    }

    @Override
    public List<StudentScores> getStudentScores(Integer stuId) {
        return studentScoresMapper.getStudentScoresByStuId(stuId);
    }

    @Override
    public boolean checkPaperDone(Integer stuId, String papName) {
        return studentScoresMapper.countPaperNameByStuId(stuId, papName) > 0;
    }

    @Override
    public void addStudentExamTemp(Integer stuId, String papName) {
        Paper paper = paperMapper.getPaperWithQuestionByPapName(papName);
//        //获取考试时长
//        Integer examTime = paper.getExamTime();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取开始考试的时间
//        Date start = new Date();
//        String startTime = simpleDateFormat.format(start);
//        //获取考试结束时间
//        Date end = new Date();
//        end.setTime(start.getTime() + (examTime * 60 * 1000));
//        String endTime = simpleDateFormat.format(end);
        //保存考生考试临时数据
//        studentExamTempMapper.addStudentExamTemp(stuId, papName, startTime, endTime);

        //考生开始开始后，将考生以及试卷信息存入redis并设置过期时间为考试时长 作为倒计时
        redisTemplate.opsForValue().set("stuExamRemaining:"+stuId,papName,paper.getExamTime(), TimeUnit.MINUTES);
    }

    @Override
    public String getExamTempPapNameByStuId(Integer stuId) {
        return (String) redisTemplate.opsForValue().get("stuExamRemaining:"+stuId);
    }


    @Override
    public long getExamRemaining(Integer stuId, String papName) throws ParseException {
//        String examEndTime = studentExamTempMapper.getExamEndTimeByStuIdAndPapName(stuId, papName);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return simpleDateFormat.parse(examEndTime).getTime() - new Date().getTime();
        return redisTemplate.getExpire("stuExamRemaining:"+stuId) * 1000;
    }



}
