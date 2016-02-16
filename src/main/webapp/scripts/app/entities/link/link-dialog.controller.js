'use strict';

angular.module('planningApp').controller('LinkDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Link',
        function($scope, $stateParams, $uibModalInstance, entity, Link) {

        $scope.link = entity;
        $scope.load = function(id) {
            Link.get({id : id}, function(result) {
                $scope.link = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('planningApp:linkUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.link.id != null) {
                Link.update($scope.link, onSaveSuccess, onSaveError);
            } else {
                Link.save($scope.link, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
