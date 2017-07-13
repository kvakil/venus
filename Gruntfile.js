module.exports = function(grunt) {
    grunt.initConfig({
        qunit: {
            src: ['qunit/test.html'],
            force: true
        },
        uglify: {
            options: {
                banner: '/*! venus <%= grunt.template.today("dd-mm-yyyy") %> */\n'
            },
            venus: {
                files: {
                    'out/venus.js': ['build/classes/main/**/*.js']
                }
            },
            venus_test: {
                files: {
                    'out/test/venus_test.js': ['build/classes/test/lib/kotlin-test*.js', 'build/classes/test/venus_test.js'],
                    'out/test/qunit.js': ['qunit/qunit.js']
                }
            }
        },
        cssmin: {
            venus_test: {
                files: {
                    'out/test/qunit.css': ['qunit/qunit.css']
                }
            }
        },
        htmlmin: {
            venus_test: {
                files: {
                    'out/test/index.html': ['qunit/test_min.html']
                }
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.registerTask('test', 'qunit:src');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    grunt.registerTask('dist', ['uglify', 'cssmin', 'htmlmin']);
};
