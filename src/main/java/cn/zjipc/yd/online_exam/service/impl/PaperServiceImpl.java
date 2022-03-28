package cn.zjipc.yd.online_exam.service.impl;

import cn.zjipc.yd.online_exam.bean.Paper;
import cn.zjipc.yd.online_exam.mapper.PaperMapper;
import cn.zjipc.yd.online_exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

}
