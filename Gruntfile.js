module.exports = function(grunt) {
    grunt.initConfig({
        qunit: {
            src: ['qunit/test.html'],
            force: true
        },
        uglify: {
            options: {
                banner: '/*! venus <%= grunt.template.today("dd-mm-yyyy") %> */\n',
                compress: {
                    unused: false
                }
            },
            venus: {
                files: {
                    'venus.min.js': ['build/classes/main/**/*.js', 'build/classes/test/lib/kotlin-test*.js', 'build/classes/test/venus_test.js']
                }
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.registerTask('test', 'qunit:src');
    grunt.loadNpmTasks('grunt-contrib-uglify');
};
