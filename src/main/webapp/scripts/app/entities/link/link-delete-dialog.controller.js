'use strict';

angular.module('planningApp')
	.controller('LinkDeleteController', function($scope, $uibModalInstance, entity, Link) {

        $scope.link = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Link.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
