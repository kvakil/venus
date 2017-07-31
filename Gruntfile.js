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
                    'out/venus.js': ['build/classes/main/min/*.js'],
                    'out/js/codemirror.js': ['src/main/frontend/js/*.js']
                }
            }
        },
        cssmin: {
            venus: {
                files: {
                    'out/css/venus.css': ['src/main/frontend/css/*.css']
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
                    collapseBooleanAttributes: true
                },
                files: {
                    'out/index.html': ['src/main/frontend/index.html']
                }
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    grunt.registerTask('test', 'qunit:src');
    grunt.registerTask('dist', ['uglify:venus', 'cssmin', 'htmlmin']);
    grunt.registerTask('frontend', ['cssmin:venus', 'htmlmin:venus']);
};
