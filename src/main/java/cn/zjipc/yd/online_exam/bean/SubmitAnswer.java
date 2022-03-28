package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * 封装学生提交的题目id与题目的答案
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitAnswer {
    private Integer questionId;
    private String questionType;
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmitAnswer answer1 = (SubmitAnswer) o;
        if (!Objects.equals(questionId, answer1.questionId)) return false;
        //如果答案顺序不一样
        byte[] answerBytes = answer.getBytes();
        byte[] answerBytes1 = answer1.answer.getBytes();
        Arrays.sort(answerBytes);
        Arrays.sort(answerBytes1);
        return (Arrays.toString(answerBytes).equals(Arrays.toString(answerBytes1)));
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    //    @Override
//    public int compareTo(Object o) {
//        if (o instanceof SubmitAnswer) {
//            SubmitAnswer answer = (SubmitAnswer) o;
//            if (answer.questionId.equals(this.questionId)){
//                if(answer.getAnswer().equals(this.answer)){
//                    return 1;
//                }
//                return 0;
//            }
//            return 0;
//        }
//        throw new RuntimeException("传入类型不一致");
//    }
}
