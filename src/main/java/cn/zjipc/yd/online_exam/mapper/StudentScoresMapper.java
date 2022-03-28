package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.StudentScores;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentScoresMapper {
    /**
     * 保存学生考试成绩
     * @param studentScores 封装了成绩信息的对象
     */
    int addStudentScores(StudentScores studentScores);

    /**
     * 获取学生考试成绩列表
     * @param stuId 学生id
     */
    List<StudentScores> getStudentScoresByStuId(Integer stuId);

    /**
     * 获取考生提交试卷的次数
     * @param stuId 考生id
     * @param papName 试卷名称
     */
    Integer countPaperNameByStuId(@Param("stuId") Integer stuId, @Param("papName")String papName);
}
