var dbm = global.dbm || require('db-migrate');
var type = dbm.dataType;

exports.up = function(db, callback) {
  db.runSql("ALTER TABLE misc MODIFY COLUMN id INT auto_increment", callback);
};

exports.down = function(db, callback) {
  db.runSql("ALTER TABLE misc MODIFY COLUMN id INT(11)", callback);
};
