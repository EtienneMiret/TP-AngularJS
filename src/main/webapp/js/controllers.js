'use strict';

(function() {

	var controllers = angular.module('controllers', ['services']);

	var finishLoading = function($scope) {
		return function() {
			$scope.loading = false;
		};
	};

	var addField = function($scope) {
		return function() {
			var newField = {order: $scope.contact.fields.length};
			$scope.contact.fields.push(newField);
		};
	};

	var removeField = function($scope) {
		return function(field) {
			var i;
			var fields = $scope.contact.fields;
			fields.splice(fields.indexOf(field), 1);
			for (i = 0; i < fields.length; i++) {
				if (fields[i].order > field.order) {
					fields[i].order--;
				}
			}
		};
	};

	var sortableFields = function($scope) {
		return {
			handle: '.handle',
			items: '.field',
			stop: function(event, ui) {
				$scope.$apply(function() {
					$('#contact .field').each(function (index) {
						$(this).scope().field.order = index;
					});
				});
			}
		};
	};

	controllers.controller('ContactListCtrl', ['$scope', 'Contact', function($scope, Contact) {
		$scope.loading = true;
		$scope.contacts = Contact.query({}, finishLoading($scope));
		$scope.order = 'lastName';
	}]);

	controllers.controller('ContactDetailCtrl', ['$scope', '$routeParams', '$location', '$interval', 'Contact', function($scope, $routeParams, $location, $interval, Contact) {
		$scope.loading = true;
		$scope.contact = Contact.get($routeParams, finishLoading($scope));
		$scope.remove = function() {
			$scope.loading = true;
			$scope.contact.$remove({}, function() {
				$location.path('/');
			});
		};
		$scope.now = new Date();
		var time = $interval(function() {
			$scope.now = new Date();
		}, 1000);
		$scope.$on('$destroy', function() {
			$interval.cancel(time);
		});
	}]);

	controllers.controller('ContactNewCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
		$scope.contact = {fields: new Array()};
		$scope.save = function() {
			$scope.loading = true;
			$http.post('rest/contacts/new', $scope.contact).success(function(data) {
				$location.path('/' + data);
			});
		};
		$scope.addField = addField($scope);
		$scope.removeField = removeField($scope);
		$('#contact').sortable(sortableFields($scope));
	}]);

	controllers.controller('ContactEditCtrl', ['$scope', '$routeParams', '$location', 'Contact', function($scope, $routeParams, $location, Contact) {
		$scope.loading = true;
		$scope.contact = Contact.get($routeParams, finishLoading($scope));
		$scope.save = function() {
			$scope.loading = true;
			$scope.contact.$update({}, function() {
				$location.path('/' + $scope.contact.id);
			});
		};
		$scope.addField = addField($scope);
		$scope.removeField = removeField($scope);
		$('#contact').sortable(sortableFields($scope));
	}]);

})();
