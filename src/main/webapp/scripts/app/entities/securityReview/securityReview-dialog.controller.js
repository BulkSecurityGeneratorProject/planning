'use strict';

angular.module('planningApp').controller('SecurityReviewDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SecurityReview', 'Note', 'User', 'Link',
        function($scope, $stateParams, $uibModalInstance, entity, SecurityReview, Note, User, Link) {

        $scope.securityReview = entity;
        $scope.notes = Note.query();
        $scope.users = User.query();
        $scope.links = Link.query();
        $scope.load = function(id) {
            SecurityReview.get({id : id}, function(result) {
                $scope.securityReview = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('planningApp:securityReviewUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.securityReview.id != null) {
                SecurityReview.update($scope.securityReview, onSaveSuccess, onSaveError);
            } else {
                SecurityReview.save($scope.securityReview, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForReviewDate = {};

        $scope.datePickerForReviewDate.status = {
            opened: false
        };

        $scope.datePickerForReviewDateOpen = function($event) {
            $scope.datePickerForReviewDate.status.opened = true;
        };
}]);
