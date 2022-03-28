package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String teaName;
    private String teaPassword;
    private String teaMajor;
    private String teaTel;
    private String role;
}
