package cn.zjipc.yd.online_exam.utils;

import cn.zjipc.yd.online_exam.bean.SubmitAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StudentUtil {
    public static String checkQueType(String answer){
        if("1".equals(answer) || "2".equals(answer)){
                return "3";
        }
        if(answer.length() == 1){
            return "1";
        }
        return "2";
    }

    public static List<SubmitAnswer>  getSubmitAnswer(Map<String, String> redisStuAnswer){
        List<SubmitAnswer> submitAnswers = new ArrayList<>();
        for (String key : redisStuAnswer.keySet()) {
            String answer =  redisStuAnswer.get(key);
            String queType = StudentUtil.checkQueType(answer);
            submitAnswers.add(new SubmitAnswer(Integer.parseInt(key),queType,answer));
        }
        return submitAnswers;

    }
    /**
     * 阅卷方法
     * @param stuSubmitAnswer 学生提交的题目与答案信息
     * @param paperAnswer 数据库中正确的答案信息
     * @return 学生成绩结果
     */
    public static double referExam(List<SubmitAnswer> stuSubmitAnswer, List<SubmitAnswer> paperAnswer) {
        //分数
        double score = 0;
        //阅卷====》遍历试卷答案，拿学生的答案去比较
        for (SubmitAnswer value : stuSubmitAnswer) {
            for (SubmitAnswer submitAnswer : paperAnswer) {
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
        return score;
    }

}
