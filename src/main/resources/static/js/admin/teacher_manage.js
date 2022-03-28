let teacherTable = new Vue({
    el: '#teacher_list_table',
    data: {
        pageInfo: {},
        newTeacherInfo: {
            teaName: '',
            teaPassword: '',
            teaMajor: '',
            teaTel: '',
        },
        updateTeacherInfo: {
            id: '',
            teaName: '',
            teaPassword: '',
            teaMajor: '',
            teaTel: '',
        },
        majorList: [],
        //新增教师姓名校验变量
        newTeaNameHasError: false,
        newTeaNameErrorMsg: '',
        //新增教师登录密码校验变量
        newPasswordHasError: false,
        newPasswordErrorMsg: '',
        //新增教师登录密码校验变量
        newTeaTelHasError: false,
        newTeaTelErrorMsg: '',
        //新增教师专业校验变量
        newTeaMajorHasError: false,
        newTeaMajorErrorMsg: '',
        //修改教师姓名校验变量
        updateTeaNameHasError: false,
        updateTeaNameErrorMsg: '',
        //修改教师登录密码校验变量
        updatePasswordHasError: false,
        updatePasswordErrorMsg: '',
        //修改教师登录密码校验变量
        updateTeaTelHasError: false,
        updateTeaTelErrorMsg: '',
        //修改教师专业校验变量
        updateTeaMajorHasError: false,
        updateTeaMajorErrorMsg: '',
    },
    //获取教师列表分页数据
    created: function () {
        this.getPageInfo(1)
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#teacher_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#teacher_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newTeacherInfo.teaName = ''
            this.newTeacherInfo.teaPassword = ''
            this.newTeacherInfo.teaMajor = ''
            this.newTeacherInfo.teaTel = ''
            this.newTeaNameErrorMsg = ''
            this.newTeaNameHasError = false
            this.newPasswordErrorMsg = ''
            this.newPasswordHasError = false
            this.newTeaTelErrorMsg = ''
            this.newTeaTelHasError = false
            this.newTeaMajorErrorMsg = ''
            this.newTeaMajorHasError = false
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updateTeacherInfo.teaName = ''
            this.updateTeacherInfo.teaPassword = ''
            this.updateTeacherInfo.teaMajor = ''
            this.updateTeacherInfo.teaTel = ''
            this.updateTeaNameErrorMsg = ''
            this.updateTeaNameHasError = false
            this.updatePasswordErrorMsg = ''
            this.updatePasswordHasError = false
            this.updateTeaTelErrorMsg = ''
            this.updateTeaTelHasError = false
            this.updateTeaMajorErrorMsg = ''
            this.updateTeaMajorHasError = false
        },
        //点击添加按钮打开教师添加模态框，并获取专业列表
        add: function () {
            $("#teacher_add_model").modal({
                backdrop: 'static'
            });
            this.getMajorList()
        },
        update: function (id) {
            $("#teacher_update_model").modal({
                backdrop: 'static'
            });
            this.getMajorList()
            this.getTeacher(id)
        },
        del: function (id) {
            if (confirm('确认删除？')) {
                this.deleteTeacher(id)
            }

        },
        //根据页码获取教师列表数据
        getPageInfo: function (pageNum) {
            axios.get('teacherInfo/operate?pageNum=' + pageNum).then((response) => {
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
        getTeacher: function (id) {
            axios.get('teacherInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateTeacherInfo.teaName = response.data.element.teaName
                    this.updateTeacherInfo.teaPassword = response.data.element.teaPassword
                    this.updateTeacherInfo.teaMajor = response.data.element.teaMajor
                    this.updateTeacherInfo.teaTel = response.data.element.teaTel
                    this.updateTeacherInfo.id = response.data.element.id
                }
            })
        },
        //ajax请求添加教师接口
        addTeacher: function () {
            if (this.checkNewTeaName() && this.checkNewPassword() && this.checkNewTeaTel() && this.checkNewTeaMajor()) {
                axios.post('teacherInfo/operate?' +
                    'teaName=' + this.newTeacherInfo.teaName +
                    '&teaMajor=' + this.newTeacherInfo.teaMajor +
                    '&teaPassword=' + this.newTeacherInfo.teaPassword +
                    '&teaTel=' + this.newTeacherInfo.teaTel)
                    .then((response) => {
                        if (response.data.success === true) {
                            $('#teacher_add_model').modal('hide')
                            if (this.pageInfo.endRow === 8) {
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
        //ajax请求更新教师信息
        updateTeacher: function () {
            if (this.checkUpdateTeaName() && this.checkUpdatePassword() && this.checkUpdateTeaTel()) {
                axios.put('teacherInfo/operate?' +
                    'teaName=' + this.updateTeacherInfo.teaName +
                    '&teaMajor=' + this.updateTeacherInfo.teaMajor +
                    '&teaPassword=' + this.updateTeacherInfo.teaPassword +
                    '&teaTel=' + this.updateTeacherInfo.teaTel +
                    '&id=' + this.updateTeacherInfo.id)
                    .then((response) => {
                        if (response.data.success === true) {
                            $('#teacher_update_model').modal('hide')
                            $.tooltip(response.data.message, 2500, true);
                            this.getPageInfo(this.pageInfo.pageNum)
                        }
                    })
            }

        },
        //ajax请求删除教师
        deleteTeacher(id) {
            axios.delete('teacherInfo/operate/' + id).then((response) => {
                $.tooltip(response.data.message,2500,true);
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                this.getPageInfo(this.pageInfo.pageNum)
            })
        },
        //新增教师姓名输入框校验
        checkNewTeaName: function () {
            if (this.$refs['newTeaNameRef'].value === '') {
                this.newTeaNameHasError = true
                this.newTeaNameErrorMsg = '姓名不能为空'
                return false
            }
            this.newTeaNameHasError = false
            this.newTeaNameErrorMsg = ''
            return true
        },
        //新增教师密码输入框校验
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
        //新增教师电话输入框校验
        checkNewTeaTel: function () {
            let value = this.$refs['newTeaTelRef'].value;
            if (value === '') {
                this.newTeaTelHasError = true
                this.newTeaTelErrorMsg = '教师电话不能为空'
                return false
            }
            if (!(/^1[3456789]\d{9}$/.test(value))) {
                this.newTeaTelHasError = true
                this.newTeaTelErrorMsg = '请输入有效手机号'
                return false
            }
            this.newTeaTelHasError = false
            this.newTeaTelErrorMsg = ''
            return true
        },
        //新增教师专业输入框校验
        checkNewTeaMajor: function () {
            let value = this.$refs['newTeaMajorRef'].value;
            if (value === '') {
                this.newTeaMajorHasError = true
                this.newTeaMajorErrorMsg = '请选择教师所在专业'
                return false
            }
            this.newTeaMajorHasError = false
            this.newTeaMajorErrorMsg = ''
            return true
        },
        //修改教师姓名输入框校验
        checkUpdateTeaName: function () {
            if (this.$refs['updateTeaNameRef'].value === '') {
                this.updateTeaNameHasError = true
                this.updateTeaNameErrorMsg = '姓名不能为空'
                return false
            }
            this.updateTeaNameHasError = false
            this.updateTeaNameErrorMsg = ''
            return true
        },
        //修改教师密码输入框校验
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
        //修改教师电话输入框校验
        checkUpdateTeaTel: function () {
            let value = this.$refs['updateTeaTelRef'].value;
            if (value === '') {
                this.updateTeaTelHasError = true
                this.updateTeaTelErrorMsg = '教师电话不能为空'
                return false
            }
            if (!(/^1[3456789]\d{9}$/.test(value))) {
                this.updateTeaTelHasError = true
                this.updateTeaTelErrorMsg = '请输入有效手机号'
                return false
            }
            this.updateTeaTelHasError = false
            this.updateTeaTelErrorMsg = ''
            return true
        },

    }
});
