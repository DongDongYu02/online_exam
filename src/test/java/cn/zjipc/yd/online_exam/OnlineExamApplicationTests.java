package cn.zjipc.yd.online_exam;

import cn.zjipc.yd.online_exam.bean.Paper;
import cn.zjipc.yd.online_exam.bean.Question;
import cn.zjipc.yd.online_exam.bean.SubmitAnswer;
import cn.zjipc.yd.online_exam.mapper.QuestionMapper;
import cn.zjipc.yd.online_exam.mapper.StudentMapper;
import cn.zjipc.yd.online_exam.mapper.StudentScoresMapper;
import cn.zjipc.yd.online_exam.utils.StudentUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@SpringBootTest
class OnlineExamApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentScoresMapper studentScoresMapper;

    @Test
    void getAllQuestionTest() {
        List<Question> allQuestion = questionMapper.getAllQuestion();
        allQuestion.forEach(System.out::println);
    }

    @Test
    void getPaperListByStuMajorTest() {
        List<Paper> papers = studentMapper.getPaperListByStuMajor("软件工程");
        papers.forEach(System.out::println);
    }

    @Test
    void getSingleQuestionListByPapNameTest() {
        List<Question> java = questionMapper.getSingleQuestionListByPapName("JAVA");
        java.forEach(System.out::println);
    }

    @Test
    void getMultiQuestionListByPapNameTest() {
        List<Question> java = questionMapper.getMultiQuestionListByPapName("JAVA");
        java.forEach(System.out::println);
    }

    @Test
    void getJudgeQuestionListByPapNameTest() {
        List<Question> java = questionMapper.getJudgeQuestionListByPapName("JAVA");
        java.forEach(System.out::println);
    }

    @Test
    void getPaperQuestionsByPapName() {
        Paper java = studentMapper.getPaperQuestionByPapName("Java");
        System.out.println(java);
        java.getSingleQuestion().forEach(System.out::println);
        java.getMultiQuestion().forEach(System.out::println);
        java.getJudgeQuestion().forEach(System.out::println);
    }

    @Test
    void getSubmitAnswerTest() {
        List<SubmitAnswer> answerList = new ArrayList<>();
        answerList.add(new SubmitAnswer(2, "1", "C"));
        answerList.add(new SubmitAnswer(4, "1", "A"));
        answerList.add(new SubmitAnswer(12, "1", "C"));
        answerList.add(new SubmitAnswer(6, "2", "B,D,C"));
        List<SubmitAnswer> answer = questionMapper.getPaperAnswerByPaperName("JAVA基础考试-软件工程");
        double score = 0;
        for (SubmitAnswer value : answerList) {
            for (SubmitAnswer submitAnswer : answer) {
                System.out.println(value + "==>" + submitAnswer);
                if (value.equals(submitAnswer)) {
                    if ("1".equals(value.getQuestionType())) {
                        score += 2;
                        break;
                    }
                    if ("2".equals(value.getQuestionType())) {
                        score += 3;
                        break;
                    }
                    if ("3".equals(value.getQuestionType())) {
                        score += 1;
                        break;
                    }
                }
            }
        }
        System.out.println(score);

    }

    @Test
    void checkDoneTest() {
        Integer integer = studentScoresMapper.countPaperNameByStuId(2210001, "JAVA基础考试-软件工程");
        System.out.println(integer);
    }
//    @Autowired
//    private StudentExamTempMapper studentExamTempMapper;
//    @Test
//    void countDownTest() throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String startTime = simpleDateFormat.format(date);
//        System.out.println(startTime);
//        date.setTime(date.getTime() + (120 * 60 * 1000));
//        String endTime = simpleDateFormat.format(date);
//        System.out.println(endTime);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        long time = simpleDateFormat.parse(endTime).getTime();
//        long l = time - new Date().getTime();
//        System.out.println(l);
//
//        studentExamTempMapper.addStudentExamTemp(220001,"JAVA基础考试",startTime,endTime);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        String temp = studentExamTempMapper.getExamEndTimeByStuIdAndPapName(220001, "JAVA基础考试");
//        long l1 = simpleDateFormat.parse(temp).getTime() - new Date().getTime();
//        System.out.println(l1);
//
//        System.out.println("==========>"+studentExamTempMapper.getPapNameByStuId(220001));
//
//
//    }
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void redis(){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        List<Object> values = hash.values("stuAnswer:2210001");
        System.out.println(values);
    }

    @Test
    public void redistest2(){
        List<SubmitAnswer> submitAnswers = new ArrayList<>();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("StuAnswerTemp:2210001");
        for (Object key : entries.keySet()) {
            String queId =(String) key;
            String answer = (String) entries.get(key);
            String queType = StudentUtil.checkQueType(answer);
            submitAnswers.add(new SubmitAnswer(Integer.parseInt(queId),queType,answer));
        }
        submitAnswers.add(new SubmitAnswer(12,"1","A"));
        for (SubmitAnswer answer : submitAnswers) {
            System.out.println(answer);
        }
    }
}
