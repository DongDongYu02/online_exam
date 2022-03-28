let adminTable = new Vue({
    el: '#admin_list_table',
    data: {
        pageInfo: {},
        newAdminInfo: {
            username: '',
            password: ''
        },
        updateAdminInfo: {
            id: '',
            username: '',
            password: ''
        },
        //检查用户名是否可用变量
        isAvailable: false,
        tempName: '',
        //新增用户名校验变量
        newUsernameHasError: false,
        newUsernameErrorMsg: '',
        //新增密码校验变量
        newPasswordHasError: false,
        newPasswordErrorMsg: '',
        //修改用户名校验变量
        updateUsernameHasError: false,
        updateUsernameErrorMsg: '',
        //修改密码校验变量
        updatePasswordHasError: false,
        updatePasswordErrorMsg: '',
    },
    //打开管理员信息页时，获取管理员信息列表
    created: function () {
        this.getPageInfo(1);
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#admin_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newAdminInfo.username = ''
            this.newAdminInfo.password = ''
            this.newUsernameHasError = false
            this.newUsernameErrorMsg = ''
            this.newPasswordHasError = false
            this.newPasswordErrorMsg = ''
            this.isAvailable = false
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updateUsernameHasError = false
            this.updateUsernameErrorMsg = ''
            this.updatePasswordHasError = false
            this.updatePasswordErrorMsg = ''
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#admin_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //点击管理员添加按钮打开管理员添加模态框
        add: function () {
            $("#admin_add_model").modal({
                backdrop: 'static'
            })
        },
        //打开修改管理员模态框，并回显对应员工原信息
        update: function (id) {
            $("#admin_update_model").modal({
                backdrop: 'static'
            })
            this.getAdmin(id)
        },
        del: function (id) {
            if (confirm('确认删除？')) {
                this.deleteAdmin(id)
                this.getPageInfo(this.pageInfo.pageNum)
            }
        },
        getAdmin: function (id) {
            axios.get('adminInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateAdminInfo.id = response.data.element.id
                    this.updateAdminInfo.username = response.data.element.username
                    this.updateAdminInfo.password = response.data.element.password
                    this.tempName = response.data.element.username
                }
            })
        },
        //根据页码获取管理员列表数据
        getPageInfo: function (pageNum) {
            axios.get('adminInfo/operate?pageNum=' + pageNum).then((response) => {
                if(response.data.element.list.length === 0 ){
                    this.getPageInfo(response.data.element.pageNum - 1)
                }
                this.pageInfo = response.data.element
            })
        },
        //发送ajax请求添加管理员接口
        addAdmin: function () {
            if (this.checkNewUsername() && this.checkNewPassword()) {
                let value = this.$refs['newUsernameRef'].value;
                axios.get('adminInfo/checkUsername?username=' + value).then((response) => {
                    if (response.data.success) {
                        axios.post('adminInfo/operate?username=' + this.newAdminInfo.username
                            + '&password=' + this.newAdminInfo.password)
                            .then((response) => {
                                if (response.data.success === true) {
                                    $('#admin_add_model').modal('hide')
                                    if (this.pageInfo.endRow === 8 && this.pageInfo.isLastPage) {
                                        this.getPageInfo(this.pageInfo.pages + 1)
                                    } else {
                                        this.getPageInfo(this.pageInfo.pages)
                                    }
                                    this.clearNewModalStatus()
                                    $.tooltip(response.data.message, 2500, true);
                                }
                            })
                    } else {
                        this.newUsernameHasError = true
                        this.newUsernameErrorMsg = '用户名已存在'
                    }
                })

            }
        },
        //ajax提交修改请求
        updateAxios: function () {
            axios.put('adminInfo/operate?id=' + this.updateAdminInfo.id
                + '&username=' + this.updateAdminInfo.username
                + '&password=' + this.updateAdminInfo.password).then((response) => {
                $('#admin_update_model').modal('hide')
                $.tooltip(response.data.message, 2500, true);
                this.getPageInfo(this.pageInfo.pageNum)
            })
        },
        updateAdmin: function () {
            if (this.checkUpdateUsername() && this.checkUpdatePassword()) {
                let value = this.$refs['updateUsernameRef'].value;
                if (value.toUpperCase() !== this.tempName.toUpperCase()) {
                    axios.get('adminInfo/checkUsername?username=' + value).then((response) => {
                        if (response.data.success) {
                            this.updateAxios()
                        } else {
                            this.updateUsernameHasError = true
                            this.updateUsernameErrorMsg = '用户名已存在'
                            return false
                        }
                    })
                } else {
                    this.updateAxios()
                }
            }
        },
        //ajax请求删除管理员
        deleteAdmin: function (id) {
            axios.delete('adminInfo/operate/' + id).then((response) => {
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                this.getPageInfo(this.pageInfo.pageNum)
                $.tooltip(response.data.message, 2500, true);
            })
        },

        //新增用户名输入框校验
        checkNewUsername: function () {
            let value = this.$refs['newUsernameRef'].value;
            if (value === '') {
                this.newUsernameHasError = true
                this.newUsernameErrorMsg = '用户名不能为空'
                return false
            }
            if (!(/^\S{1,10}$/.test(value))) {
                this.newUsernameHasError = true
                this.newUsernameErrorMsg = '用户名长度不能超过10'
                return false
            }
            this.newUsernameHasError = false
            this.newUsernameErrorMsg = ''
            return true
        },
        //新增密码输入框校验
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
        //修改用户名输入框校验
        checkUpdateUsername: function () {
            let value = this.$refs['updateUsernameRef'].value;
            if (value === '') {
                this.updateUsernameHasError = true
                this.updateUsernameErrorMsg = '用户名不能为空'
                return false
            }
            if (!(/^\S{1,10}$/.test(value))) {
                this.newUsernameHasError = true
                this.newUsernameErrorMsg = '用户名长度不能超过10'
                return false
            }
            this.updateUsernameHasError = false
            this.updateUsernameErrorMsg = ''
            return true
        },
        //修改密码输入框校验
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
    }

});
