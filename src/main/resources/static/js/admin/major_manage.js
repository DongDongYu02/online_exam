let majorTable = new Vue({
    el: '#major_list_table',
    data: {
        pageInfo: {},
        newMajorInfo: {
            majName: ''
        },
        updateMajorInfo: {
            id: '',
            majName: ''
        },
        majorList: [],
        majNameTemp:'',
        //新增专业名称校验变量
        newMajNameHasError: false,
        newMajNameErrorMsg: '',
        //修改专业名称校验变量
        updateMajNameHasError: false,
        updateMajNameErrorMsg: '',
    },
    //获取专业列表分页数据
    created: function () {
        this.getPageInfo(1)
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#major_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newMajorInfo.majName = ''
            this.newMajNameHasError = false
            this.newStuMajorErrorMsg = ''
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updateMajNameHasError = false
            this.updateStuMajorErrorMsg = ''
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#major_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //点击添加按钮打开专业添加模态框
        add: function () {
            $("#major_add_model").modal({
                backdrop: 'static'
            });
        },
        //点击修改按钮打开修改专业模态框并且回显当前专业数据
        update: function (id) {
            $("#major_update_model").modal({
                backdrop: 'static'
            });
            this.getMajor(id)
        },
        //删除专业按钮，确认删除
        del: function (id) {
            if (confirm('确认删除？')) {
                this.deleteMajor(id)
            }

        },
        //根据页码获取专业列表数据
        getPageInfo: function (pageNum) {
            axios.get('majorInfo/operate?pageNum=' + pageNum).then((response) => {
                if(response.data.element.list.length === 0 ){
                    this.getPageInfo(response.data.element.pageNum - 1)
                }
                this.pageInfo = response.data.element
            })
        },
        //根据专业id ajax请求获取专业信息
        getMajor: function (id) {
            axios.get('majorInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateMajorInfo.majName = response.data.element.majName
                    this.updateMajorInfo.id = response.data.element.id
                    this.majNameTemp = response.data.element.majName
                }
            })
        },
        //ajax请求添加专业接口
        addMajor: function () {
            if (this.checkNewMajName()) {
                let value = this.$refs['newMajNameRef'].value;
                axios.get('majorInfo/checkMajorName?majName='+ value).then((response)=>{
                    if(response.data.success){
                        axios.post('majorInfo/operate?' +
                            'majName=' + this.newMajorInfo.majName)
                            .then((response) => {
                                if (response.data.success === true) {
                                    $('#major_add_model').modal('hide')
                                    if (this.pageInfo.endRow === 8) {
                                        this.getPageInfo(this.pageInfo.pages + 1)
                                    } else {
                                        this.getPageInfo(this.pageInfo.pages)
                                    }
                                    this.clearNewModalStatus()
                                    $.tooltip(response.data.message, 2500, true);
                                }
                            })
                    }else{
                        this.newMajNameHasError = true
                        this.newMajNameErrorMsg = '专业名称已存在！'
                    }
                })

            }
        },
        //ajax请求修改专业信息接口
        updateAxios:function (){
            axios.put('majorInfo/operate?' +
                'majName=' + this.updateMajorInfo.majName +
                '&id=' + this.updateMajorInfo.id)
                .then((response) => {
                    if (response.data.success === true) {
                        $('#major_update_model').modal('hide')
                        $.tooltip(response.data.message, 2500,true);
                        this.getPageInfo(this.pageInfo.pageNum)
                    }
                })
        },
        updateMajor: function () {
            if (this.checkUpdateMajName()) {
                let value = this.$refs['updateMajNameRef'].value;
                if(value.toUpperCase() === this.majNameTemp.toUpperCase()){
                    this.updateAxios()
                }else{
                    axios.get('majorInfo/checkMajorName?majName='+ value).then((response)=>{
                        if(response.data.success){
                            this.updateAxios()
                        }else{
                            this.updateMajNameHasError = true
                            this.updateMajNameErrorMsg = '专业名称已存在！'
                        }
                    })
                }
            }
        },
        //ajax请求删除专业接口
        deleteMajor(id) {
            axios.delete('majorInfo/operate/' + id).then((response) => {
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                $.tooltip(response.data.message, 2500, true);
                this.getPageInfo(this.pageInfo.pageNum)
            })
        },
        //新增专业名称输入框校验
        checkNewMajName: function () {
            if (this.$refs['newMajNameRef'].value === '') {
                this.newMajNameHasError = true
                this.newMajNameErrorMsg = '专业名称不能为空'
                return false
            }
            this.newMajNameHasError = false
            this.newMajNameErrorMsg = ''
            return true
        },
        //修改专业名称输入框校验
        checkUpdateMajName: function () {
            if (this.$refs['updateMajNameRef'].value === '') {
                this.updateMajNameHasError = true
                this.updateMajNameErrorMsg = '专业名称不能为空'
                return false
            }
            this.updateMajNameHasError = false
            this.updateMajNameErrorMsg = ''
            return true
        },
    }
});
