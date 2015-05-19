'use strict';

(function () {

	var filters = angular.module('filters', []);

	filters.filter('relativeTime', ['dateFilter', function(dateFilter) {
		return function(input, now) {
			var date = new Date(input);
			var difference = now.getTime() - date.getTime();
			var result;
			if (difference >= 0) { // input is in the past.
				if (difference < 2000) {
					result = '\u00e0 l\u2019instant';
				} else if (difference < 60 * 1000) {
					result = 'il y a ' + Math.floor(difference / 1000) + ' secondes';
				} else if (difference < 120 * 1000) {
					result = 'il y a une minute';
				} else if (difference < 3600 * 1000) {
					result = 'il y a ' + Math.floor(difference / 60000) + ' minutes';
				} else if (now.getFullYear() == date.getFullYear() && now.getMonth() == date.getMonth() && now.getDate() == date.getDate()) {
					result = 'aujourd\u2019hui \u00e0 ' + dateFilter(date, 'mediumTime');
				} else {
					result = dateFilter(date, 'fullDate') + ' \u00e0 ' + dateFilter(date, 'mediumTime');
				}
			} else { // input is in the future.
				result = dateFilter(date, 'fullDate') + ' \u00e0 ' + dateFilter(date, 'mediumTime');
			}
			return input ? result : '';
		};
	}]);

})();
