App.service('persistenceREST', function($http) {
	return {
		fetchAllEarnings: function(controller){
			var url = "http://localhost:8080/earnings/all";
			$http({method: 'GET', url: url}).
		      success(function(data, status) {
		    	  console.log("List retrieved successfuly.");
		    	  for (var i=0;i<data.length;i++){
		    		  console.log(data[i]);
		    		  controller.pushEarning(data[i]);
		      	  }
		      }).
		      error(function(data, status) {
		    	  console.log("Request failed [Status : "+status+"]");
		      });
		},
		/*Earning Ops*/
		addEarning: function(label, amount){
			var url = "http://localhost:8080/earnings/add/"+label+"/"+amount;
			$http({method: 'PUT', url: url}).
		      success(function(data, status) {
		    	  console.log("Earning added successfuly.");
		    	  console.log("id onsuccess : "+data);
		    	  console.log($scope);
		      }).
		      error(function(data, status) {
		    	  console.log("Request failed [Status : "+status+"]");
		      });
		}
	};
	
});
App.service('persistencejs', function() {
	persistence.store.websql.config(persistence, 'em', 'Earning Monitor Database', 5*1024*1024);
	
	/*Earning*/
	var Earning = persistence.define('earning', {
		label: 'TEXT',
		amount: 'INT'
	});
	
	/*Outgoing*/
	var Outgoing = persistence.define('outgoing', {
		label: 'TEXT',
		amount: 'INT',
		type: 'TEXT'
	});
	persistence.schemaSync();
	return {
		/*Earning Ops*/
		addEarning: function(label, amount){
			var t = new Earning();
			t.label = label;
			t.amount = amount;
			persistence.add(t);
			persistence.flush();
			return t.id;
		},
		removeEarning: function(id){
			Earning.all().filter('id','=',id).destroyAll();
			persistence.flush();
		},
		fetchAllEarnings: function(controller){
			Earning.all().list(function(items){
				var itemCount = items.length;
				items.forEach(function(item){
					controller.pushEarning(item.id, item.label, item.amount);
					if(--itemCount==0)
						controller.refresh();
					
				});
			});
		},
		/*Outgoing Ops*/
		addOutgoing: function(label, amount, type){
			var t = new Outgoing();
			t.label = label;
			t.amount = amount;
			t.type = type;
			persistence.add(t);
			persistence.flush();
			return t.id;
		},
		removeOutgoing: function(id){
			Outgoing.all().filter('id','=',id).destroyAll();
			persistence.flush();
		},
		fetchAllOutgoings: function(controller){
			Outgoing.all().list(function(items){
				var itemCount = items.length;
				items.forEach(function(item){
					controller.pushOutgoing(item.id, item.label, item.amount, item.type);
					if(--itemCount==0)
						controller.refresh();
					
				});
			});
		}
	};

});