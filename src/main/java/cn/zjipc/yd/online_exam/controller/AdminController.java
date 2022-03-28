package cn.zjipc.yd.online_exam.controller;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.service.AdminService;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * ==============================================管理员信息管理接口=====================================================
     * 获取管理员列表的分页数据
     */
    @GetMapping("/adminInfo/operate")
    @ResponseBody
    public AjaxMsg operate(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Admin> adminList = adminService.getAdminList();
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return new AjaxMsg(true, "success", pageInfo);
    }

    /**
     * 根据ID获取管理员信息
     *
     * @param id 管理员id
     */
    @GetMapping("/adminInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg operate(@PathVariable Integer id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return new AjaxMsg(true, "查询成功", admin);
        }
        return new AjaxMsg(false, "查询失败");
    }

    /**
     * 检查添加管理员用户名是否存在
     *
     * @param username 用户名
     */
    @GetMapping("/adminInfo/checkUsername")
    @ResponseBody
    public AjaxMsg checkUsername(String username) {
        if (adminService.getAdminByUsername(username) != null) {
            return new AjaxMsg(false, "用户名已存在");
        }
        return new AjaxMsg(true, "用户名可用");
    }

    /**
     * 添加管理员接口
     *
     * @param admin 封装了管理员信息的对象
     */
    @PostMapping("/adminInfo/operate")
    @ResponseBody
    public AjaxMsg saveAdmin(Admin admin) {
        if (adminService.saveAdmin(admin) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改管理员接口
     *
     * @param admin 封装了管理员信息的对象
     */
    @PutMapping("/adminInfo/operate")
    @ResponseBody
    public AjaxMsg updateAdmin(Admin admin) {
        if (adminService.updateAdmin(admin) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 根据id删除管理员接口
     *
     * @param id 管理员id
     */
    @DeleteMapping("/adminInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteAdmin(@PathVariable Integer id) {
        if (adminService.deleteAdminById(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }

    /**
     * ==============================================教师信息管理接口======================================================
     * 分页查询教师列表数据
     *
     * @param pageNum 页码
     */
    @GetMapping("/teacherInfo/operate")
    @ResponseBody
    public AjaxMsg getTeacherList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Teacher> teacherList = adminService.getTeacherList();
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 添加教师信息接口
     *
     * @param teacher 封装了教师信息的对象
     */
    @PostMapping("/teacherInfo/operate")
    @ResponseBody
    public AjaxMsg addTeacher(Teacher teacher) {
        if (adminService.addTeacher(teacher) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改教师接口
     *
     * @param teacher 封装了教师信息的对象
     */
    @PutMapping("/teacherInfo/operate")
    @ResponseBody
    public AjaxMsg updateTeacher(Teacher teacher) {
        if (adminService.updateTeacher(teacher) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(true, "修改失败");
    }

    /**
     * 根据id查询教师信息
     * 修改时回显教师信息
     */
    @GetMapping("/teacherInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getTeacherById(@PathVariable Integer id) {
        Teacher t = adminService.getTeacherById(id);
        if (t != null) {
            return new AjaxMsg(true, "查询成功", t);
        }
        return new AjaxMsg(false, "查无此人");
    }

    /**
     * 根据id删除教师
     */
    @DeleteMapping("/teacherInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteTeacher(@PathVariable Integer id) {
        if (adminService.deleteTeacherById(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }

    /**
     * ================================================学生信息管理接口====================================================
     * 获取学生列表的分页数据
     *
     * @param pageNum 页码
     */
    @GetMapping("/studentInfo/operate")
    @ResponseBody
    public AjaxMsg getStudentList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Student> studentList = adminService.getStudentList();
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 查询学生信息byId
     * 修改时作为回显数据
     *
     * @param id 学生id
     */
    @GetMapping("/studentInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getStudentById(@PathVariable Integer id) {
        Student student = adminService.getStudentById(id);
        if (student != null) {
            return new AjaxMsg(true, "查询成功", student);
        }
        return new AjaxMsg(false, "查无此人");

    }

    /**
     * 添加学生信息接口
     *
     * @param student 封装学生信息的对象
     */
    @PostMapping("/studentInfo/operate")
    @ResponseBody
    public AjaxMsg addStudent(Student student) {
        if (adminService.addStudent(student) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改学生信息接口
     *
     * @param student 封装了学生信息的对象
     */
    @PutMapping("/studentInfo/operate")
    @ResponseBody
    public AjaxMsg updateStudent(Student student) {
        if (adminService.updateStudent(student) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 删除学生接口
     *
     * @param id 学生id
     */
    @DeleteMapping("/studentInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteStudent(@PathVariable Integer id) {
        if (adminService.deleteStudentById(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");

    }

    /**
     * =============================================专业信息管理接口=======================================================
     * 查询专业信息分页数据接口
     *
     * @param pageNum 页码
     */
    @GetMapping("/majorInfo/operate")
    @ResponseBody
    public AjaxMsg getMajorList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Major> majorList = adminService.getMajorList();
        PageInfo<Major> pageInfo = new PageInfo<>(majorList);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 查询所有专业集合
     * 添加学生、教师信息时，专业选择下拉框的数据
     */
    @GetMapping("/majorInfo/allMajor")
    @ResponseBody
    public AjaxMsg getMajorList() {
        List<Major> majorList = adminService.getMajorList();
        return new AjaxMsg(true, "查询成功", majorList);
    }

    /**
     * 查询专业信息接口
     * 作为修改专业信息时的回显数据
     *
     * @param id 专业id
     */
    @GetMapping("/majorInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getMajor(@PathVariable Integer id) {
        Major major = adminService.getMajorById(id);
        if (major != null) {
            return new AjaxMsg(true, "查询成功", major);
        }
        return new AjaxMsg(false, "查无此专业");
    }

    /**
     * 检查专业名称是否已存在
     */
    @GetMapping("/majorInfo/checkMajorName")
    @ResponseBody
    public AjaxMsg checkMajorName(String majName) {
        if (adminService.checkMajorName(majName) != null) {
            return new AjaxMsg(false, "专业名已存在");
        }
        return new AjaxMsg(true, "专业名可用");
    }

    /**
     * 添加专业信息
     *
     * @param major 封装了专业信息的对象
     */
    @PostMapping("/majorInfo/operate")
    @ResponseBody
    public AjaxMsg addMajor(Major major) {
        if (adminService.addMajor(major) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改专业信息
     *
     * @param major 封装了专业信息的对象
     */
    @PutMapping("/majorInfo/operate")
    @ResponseBody
    public AjaxMsg updateMajor(Major major) {
        if (adminService.updateMajor(major) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 删除专业byID
     *
     * @param id 专业id
     */
    @DeleteMapping("/majorInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteMajor(@PathVariable Integer id) {
        if (adminService.deleteMajorById(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }

    /**
     * ================================================试卷信息管理接口====================================================
     * 获取试卷信息的分页数据
     *
     * @param pageNum 页码
     */
    @GetMapping("/paperInfo/operate")
    @ResponseBody
    public AjaxMsg getAllPaper(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Paper> allPaper = adminService.getAllPaper();
        PageInfo<Paper> pageInfo = new PageInfo<>(allPaper);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 获取试卷列表
     */
    @GetMapping("/paperInfo/allPaper")
    @ResponseBody
    public AjaxMsg allPaper() {
        List<Paper> allPaper = adminService.getAllPaper();
        return new AjaxMsg(true, "查询成功", allPaper);
    }

    /**
     * 查询试卷信息接口
     *
     * @param id 试卷id
     */
    @GetMapping("/paperInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getPaperById(@PathVariable Integer id) {
        Paper paper = adminService.getPaperById(id);
        if (paper != null) {
            Paper paper1 = WebUtil.paperFormatDateTime(paper);
            return new AjaxMsg(true, "查询成功", paper1);
        }
        return new AjaxMsg(false, "查无此试卷");
    }

    /**
     * 检查试卷名称是否可用
     *
     * @param papName 试卷名称
     */
    @GetMapping("/paperInfo/checkPaperName")
    @ResponseBody
    public AjaxMsg checkPaperName(String papName) {
        if (adminService.checkPapName(papName) != null) {
            return new AjaxMsg(false, "试卷已存在");
        }
        return new AjaxMsg(true, "试卷名称可用");
    }

    /**
     * 添加试卷信息
     *
     * @param paper 封装了试卷信息的对象
     */
    @PostMapping("/paperInfo/operate")
    @ResponseBody
    public AjaxMsg addPaper(Paper paper) {
        if (adminService.addPaper(paper) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改试卷信息接口
     *
     * @param paper 封装了试卷信息的对象
     */
    @PutMapping("/paperInfo/operate")
    @ResponseBody
    public AjaxMsg updatePaper(Paper paper) {
        if (adminService.updatePaper(paper) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 删除试卷接口
     *
     * @param id 试卷id
     */
    @DeleteMapping("/paperInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deletePaper(@PathVariable Integer id) {
        if (adminService.deletePaper(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }

    /**
     * =============================================题目信息管理接口=======================================================
     * 查询题目列表的分页数据
     *
     * @param pageNum 页码
     */
    @GetMapping("/questionInfo/operate")
    @ResponseBody
    public AjaxMsg getAllQuestion(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        List<Question> allQuestion = adminService.getAllQuestion();
        PageInfo<Question> pageInfo = new PageInfo<>(allQuestion);
        return new AjaxMsg(true, "查询成功", pageInfo);
    }

    /**
     * 查询题目信息byId
     *
     * @param id 题目id
     */
    @GetMapping("/questionInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg getQuestionById(@PathVariable Integer id) {
        Question q = adminService.getQuestionById(id);
        if (q != null) {
            return new AjaxMsg(true, "查询成功", q);
        }
        return new AjaxMsg(false, "查无此题目");
    }

    /**
     * 检查题目是否已存在
     * @param queName 题目名
     */
    @GetMapping("questionInfo/checkQueName")
    @ResponseBody
    public AjaxMsg checkQueName(String queName){
        if(adminService.checkQueName(queName) != null){
            return new AjaxMsg(false,"题目已存在");
        }
        return new AjaxMsg(true,"题目可用");
    }

    /**
     * 添加题目接口
     *
     * @param question 封装了题目信息的对象
     */
    @PostMapping("/questionInfo/operate")
    @ResponseBody

    public AjaxMsg addQuestion(Question question) {
        question.setQueName(question.getQueName().replaceAll(" ", "+"));
        if (adminService.addQuestion(question) > 0) {
            return new AjaxMsg(true, "添加成功");
        }
        return new AjaxMsg(false, "添加失败");
    }

    /**
     * 修改题目信息接口
     *
     * @param question 封装了题目信息的对象
     */
    @PutMapping("/questionInfo/operate")
    @ResponseBody
    public AjaxMsg updateQuestion(Question question) throws UnsupportedEncodingException {
        question.setQueName(question.getQueName().replaceAll(" ", "+"));
        if (adminService.updateQuestion(question) > 0) {
            return new AjaxMsg(true, "修改成功");
        }
        return new AjaxMsg(false, "修改失败");
    }

    /**
     * 删除题目信息接口
     *
     * @param id 题目id
     */
    @DeleteMapping("/questionInfo/operate/{id}")
    @ResponseBody
    public AjaxMsg deleteQuestion(@PathVariable Integer id) {
        if (adminService.deleteQuestion(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }
}

