'use strict';

angular.module('planningApp')
	.controller('SecurityReviewDeleteController', function($scope, $uibModalInstance, entity, SecurityReview) {

        $scope.securityReview = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SecurityReview.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
