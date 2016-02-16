'use strict';

angular.module('planningApp')
    .controller('SecurityReviewDetailController', function ($scope, $rootScope, $stateParams, entity, SecurityReview, Note, User, Link) {
        $scope.securityReview = entity;
        $scope.load = function (id) {
            SecurityReview.get({id: id}, function(result) {
                $scope.securityReview = result;
            });
        };
        var unsubscribe = $rootScope.$on('planningApp:securityReviewUpdate', function(event, result) {
            $scope.securityReview = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
