package cn.zjipc.yd.online_exam.controller;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.service.TeacherService;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 获取教师信息
     */
    @GetMapping("/teacherInfo")
    @ResponseBody
    public AjaxMsg teacherInfo(HttpSession session) {
        Teacher user = (Teacher) session.getAttribute("user");
        if (user != null) {
            return new AjaxMsg(true, "获取成功", user);
        }
        return new AjaxMsg(false, "获取失败");

    }

    /**
     * 查询我的学生信息列表的分页数据接口
     *
     * @param teaMajor 对应专业名称
     * @param pageNum  页码
     */
    @GetMapping("/myStudentInfo")
    @ResponseBody
    public AjaxMsg myStudentInfo(String teaMajor, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Student> myStudentList = teacherService.getMyStudentList(teaMajor);
        PageInfo<Student> pageInfo = new PageInfo<>(myStudentList);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 查看学生成绩接口
     *
     * @param id 学生id
     */
    @GetMapping("/checkStudentResult/{id}")
    @ResponseBody
    public AjaxMsg checkStudentResult(@PathVariable Integer id) {
        List<StudentScores> studentResults = teacherService.getStudentResults(id);
        if (studentResults != null) {
            return new AjaxMsg(true, "查询成功", studentResults);
        }
        return new AjaxMsg(false, "该学生没有任何成绩");

    }

    /**
     * 获取我的专业试卷列表的分页数据接口
     *
     * @param pageNum 页码 默认1
     */
    @GetMapping("/myPaperInfo/operate")
    @ResponseBody
    public AjaxMsg myPaperList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Teacher user = (Teacher) session.getAttribute("user");
        if (user != null) {
            String major = user.getTeaMajor();
            PageHelper.startPage(pageNum, 8);
            List<Paper> myPaperList = teacherService.getMyPaperList(major);
            PageInfo<Paper> pageInfo = new PageInfo<>(myPaperList);
            return new AjaxMsg(true, "查询成功", pageInfo);
        }
        return new AjaxMsg(false, "你可能不是教师");
    }

    /**
     * 获取试卷信息接口
     *
     * @param id 试卷id
     */
    @GetMapping("myPaperInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getMyPaper(@PathVariable Integer id) {
        Paper paper = teacherService.getMyPaperById(id);
        if (paper != null) {
            return new AjaxMsg(true, "查询成功", WebUtil.paperFormatDateTime(paper));
        }
        return new AjaxMsg(true, "查无此试卷");
    }

    /**
     * 检查试卷名称是否已存在
     *
     * @param papName 试卷名称
     */
    @GetMapping("myPaperInfo/checkPaperName")
    @ResponseBody
    public AjaxMsg checkPapName(String papName) {
        if(teacherService.checkPapName(papName) != null){
            return new AjaxMsg(false,"试卷已存在");
        }
        return new AjaxMsg(true,"试卷名可用");
    }

    /**
     * 根据教师专业添加试卷接口
     *
     * @param paper   封装了试卷信息的对象，所属专业是教师专业
     * @param session 获取session的用户信息
     */
    @PostMapping("/myPaperInfo/operate")
    @ResponseBody
    public AjaxMsg addMyPaper(Paper paper, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            paper.setPapMajor(teacher.getTeaMajor());
            if (teacherService.addMyPaper(paper) > 0) {
                return new AjaxMsg(true, " 添加成功");
            }
            return new AjaxMsg(false, " 添加失败");
        }
        return new AjaxMsg(false, "你可能不是教师");
    }

    /**
     * 教师修改试卷信息接口
     *
     * @param paper 封装了试卷信息的对象
     */
    @PutMapping("/myPaperInfo/operate")
    @ResponseBody
    public AjaxMsg updateMyPaper(Paper paper) {
        if (teacherService.updateMyPaper(paper) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 教师删除试卷接口
     *
     * @param id 试卷id
     */
    @DeleteMapping("/myPaperInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteMyPaper(@PathVariable Integer id) {
        if (teacherService.deleteMyPaper(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");

    }

    /**
     * 保存当前试卷的试卷名
     */
    @GetMapping("/myPaperInfo/savePapName")
    @ResponseBody
    public AjaxMsg savePapName(String papName, HttpServletRequest request) {
        request.getSession().setAttribute("papName", papName);
        return new AjaxMsg(true, WebUtil.getRequestIP(request));
    }

    /**
     * 查看试卷对应的题目列表分页数据接口
     *
     * @param pageNum 页码
     * @param session 获取session中的试卷名称
     */
    @GetMapping("/myPaperInfo/questions")
    @ResponseBody
    public AjaxMsg getQuestions(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, HttpSession session) {
        String papName = (String) session.getAttribute("papName");
        PageHelper.startPage(pageNum, 8);
        List<Question> questions = teacherService.getQuestionByPaperName(papName);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        return new AjaxMsg(true, "获取成功", pageInfo);
    }

    /**
     * 教师获取题目信息接口
     *
     * @param id 题目id
     */
    @GetMapping("/myPaperInfo/questions/{id}")
    @ResponseBody
    public AjaxMsg getQuestionById(@PathVariable Integer id) {
        Question q = teacherService.getQuestionById(id);
        if (q != null) {
            return new AjaxMsg(true, "查询成功", q);
        }
        return new AjaxMsg(false, "查无此题");

    }

    /**
     * 教师添加题目接口
     *
     * @param question 封装了题目信息的对象
     */
    @PostMapping("/myPaperInfo/questions")
    @ResponseBody
    public AjaxMsg addQuestion(Question question, HttpSession session) {
        String papName = (String) session.getAttribute("papName");
        if (teacherService.addQuestion(question, papName) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 教师修改题目信息接口
     *
     * @param question 封装了题目信息的对象
     */
    @PutMapping("/myPaperInfo/questions")
    @ResponseBody
    public AjaxMsg updateQuestion(Question question) {
        if (teacherService.updateQuestion(question) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 教师删除题目信息接口
     *
     * @param id 题目id
     */
    @DeleteMapping("myPaperInfo/questions/{id}")
    @ResponseBody
    public AjaxMsg deleteQuestion(@PathVariable Integer id) {
        if (teacherService.deleteQuestion(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }
}
