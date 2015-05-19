'use strict';

var directives = angular.module('directives', []);

directives.directive('contactsLoading', [function() {
	function postLink(scope, element, attrs) {
		var spinner = new Spinner();
		scope.$watch(attrs.contactsLoading, function() {
			if (scope.loading) {
				spinner.spin(element[0]);
			} else {
				spinner.stop();
			}
		});
		element.on('$destroy', function() {
			spinner.stop();
		});
	}
	return {
		restrict: 'A',
		scope: {loading: '=contactsLoading'},
		compile: function() {
			return {
				pre: function() {},
				post: postLink
			}
		}
	};
}]);
