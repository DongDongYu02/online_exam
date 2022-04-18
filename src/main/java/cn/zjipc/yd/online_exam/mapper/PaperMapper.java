package cn.zjipc.yd.online_exam.mapper;

import cn.zjipc.yd.online_exam.bean.Paper;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;
import org.apache.ibatis.annotations.Mapper;

import java.nio.file.Path;
import java.util.List;

@Mapper
public interface PaperMapper {
    /**
     * 获取所有试卷信息
     */
    List<Paper> getAllPaper();

    /**
     * 获取试卷列表by专业名称
     * @param major 专业名称
     */
    List<Paper> getPaperListByMajor(String major);

    /**
     * 获取试卷信息byId
     * @param id 试卷id
     */
    Paper getPaperById(Integer id);

    /**
     * 获取试卷信息
     * @param papName 试卷名称
     */
    Paper getPaperByPapName(String papName);

    /**
     * 添加试卷信息
     */
    int addPaper(Paper paper);


    /**
     * 修改试卷信息
     * @param paper 封装了试卷信息的对象
     */
    int updatePaper(Paper paper);

    /**
     * 删除试卷信息
     * @param id 试卷id
     */
    int deletePaper(Integer id);

    /**
     * 根据试卷名称获取题目列表
     * @param papName 试卷名称
     */
    Paper getPaperWithQuestionByPapName(String papName);

    Long getPaperCount();

//    /**
//     * 根据试卷名获取对应题目列表
//     * @param papName 试卷名称
//     */
//    Paper getPaperWithQuestionsByPapName(String papName);
}
