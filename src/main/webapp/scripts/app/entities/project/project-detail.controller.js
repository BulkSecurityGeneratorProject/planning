'use strict';

angular.module('planningApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, User, Task, Link) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
