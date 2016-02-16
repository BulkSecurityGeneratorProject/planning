'use strict';

angular.module('planningApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


