storageEngine = function() {
	var database;
	var objectStores;
	return {
	    save : function(type, obj, successCallback, errorCallback) {

			if (!obj.id) {
				delete obj.id ;
			} else {
				obj.id = parseInt(obj.id)
			}

			$.post('TaskServlet', {task: JSON.stringify(obj)})
				.done(function() { successCallback(obj); })
	    },
	    findAll : function(type, successCallback, errorCallback) {
		    $.get('TaskServlet')
				.done(successCallback)
	    },
	    delete : function(type, id, successCallback, errorCallback) { 
	    },
	    findByProperty : function(type, propertyName, propertyValue, successCallback, errorCallback) {
		    const param = {};
		    param[propertyName] = propertyName;

			$.get('TaskServlet', param)
				.done(successCallback.bind())
	    },
		findById : function (type, id, successCallback, errorCallback)	{
		}
	}
}();
