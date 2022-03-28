package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Paper;
import cn.zjipc.yd.online_exam.bean.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student getStudentByIdAndPassword(Student student);
    /**
     * 获取学生列表信息
     */
    List<Student> getStudentList();

    /**
     * 添加学生信息
     * @param student 封装了学生信息的对象
     */
    int addStudent(Student student);

    /**
     * 修改学生信息
     * @param student 封装了学生信息的对象
     */
    int updateStudent(Student student);

    /**
     * 获取学生信息byId
     * @param id 学生学号
     */
    Student getStudentById(Integer id);

    /**
     * 获取学生列表by专业名称
     * @param stuMajor 专业名称
     */
    List<Student> getStudentByStuMajor(String stuMajor);
    /**
     * 删除学生byId
     * @param id 学生学号
     */
    int deleteStudentById(Integer id);

    /**
     * 根据学生专业获取试卷列表
     * @param stuMajor 学生专业
     */
     List<Paper> getPaperListByStuMajor(String stuMajor);

    /**
     * 根据试卷名字查询试卷信息以及该试卷对应的所有题目
     * @param papName 试卷名字
     */
     Paper getPaperQuestionByPapName(String papName);
}
