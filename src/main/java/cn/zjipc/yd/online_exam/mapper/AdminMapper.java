package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    /**
     * 获取管理员信息by用户名和密码
     * @param admin 封装了用户名和密码的管理员对象
     */
    Admin getAdminByUsernameAndPassword(Admin admin);

    /**
     * 添加管理员
     * @param admin 管理员对象
     */
    Integer addAdmin(Admin admin);

    /**
     * 获取管理员信息by用户名
     * @param username 用户名
     */
    Admin getAdminByUsername(String username);

    /**
     * 获取管理员信息byId
     * @param id 管理员id
     */
    Admin getAdminById(Integer id);

    /**
     * 获取管理员列表
     */
    List<Admin> getAdminList();

    /**
     * 修改管理员信息
     * @param admin 封装了管理员信息的对象
     * @return
     */
    int updateAdmin(Admin admin);

    /**
     * 根据id删除管理员
     * @param id 管理员id
     */
    int deleteAdminById(Integer id);

}
