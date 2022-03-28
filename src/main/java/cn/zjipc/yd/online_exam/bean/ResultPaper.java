package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装学生id和提交的试卷
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultPaper {
    private Integer studentId;
    private String paperName;
    private List<SubmitAnswer> submitAnswerList;
}
