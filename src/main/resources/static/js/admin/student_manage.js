let studentTable = new Vue({
    el: '#student_list_table',
    data: {
        pageInfo: {},
        newStudentInfo: {
            stuName: '',
            stuPassword: '',
            stuMajor: '',
            stuTel: '',
        },
        updateStudentInfo: {
            id: '',
            stuName: '',
            stuPassword: '',
            stuMajor: '',
            stuTel: '',
        },
        majorList: [],
        //新增学生姓名校验变量
        newStuNameHasError: false,
        newStuNameErrorMsg: '',
        //新增学生登录密码校验变量
        newPasswordHasError: false,
        newPasswordErrorMsg: '',
        //新增学生登录密码校验变量
        newStuTelHasError: false,
        newStuTelErrorMsg: '',
        //新增学生专业校验变量
        newStuMajorHasError: false,
        newStuMajorErrorMsg: '',
        //修改学生姓名校验变量
        updateStuNameHasError: false,
        updateStuNameErrorMsg: '',
        //修改学生登录密码校验变量
        updatePasswordHasError: false,
        updatePasswordErrorMsg: '',
        //修改学生登录密码校验变量
        updateStuTelHasError: false,
        updateStuTelErrorMsg: '',
        //修改学生专业校验变量
        updateStuMajorHasError: false,
        updateStuMajorErrorMsg: '',

    },
    //获取学生列表分页数据
    created: function () {
        this.getPageInfo(1)
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#student_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newStudentInfo.stuName = ''
            this.newStudentInfo.stuPassword = ''
            this.newStudentInfo.stuMajor = ''
            this.newStudentInfo.stuTel = ''
            this.newStuNameErrorMsg = ''
            this.newStuNameHasError = false
            this.newPasswordErrorMsg = ''
            this.newPasswordHasError = false
            this.newStuTelErrorMsg = ''
            this.newStuTelHasError = false
            this.newStuMajorErrorMsg = ''
            this.newStuMajorHasError = false
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updateStuNameHasError = false
            this.updatePasswordErrorMsg = ''
            this.updatePasswordHasError = false
            this.updateStuTelErrorMsg = ''
            this.updateStuTelHasError = false
            this.updateStuMajorErrorMsg = ''
            this.updateStuMajorHasError = false
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#student_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //点击添加按钮打开学生添加模态框，并获取专业列表
        add: function () {
            $("#student_add_model").modal({
                backdrop: 'static'
            });
            this.getMajorList()

        },
        //修改按钮
        update: function (id) {
            $("#student_update_model").modal({
                backdrop: 'static'
            });
            this.getMajorList()
            this.getStudent(id)
        },
        //删除按钮
        del: function (id) {
            if (confirm('确认删除？')) {
                this.deleteStudent(id)
            }

        },
        //根据页码获取学生列表数据
        getPageInfo: function (pageNum) {
            axios.get('studentInfo/operate?pageNum=' + pageNum).then((response) => {
                if(response.data.element.list.length === 0 ){
                    this.getPageInfo(response.data.element.pageNum - 1)
                }
                this.pageInfo = response.data.element
            })
        },
        //获取专业列表
        getMajorList: function () {
            axios.get('majorInfo/allMajor').then((response) => {
                this.majorList = response.data.element
            })
        },
        //根据学生学号ajax请求获取学生信息
        getStudent: function (id) {
            axios.get('studentInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateStudentInfo.stuName = response.data.element.stuName
                    this.updateStudentInfo.stuPassword = response.data.element.stuPassword
                    this.updateStudentInfo.stuMajor = response.data.element.stuMajor
                    this.updateStudentInfo.stuTel = response.data.element.stuTel
                    this.updateStudentInfo.id = response.data.element.id
                }
            })
        },
        //ajax请求添加学生接口
        addStudent: function () {
            if (this.checkNewStuName() && this.checkNewPassword() && this.checkNewStuTel() && this.checkNewStuMajor()) {
                axios.post('studentInfo/operate?' +
                    'stuName=' + this.newStudentInfo.stuName +
                    '&stuMajor=' + this.newStudentInfo.stuMajor +
                    '&stuPassword=' + this.newStudentInfo.stuPassword +
                    '&stuTel=' + this.newStudentInfo.stuTel)
                    .then((response) => {
                        if (response.data.success === true) {
                            $('#student_add_model').modal('hide')
                            if (this.pageInfo.endRow === 8 && this.pageInfo.isLastPage) {
                                this.getPageInfo(this.pageInfo.pages + 1)
                            } else {
                                this.getPageInfo(this.pageInfo.pages)
                            }
                            this.clearNewModalStatus()
                            $.tooltip(response.data.message, 2500, true);
                        }
                    })
            }

        },
        //ajax请求修改学生信息接口
        updateStudent: function () {
            if (this.checkUpdateStuName() && this.checkUpdatePassword() && this.checkUpdateStuTel()){
                axios.put('studentInfo/operate?' +
                    'stuName=' + this.updateStudentInfo.stuName +
                    '&stuMajor=' + this.updateStudentInfo.stuMajor +
                    '&stuPassword=' + this.updateStudentInfo.stuPassword +
                    '&stuTel=' + this.updateStudentInfo.stuTel +
                    '&id=' + this.updateStudentInfo.id)
                    .then((response) => {
                        if (response.data.success === true) {
                            $('#student_update_model').modal('hide')
                            $.tooltip(response.data.message, 2500,true);
                            this.getPageInfo(this.pageInfo.pageNum)
                        }
                    })
            }
        },
        //ajax请求删除学生信息接口
        deleteStudent(id) {
            axios.delete('studentInfo/operate/' + id).then((response) => {
                $.tooltip(response.data.message, 2500, true);
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                this.getPageInfo(this.pageInfo.pageNum)
            })
        },
        //新增学生姓名输入框校验
        checkNewStuName: function () {
            if (this.$refs['newStuNameRef'].value === '') {
                this.newStuNameHasError = true
                this.newStuNameErrorMsg = '姓名不能为空'
                return false
            }
            this.newStuNameHasError = false
            this.newStuNameErrorMsg = ''
            return true
        },
        //新增学生密码输入框校验
        checkNewPassword: function () {
            if (this.$refs['newPasswordRef'].value === '') {
                this.newPasswordHasError = true
                this.newPasswordErrorMsg = '密码不能为空'
                return false
            }
            this.newPasswordHasError = false
            this.newPasswordErrorMsg = ''
            return true
        },
        //新增学生电话输入框校验
        checkNewStuTel: function () {
            let value = this.$refs['newStuTelRef'].value;
            if (value === '') {
                this.newStuTelHasError = true
                this.newStuTelErrorMsg = '学生电话不能为空'
                return false
            }
            if (!(/^1[3456789]\d{9}$/.test(value))) {
                this.newStuTelHasError = true
                this.newStuTelErrorMsg = '请输入有效手机号'
                return false
            }
            this.newStuTelHasError = false
            this.newStuTelErrorMsg = ''
            return true
        },
        //新增学生专业输入框校验
        checkNewStuMajor: function () {
            let value = this.$refs['newStuMajorRef'].value;
            if (value === '') {
                this.newStuMajorHasError = true
                this.newStuMajorErrorMsg = '请选择学生专业'
                return false
            }
            this.newStuMajorHasError = false
            this.newStuMajorErrorMsg = ''
            return true
        },
        //修改学生姓名输入框校验
        checkUpdateStuName: function () {
            if (this.$refs['updateStuNameRef'].value === '') {
                this.updateStuNameHasError = true
                this.updateStuNameErrorMsg = '姓名不能为空'
                return false
            }
            this.updateStuNameHasError = false
            this.updateStuNameErrorMsg = ''
            return true
        },
        //修改学生密码输入框校验
        checkUpdatePassword: function () {
            if (this.$refs['updatePasswordRef'].value === '') {
                this.updatePasswordHasError = true
                this.updatePasswordErrorMsg = '密码不能为空'
                return false
            }
            this.updatePasswordHasError = false
            this.updatePasswordErrorMsg = ''
            return true
        },
        //修改学生电话输入框校验
        checkUpdateStuTel: function () {
            let value = this.$refs['updateStuTelRef'].value;
            if (value === '') {
                this.updateStuTelHasError = true
                this.updateStuTelErrorMsg = '学生电话不能为空'
                return false
            }
            if (!(/^1[3456789]\d{9}$/.test(value))) {
                this.updateStuTelHasError = true
                this.updateStuTelErrorMsg = '请输入有效手机号'
                return false
            }
            this.updateStuTelHasError = false
            this.updateStuTelErrorMsg = ''
            return true
        },

    }
});
