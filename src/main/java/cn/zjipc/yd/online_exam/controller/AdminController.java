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
@RestController
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
    public AjaxMsg getQuestionById(@PathVariable Integer id) {
        Question q = adminService.getQuestionById(id);
        if (q != null) {
            return new AjaxMsg(true, "查询成功", q);
        }
        return new AjaxMsg(false, "查无此题目");
    }

    /**
     * 检查题目是否已存在
     *
     * @param queName 题目名
     */
    @GetMapping("questionInfo/checkQueName")
    public AjaxMsg checkQueName(String queName) {
        if (adminService.checkQueName(queName) != null) {
            return new AjaxMsg(false, "题目已存在");
        }
        return new AjaxMsg(true, "题目可用");
    }

    /**
     * 添加题目接口
     *
     * @param question 封装了题目信息的对象
     */
    @PostMapping("/questionInfo/operate")

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
    public AjaxMsg deleteQuestion(@PathVariable Integer id) {
        if (adminService.deleteQuestion(id) > 0) {
            return new AjaxMsg(true, "删除成功");
        }
        return new AjaxMsg(false, "删除失败");
    }

    /**
     * 统计系统教师人数接口
     *
     * @return 教师人数
     */
    @GetMapping("/teacherCount")
    public AjaxMsg teacherCount() {
        Long count = adminService.getTeacherCount();
        return new AjaxMsg(true, "获取成功", count);
    }

    /**
     * 统计系统学生人数接口
     * @Author Dong
     * @Date 10:55 2022/4/18
     * @return
     **/
    @GetMapping("/studentCount")
    public AjaxMsg studentCount() {
        Long count = adminService.getStudentCount();
        return new AjaxMsg(true, "获取成功", count);
    }

    /**
     * 统计试卷接口
     * @Author Dong
     * @Date 10:55 2022/4/18
     * @return
     **/
    @GetMapping("/paperCount")
    public AjaxMsg paperCount() {
        Long count = adminService.getPaperCount();
        return new AjaxMsg(true, "获取成功", count);
    }

   /**
    * 统计题目数量接口
    * @Author Dong
    * @Date 10:56 2022/4/18
    * @return
    **/
    @GetMapping("/questionCount")
    public AjaxMsg questionCount() {
        Long count = adminService.getQuestionCount();
        return new AjaxMsg(true, "获取成功", count);
    }
}

