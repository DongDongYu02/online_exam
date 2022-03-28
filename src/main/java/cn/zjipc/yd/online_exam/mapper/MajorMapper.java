package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    /**
     * 获取专业信息列表
     */
    List<Major> getMajorList();

    /**
     * 获取专业信息byId
     * @param id 专业id
     */
    Major getMajorById(Integer id);

    /**
     * 获取专业信息
     * @param majName 专业名称
     */
    Major getMajorByMajName(String majName);
    /**
     * 添加专业信息
     * @param major 封装了专业信息的对象
     */
    int addMajor(Major major);

    /**
     * 修改专业信息
     * @param major 封装专业信息的对象
     */
    int updateMajor(Major major);

    /**
     * 删除专业
     * @param id 专业id
     */
    int deleteMajor(Integer id);
}
