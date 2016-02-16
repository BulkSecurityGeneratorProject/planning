'use strict';

angular.module('planningApp')
    .controller('LinkDetailController', function ($scope, $rootScope, $stateParams, entity, Link) {
        $scope.link = entity;
        $scope.load = function (id) {
            Link.get({id: id}, function(result) {
                $scope.link = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:linkUpdate', function(event, result) {
            $scope.link = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
