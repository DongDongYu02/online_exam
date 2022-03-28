package cn.zjipc.yd.online_exam.bean;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    private Integer id; //试卷id
    private String papName;  //试卷题目
    private String papMajor; //试卷所属专业
    private String createTime; //试卷创建时间
    private String startTime; //试卷开始考试时间
    private String endTime; //试卷结束考试时间
    private Integer examTime; //考试时长

    private long examRemaining; //剩余考试时长 非sql字段
    private List<Question> questionList; //试卷题目 非sql字段
    private PageInfo<Question> pageInfo; //封装了试卷题目的分页数据 非sql字段
    private List<Question> singleQuestion; //单选题 非sql字段
    private List<Question> multiQuestion; //多选题 非sql字段
    private List<Question> judgeQuestion; //判断题 非sql字段
}
