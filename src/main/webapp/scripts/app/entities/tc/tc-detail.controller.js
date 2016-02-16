'use strict';

angular.module('planningApp')
    .controller('TcDetailController', function ($scope, $rootScope, $stateParams, entity, Tc, SecurityReview, Note, User, Link) {
        $scope.tc = entity;
        $scope.load = function (id) {
            Tc.get({id: id}, function(result) {
                $scope.tc = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:tcUpdate', function(event, result) {
            $scope.tc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
