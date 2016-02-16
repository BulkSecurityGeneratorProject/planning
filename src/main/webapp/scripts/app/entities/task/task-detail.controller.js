'use strict';

angular.module('planningApp')
    .controller('TaskDetailController', function ($scope, $rootScope, $stateParams, entity, Task, User, SecurityReview, Tc, Note, Link, Project) {
        $scope.task = entity;
        $scope.load = function (id) {
            Task.get({id: id}, function(result) {
                $scope.task = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:taskUpdate', function(event, result) {
            $scope.task = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
