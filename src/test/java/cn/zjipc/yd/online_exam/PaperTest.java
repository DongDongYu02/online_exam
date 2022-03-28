package cn.zjipc.yd.online_exam;

import cn.zjipc.yd.online_exam.bean.Paper;
import cn.zjipc.yd.online_exam.mapper.PaperMapper;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.List;

@SpringBootTest
public class PaperTest {
    @Autowired
    private PaperMapper paperMapper;
    @Test
    void getAllPaperTest(){
        List<Paper> allPaper = paperMapper.getAllPaper();
        for (Paper paper : allPaper) {
            System.out.println(paper);
        }
    }

    @Test
    void getPaper(){
        Paper paperById = paperMapper.getPaperById(1);
        System.out.println(paperById.getStartTime());
        Paper paper = WebUtil.paperFormatDateTime(paperById);
        System.out.println(paper.getStartTime());
    }
}
