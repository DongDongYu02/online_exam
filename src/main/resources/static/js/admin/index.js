let indexApp = new Vue({
    el: "#count",
    data: {
        teacherCount: '',
        studentCount: '',
        paperCount: '',
        questionCount: ''
    },
    created() {
        this.getTeacherCount()
        this.getStudentCount()
        this.getPaperCount()
        this.getQuestionCount()
    },
    methods: {
        getTeacherCount() {
            axios.get('/admin/teacherCount').then((resp => {
                this.teacherCount = resp.data.element
            }))
        },
        getStudentCount() {
            axios.get('/admin/studentCount').then((resp => {
                this.studentCount = resp.data.element
            }))
        },
        getPaperCount() {
            axios.get('/admin/paperCount').then((resp => {
                this.paperCount = resp.data.element
            }))
        },
        getQuestionCount() {
            axios.get('/admin/questionCount').then((resp => {
                this.questionCount = resp.data.element
            }))
        }
    }
})
