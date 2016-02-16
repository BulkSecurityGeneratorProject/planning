'use strict';

angular.module('planningApp').controller('TcDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tc', 'SecurityReview', 'Note', 'User', 'Link',
        function($scope, $stateParams, $uibModalInstance, entity, Tc, SecurityReview, Note, User, Link) {

        $scope.tc = entity;
        $scope.securityreviews = SecurityReview.query();
        $scope.notes = Note.query();
        $scope.users = User.query();
        $scope.links = Link.query();
        $scope.load = function(id) {
            Tc.get({id : id}, function(result) {
                $scope.tc = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('planningApp:tcUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tc.id != null) {
                Tc.update($scope.tc, onSaveSuccess, onSaveError);
            } else {
                Tc.save($scope.tc, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPlannedDate = {};

        $scope.datePickerForPlannedDate.status = {
            opened: false
        };

        $scope.datePickerForPlannedDateOpen = function($event) {
            $scope.datePickerForPlannedDate.status.opened = true;
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
