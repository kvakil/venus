module.exports = function(grunt) {
    grunt.initConfig({
        qunit: {
            src: ['test.html'],
            force: true
        }
    });
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.registerTask('test', 'qunit:src');
};
