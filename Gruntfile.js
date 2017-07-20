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
                    'out/venus.js': ['build/classes/main/min/*.js']
                }
            },
            venus_dev: {
                options: {
                    compress: false,
                    mangle: false,
                    beautify: true
                },
                files: {
                    'out/venus.js': ['build/classes/main/min/*.js']
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
            venus: {
                files: {
                    'out/css/venus.css': ['src/main/frontend/css/*.css']
                }
            },
            venus_test: {
                files: {
                    'out/test/qunit.css': ['qunit/qunit.css']
                }
            }
        },
        htmlmin: {
            venus: {
                options: {
                    removeComments: true,
                    collapseWhitespace: true,
                    removeEmptyAttributes: true,
                    removeCommentsFromCDATA: true,
                    removeRedundantAttributes: true,
                    collapseBooleanAttributes: true,
                },
                files: {
                    'out/index.html': ['src/main/frontend/index.html']
                }
            },
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
    grunt.registerTask('dist', ['uglify:venus', 'uglify:venus_test', 'cssmin', 'htmlmin']);
    grunt.registerTask('devdist', ['uglify:venus_dev', 'cssmin', 'htmlmin']);
    grunt.registerTask('frontend', ['cssmin:venus', 'htmlmin:venus']);
};
