package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScores {
    private Integer id;
    private Integer stuId;
    private String stuName;
    private String stuMajor;
    private String papName;
    private Double score;
    private String createTime;
}
