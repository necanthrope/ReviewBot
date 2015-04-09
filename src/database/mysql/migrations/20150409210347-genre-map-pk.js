var dbm = global.dbm || require('db-migrate');
var type = dbm.dataType;

exports.up = function(db, callback) {
	db.runSql( "ALTER TABLE genre_map ADD COLUMN id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY", callback );
};

exports.down = function(db, callback) {
	db.runSql( "ALTER TABLE genre_map DROP COLUMN id", callback );
};
