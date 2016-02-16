'use strict';

angular.module('planningApp')
	.controller('TcDeleteController', function($scope, $uibModalInstance, entity, Tc) {

        $scope.tc = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
