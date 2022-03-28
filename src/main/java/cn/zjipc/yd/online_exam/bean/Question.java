package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id; //题目id
    private String queName; //题目
    private String queType; //题目类型
    private String queOption1; //选项1
    private String queOption2;  //选项2
    private String queOption3; //选项3
    private String queOption4;  //选项4
    private String queAnswer; //正确答案
    private String quePaper; //所属试卷

    private String stuAnswer; //考生选择的答案，如果选择过？
}
