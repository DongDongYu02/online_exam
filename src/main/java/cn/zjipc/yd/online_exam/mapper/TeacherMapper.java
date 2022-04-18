package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface TeacherMapper {

    /**
     * 根据工号查询教师信息
     */
    Teacher getTeacherById(Integer id);

    /**
     * 根据教师工号和密码查询教师信息
     * @param teacher 封装了工号和密码的教师对象
     */
    Teacher getTeacherByIdAndPassword(Teacher teacher);
    /**
     * 获取教师列表
     * @return 教师列表
     */
    List<Teacher> getTeacherList();

    /**
     * 添加教师信息
     */
    int addTeacher(Teacher teacher);

    /**
     * 修改教师信息
     * @param teacher 封装了教师信息的对象
     */
    int updateTeacher(Teacher teacher);

    /**
     * 删除教师信息byId
     */
    int deleteTeacher(Integer id);

    Long getTeacherCount();
}
