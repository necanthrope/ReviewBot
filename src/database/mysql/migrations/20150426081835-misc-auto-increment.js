var dbm = global.dbm || require('db-migrate');
var type = dbm.dataType;

exports.up = function(db, callback) {
  db.runSql("ALTER TABLE misc MODIFY COLUMN misc INT auto_increment", callback);
};

exports.down = function(db, callback) {
  db.runSql("ALTER TABLE misc MODIFY COLUMN misc INT(11)", callback);
};
