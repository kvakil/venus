grunt.loadNpmTasks('grunt-contrib-qunit');
gruntConfig.qunit = {
    src: ['test.html']
};
grunt.registerTask('test', 'qunit:src');