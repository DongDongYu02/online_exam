let studentInfo = new Vue({
    el: '#student_list_table',
    data: {
        teaInfo: {},
        pageInfo: {},
        studentResult:[],
        stuName:''
    },
    created: function () {
        axios.get('teacherInfo').then((response) => {
            if (response.data.success === true) {
                this.teaInfo = response.data.element
                this.getPageInfo()
            }
        })

    },
    methods: {
        getPageInfo: function () {
            axios.get('myStudentInfo?teaMajor=' +
                this.teaInfo.teaMajor).then((response) => {
                this.pageInfo = response.data.element
            })
        },
        checkResult:function (id,stuName){
            $("#student_result_model").modal({
                backdrop: 'static'
            });
            axios.get('checkStudentResult/'+id).then((response)=>{
                if(response.data.success){
                    this.studentResult = response.data.element
                    this.stuName = stuName
                }
            })
        }
    }
})
