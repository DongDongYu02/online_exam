let paperTable = new Vue({
    el: '#paper_list_table',
    data: {
        pageInfo: {},
        newPaperInfo: {
            papName: '',
            startTime: '',
            endTime: '',
            examTime:''
        },
        updatePaperInfo: {
            id: '',
            papName: '',
            startTime: '',
            endTime: '',
            examTime:''
        },
        papName:'',
        papNameTemp:'',
        //新增试卷名称校验变量
        newPapNameHasError:false,
        newPapNameErrorMsg:'',
        //新增所属专业校验变量
        newPapMajorHasError:false,
        newPapMajorErrorMsg:'',
        //新增开始时间校验变量
        newStartTimeHasError:false,
        newStartTimeErrorMsg:'',
        //新增结束时间校验变量
        newEndTimeHasError:false,
        newEndTimeErrorMsg:'',
        //新增考试时长校验变量
        newExamTimeHasError:false,
        newExamTimeErrorMsg:'',
        //修改试卷名称校验变量
        updatePapNameHasError:false,
        updatePapNameErrorMsg:'',
    },
    //获取试卷列表分页数据
    created: function () {
        this.getPageInfo(1)
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#paper_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newPaperInfo.papName = ''
            this.newPaperInfo.papMajor = ''
            this.newPaperInfo.startTime = ''
            this.newPaperInfo.endTime = ''
            this.newPaperInfo.examTime = ''
            this.newPapNameErrorMsg=''
            this.newPapNameHasError = false
            this.newPapMajorErrorMsg=''
            this.newPapMajorHasError = false
            this.newStartTimeErrorMsg=''
            this.newStartTimeHasError = false
            this.newEndTimeErrorMsg=''
            this.newEndTimeHasError = false
            this.newExamTimeErrorMsg=''
            this.newExamTimeHasError = false
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updatePapNameErrorMsg=''
            this.updatePapNameHasError = false
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#paper_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //点击添加按钮打开试卷添加模态框
        add: function () {
            $("#paper_add_model").modal({
                backdrop: 'static'
            });
        },
        //查看试卷的题目列表
        checkQuestions: function (papName) {
            axios.get('myPaperInfo/savePapName?papName='+ papName).then((response)=>{
                if(response.data.success){
                    location.href = response.data.message + '/teacher/paperQuestionManage'
                }
            })
        },
        //点击修改按钮打开修改试卷信息模态框，回显该试卷的信息
        update: function (id) {
            $("#paper_update_model").modal({
                backdrop: 'static'
            });
            this.getPaper(id)
        },
        del: function (id) {
            // if (confirm('确认删除？')) {
            //     this.deletePaper(id)
            // }
            $.confirm({
                confirmButton: '确认',
                cancelButton: '取消',
                title: '题目管理',
                content: '确认删除试卷？',
                confirm: ()=>{
                   this.deletePaper(id)
                },
                cancel: function(){
                    //取消
                }
            });
        },
        //根据页码获取试卷列表数据
        getPageInfo: function (pageNum) {
            axios.get('myPaperInfo/operate?pageNum=' + pageNum).then((response) => {
                if(response.data.element.list.length === 0 ){
                    this.getPageInfo(response.data.element.pageNum - 1)
                }
                this.pageInfo = response.data.element
            })
        },

        //根据试卷id ajax请求获取试卷信息
        getPaper: function (id) {
            axios.get('myPaperInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updatePaperInfo.papName = response.data.element.papName
                    this.updatePaperInfo.startTime = response.data.element.startTime
                    this.updatePaperInfo.endTime = response.data.element.endTime
                    this.updatePaperInfo.examTime = response.data.element.examTime
                    this.updatePaperInfo.id = response.data.element.id
                    this.papNameTemp = response.data.element.papName
                }
            })
        },
        //ajax请求添加试卷接口
        addPaper: function () {
            // if(confirm('确认添加试卷？')){
            //
            // }

            if(this.checkNewPapName() && this.checkNewStartTime() && this.checkNewEndTime() && this.checkNewExamTime()){
                let value = this.$refs['newPapNameRef'].value;
                axios.get('myPaperInfo/checkPaperName?papName=' + value).then((response) => {
                    if(response.data.success){
                        $.confirm({
                            confirmButton: '确认',
                            cancelButton: '取消',
                            title: '题目管理',
                            content: '确认添加试卷？',
                            confirm: ()=>{
                                axios.post('myPaperInfo/operate?' +
                                    'papName=' + this.newPaperInfo.papName +
                                    '&startTime=' + this.newPaperInfo.startTime +
                                    '&examTime=' + this.newPaperInfo.examTime +
                                    '&endTime=' + this.newPaperInfo.endTime)
                                    .then((response) => {
                                        if (response.data.success === true) {
                                            $('#paper_add_model').modal('hide')
                                            if (this.pageInfo.endRow === 8 && this.pageInfo.isLastPage) {
                                                this.getPageInfo(this.pageInfo.pages + 1)
                                            } else {
                                                this.getPageInfo(this.pageInfo.pages)
                                            }
                                            this.clearNewModalStatus()
                                            $.tooltip(response.data.message, 2500, true);
                                        }
                                    })
                            },
                            cancel: function(){
                                //取消
                            }
                        });
                    }else{
                        this.newPapNameHasError = true
                        this.newPapNameErrorMsg = '试卷已存在'
                    }
                })
            }
        },
        updateAxios:function (){
            $.confirm({
                confirmButton: '确认',
                cancelButton: '取消',
                title: '题目管理',
                content: '确认修改试卷？',
                confirm: ()=>{
                    axios.put('myPaperInfo/operate?' +
                        'papName=' + this.updatePaperInfo.papName +
                        '&startTime=' + this.updatePaperInfo.startTime +
                        '&endTime=' + this.updatePaperInfo.endTime +
                        '&examTime=' + this.updatePaperInfo.examTime +
                        `&id=` + this.updatePaperInfo.id)
                        .then((response) => {
                            $('#paper_update_model').modal('hide')
                            $.tooltip(response.data.message, 2500, true);
                            this.getPageInfo(this.pageInfo.pageNum)
                        })
                },
                cancel: function(){
                    //取消
                }
            });
        },
        updatePaper: function () {
            if(this.checkUpdatePapName()){
                let value = this.$refs['updatePapNameRef'].value;
                if(value.toUpperCase() === this.papNameTemp.toUpperCase()){
                    this.updateAxios()
                }else{
                    axios.get('myPaperInfo/checkPaperName?papName=' + value).then((response) => {
                        if(response.data.success){
                            this.updateAxios()
                        }else{
                            this.updatePapNameHasError = true
                            this.updatePapNameErrorMsg = '试卷已存在'
                        }
                    })
                }

            }
        },
        deletePaper(id) {
            axios.delete('myPaperInfo/operate/' + id).then((response) => {
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                this.getPageInfo(this.pageInfo.pageNum)
                $.tooltip(response.data.message, 2500,true);
            })
        },
        //新增试卷名称输入框校验
        checkNewPapName: function () {
            let value = this.$refs['newPapNameRef'].value;
            if (value === '') {
                this.newPapNameHasError = true
                this.newPapNameErrorMsg = '试卷名称不能为空'
                return false
            }
            this.newPapNameHasError = false
            this.newPapNameErrorMsg = ''
            return true
        },
        //新增考试开始时间选择校验
        checkNewStartTime: function () {
            let value = this.$refs['newStartTimeRef'].value;
            if (value === '') {
                this.newStartTimeHasError = true
                this.newStartTimeErrorMsg = '请选择考试开始时间'
                return false
            }
            this.newStartTimeHasError = false
            this.newStartTimeErrorMsg = ''
            return true
        },
        //新增考试结束时间选择校验
        checkNewEndTime: function () {
            let value = this.$refs['newEndTimeRef'].value;
            if (value === '') {
                this.newEndTimeHasError = true
                this.newEndTimeErrorMsg = '请选择考试结束时间'
                return false
            }
            this.newEndTimeHasError = false
            this.newEndTimeErrorMsg = ''
            return true
        },
        //新增考试时长选择校验
        checkNewExamTime: function () {
            let value = this.$refs['newExamTimeRef'].value;
            if (value === '') {
                this.newExamTimeHasError = true
                this.newExamTimeErrorMsg = '请选择考试时长'
                return false
            }
            this.newExamTimeHasError = false
            this.newExamTimeErrorMsg = ''
            return true
        },

        //修改试卷名称输入框校验
        checkUpdatePapName: function () {
            let value = this.$refs['updatePapNameRef'].value;
            if (value === '') {
                this.updatePapNameHasError = true
                this.updatePapNameErrorMsg = '试卷名称不能为空'
                return false
            }
            this.updatePapNameHasError = false
            this.updatePapNameErrorMsg = ''
            return true
        },
    }
});
