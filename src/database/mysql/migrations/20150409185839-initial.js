var dbm = global.dbm || require('db-migrate');
var type = dbm.dataType;
var fs = require('fs');
var path = require('path');
var async = require("async");

function run_sql( db, data, callback ){
	var promises = data.split( ";" ).map( function( command ){
		if( command.trim() == "" ){
			return function( callback ){
				callback( undefined );
			};
		}else{
			return db.runSql.bind( db, command );
		}
	});
	return async.series( promises, callback );
}

exports.up = function(db, callback) {
  var filePath = path.join(__dirname + '/sqls/20150409185839-initial-up.sql');
  fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
    if (err) return callback(err);
      //console.log('received data: ' + data);

		return run_sql( db, data, callback );
  });
};

exports.down = function(db, callback) {
  var filePath = path.join(__dirname + '/sqls/20150409185839-initial-down.sql');
  fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
    if (err) return callback(err);
      console.log('received data: ' + data);

		return run_sql( db, data, callback );
  });
};
