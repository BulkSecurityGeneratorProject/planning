'use strict';

angular.module('planningApp')
    .controller('NoteDetailController', function ($scope, $rootScope, $stateParams, entity, Note, User) {
        $scope.note = entity;
        $scope.load = function (id) {
            Note.get({id: id}, function(result) {
                $scope.note = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:noteUpdate', function(event, result) {
            $scope.note = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
