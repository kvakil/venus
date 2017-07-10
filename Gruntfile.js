module.exports = function(grunt) {
    grunt.initConfig({
        qunit: {
            src: ['test.html']
        }
    });
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.registerTask('test', 'qunit:src');
};
