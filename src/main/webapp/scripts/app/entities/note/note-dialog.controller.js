'use strict';

angular.module('planningApp').controller('NoteDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Note', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Note, User) {

        $scope.note = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Note.get({id : id}, function(result) {
                $scope.note = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('planningApp:noteUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.note.id != null) {
                Note.update($scope.note, onSaveSuccess, onSaveError);
            } else {
                Note.save($scope.note, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
